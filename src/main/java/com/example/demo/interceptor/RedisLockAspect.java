package com.example.demo.interceptor;

import com.example.demo.common.BusinessException;
import com.example.demo.utils.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 简化 redis 分布式锁的使用
 * Created by stan on 2018/12/11.
 */
@Aspect
@Component
public class RedisLockAspect {


    public final static String SYSTEM_CODE = "tms";

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private ExpressionParser parser = new SpelExpressionParser();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;



    /**
     * 根据 pjp 找到对应的方法
     * 后期加上类型判定
     * @return
     */
    private Method resolveMethod(ProceedingJoinPoint pjp){
        Method method = null;
        Signature signature = pjp.getSignature();
        if( signature instanceof MethodSignature) {
            method = ((MethodSignature) pjp.getSignature()).getMethod();
        } else {
            Class<?> clz = signature.getDeclaringType();
            Optional<Method> methodOptional = Stream.of(clz.getDeclaredMethods())
                    .filter(p -> p.getName().equals(signature.getName()))
                    .findFirst();
            if(methodOptional.isPresent()) {
                method = methodOptional.get();
            }
        }
        return method;
    }


    /**
     * 解析 spel 表达式
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String resolveSpelKey(String key, Method method, Object[] args){
        Expression expression = parser.parseExpression(key);
        EvaluationContext evaluationContext  = new MethodBasedEvaluationContext(null,method,args,parameterNameDiscoverer);
        return expression.getValue(evaluationContext,String.class);
    }




    /**
     * 拦截含义对应注解的方法
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(RedisLock)")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable{

        // resolve the annotation
        Method method = resolveMethod(pjp);
        if(method == null) {
            return pjp.proceed();
        }
        RedisLock redisLockAnno = method.getAnnotation(RedisLock.class);
        if(redisLockAnno == null) {
            return pjp.proceed();
        }

        // resolve prefix
        String prefix = redisLockAnno.lockPrefix();
        if(prefix.trim().length() == 0) {
            prefix = String.format("%s:%s",
                    pjp.getSignature().getDeclaringType().getSimpleName(), pjp.getSignature().getName());
        }

        // resolve expression key
        String key = resolveSpelKey(redisLockAnno.key(), method, pjp.getArgs());
        // 生成最终的 key
        String redisKey = String.format("%s:%s:%s",SYSTEM_CODE,prefix,key);

        // logger.info("the redis key is {}", redisKey);

        // resolve time out
        long timeout = redisLockAnno.timeUnit().toSeconds(redisLockAnno.timeout());
        if(timeout < 0) {
            timeout = 1;
        }

        // define execute callback
        Function<Boolean,Object> fun = e -> {
            if(e) {
                logger.info("{} 获取分布式锁成功, 正在执行方法!!", redisKey);
                try {
                    return pjp.proceed();
                } catch (Throwable t) {
                    logger.info(String.format("%s 执行失败", redisKey),t);
                    // 执行失败的处理
                    throw new RuntimeException(t);
                }
            } else {
                // 没执行的处理
                logger.info("{} 获取分布式失败, 方法不执行!!", redisKey);
                throw new BusinessException("当前方法正在执行， 请稍候重试！");
            }
        };
        return redisUtils.doInRedisLock(redisKey, timeout, fun);
    }

}
