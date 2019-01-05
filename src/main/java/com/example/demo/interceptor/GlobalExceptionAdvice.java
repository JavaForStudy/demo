package com.example.demo.interceptor;

import com.example.demo.common.BusinessException;
import com.example.demo.common.DataBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Exception Handler Controller Advice to catch all controller exceptions and respond gracefully to
 * the caller
 * <p>
 * Created by Y.Kamesh on 8/2/2015.
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    private void handleLog(HttpServletRequest request, Exception ex) {
        Map<String,String []> parameter = request.getParameterMap();
        StringBuffer logBuffer = new StringBuffer();
        if (request != null) {
            logBuffer.append("  request method=" + request.getMethod());
            logBuffer.append("  url=" + request.getRequestURL());
        }
        logBuffer.append("  parameter:[");
        for (Iterator iter = parameter.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<String,String[]> element = (Map.Entry) iter.next();
            String strKey = element.getKey();
            String[] strObj = element.getValue();
            logBuffer.append(strKey).append("=").append(Arrays.toString(strObj)).append(",");
        }
        logBuffer.append("]");
        logger.error(logBuffer.toString());
        logger.error("  exception:{}", ex);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public DataBean handleSQLException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        handleLog(request, e);
        DataBean dataBean = new DataBean();
        dataBean.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "");
        dataBean.setDesc("数据库错误");
        return dataBean;
    }

    @ExceptionHandler({LoginException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public DataBean handleAllException(LoginException e, HttpServletRequest request, HttpServletResponse response) {
        handleLog(request, e);
        DataBean dataBean = new DataBean();
        dataBean.setCode(HttpStatus.UNAUTHORIZED.value() + "");
        dataBean.setDesc("请登录");
        return dataBean;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, BindException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public DataBean handleInvalidFormatException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        handleLog(request, e);
        DataBean dataBean = new DataBean();
        dataBean.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "");
        dataBean.setDesc("不能输入非法字符!");

        if( e instanceof BindException){
            FieldError fieldError = ((BindException) e).getBindingResult().getFieldError();
            if(fieldError != null){
                logger.info(String.format("%s 不支持 %s ", fieldError.getField(), fieldError.getRejectedValue()));
                dataBean.setDesc(String.format("非法输入 %s ", fieldError.getRejectedValue()));
            }
        }

        return dataBean;
    }


    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public DataBean handleBusinessException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        handleLog(request, e);

        DataBean dataBean = new DataBean();
        dataBean.setCode(HttpStatus.OK.value() + "");
        dataBean.setDesc("业务异常！");

        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            dataBean.setCode(businessException.getCode());
            dataBean.setDesc(businessException.getDesc());
        }

        return dataBean;
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Order(Ordered.LOWEST_PRECEDENCE)
    public DataBean handleAllException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        handleLog(request, e);
        DataBean dataBean = new DataBean();
        dataBean.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "");
        if(Objects.equals(e.getClass().getName(),NullPointerException.class.getName())){
            dataBean.setDesc("服务器错误:空异常");
        }else if(Objects.equals(e.getClass().getName(),RuntimeException.class.getName())
                || Objects.equals(e.getClass().getName(),IllegalArgumentException.class.getName())){//runtime将异常打印出来
            dataBean.setDesc(e.getMessage());
        }else{
            dataBean.setDesc("服务器异常");
        }
        return dataBean;
    }
}