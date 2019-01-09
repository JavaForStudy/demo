package com.example.demo.utils;

import com.example.demo.common.BusinessException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 通用 Service 校验、取值的 util
 * @author wangsd
 */
public class OpUtil {

	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * isEmpty/isNotEmpty
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >  
	 */
	
	/*
	 * 校验字符串、id、价钱
	 */
	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0;
	}
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	public static boolean isEmpty(Integer id){
		return id == null || id.intValue() == 0;
	}
	public static boolean isNotEmpty(Integer id){
		return !isEmpty(id);
	}
	// 不用 Number 合并是因为  (new BigDecimal("0.123")).intValue() = 0
	// 这与期望不符
	public static boolean isEmpty(BigDecimal price){
		return price == null || price.compareTo(new BigDecimal("0")) == 0;
	}
	public static boolean isNotEmpty(BigDecimal price){
		return !isEmpty(price);
	}
	
	/*
	 * 校验数组集合
	 */
	public static boolean isEmpty(int[] array){
		return array == null || array.length == 0;
	}
	public static boolean isNotEmpty(int[] array){
		return !isEmpty(array);
	}
	public static boolean isEmpty(Object[] array){
		return array == null || array.length == 0;
	}
	public static boolean isNotEmpty(Object[] array){
		return !isEmpty(array);
	}
	public static  boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}
	public static boolean isNotEmpty(Collection<?> collection){
		return !isEmpty(collection);
	}
	public static  boolean isEmpty(Map<?,?> map){
		return map == null || map.isEmpty();
	}
	public static  boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}
	
	
	/*
	 * 校验对象 
	 */
	public static boolean isEmpty(Object obj){
		return isEmpty(obj,false);
	}
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj,false);
	}
	
	/**
	 * 如果为 true，会强制类型转换后进行比较
	 */
	public static boolean isEmpty(Object obj, boolean strict){
		if(obj == null){
			return true;
		}
		if(!strict){ // 如果非强制比较，不进行类型转换后的比较
			return false;
		}
		
		// 不为 null 时的 特殊类型比较 
		if(obj instanceof String){
			return isEmpty((String)obj);
		}else if(obj instanceof Integer){
			return isEmpty((Integer)obj);
		}else if(obj instanceof BigDecimal){
			return isEmpty((BigDecimal)obj);
		}else if(obj instanceof Collection){
			return isEmpty((Collection<?>)obj);
		}else if(obj instanceof Map){
			return isEmpty((Map<?,?>)obj);
		}else{ // 未加上的类型，后期可以随便加
			return false;
		}
	}
	public static boolean isNotEmpty(Object obj, boolean strict){
		return !isEmpty(obj,strict);
	}
	
	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * isEquals/isNotEquals
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >  
	 */
	/**
	 * 判断 src 是否与 tar 相等，两个对象都必须存在才 true
	 * 写这方法主要用于数字相等的比较
	 */
	public static boolean isEquals(Object src, Object tar){
		if(isEmpty(src) || isEmpty(tar)){
			return false;
		}
		
		// 拦截特殊类型比较
		if(src instanceof Number && tar instanceof Number){
			return isEquals((Number)src,(Number)tar);
		}
		return src.equals(tar);
	}
	public static boolean isNotEquals(Object src, Object tar){
		return !isEquals(src,tar);
	}
	
	public static boolean isEquals(Number src, Number tar){
		// 如果不转为 Object，输入 0 的时候该方法会判断为 空
		if(isEmpty((Object)src) || isEmpty((Object)tar)){ 
			return false;
		}
		return src.intValue() == tar.intValue();
	}
	public static boolean isNotEquals(Number src, Number tar){
		return !isEquals(src,tar);
	}
	
	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * ReturnUtil
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 */  
	/**
	 * 获取第一个为 true 的值
	 * <pre>
	obj.getInfo() != null ? obj.getInfo() : ""; // 正确用法
	obj.getPrice() != null ? obj.getPrice() : new BigDecimal("0"); // 正确用法
	obj != null ? obj.getInfo() : ""; // 错误用法,第一个 响应 Object，第二个响应 String，类型不一致会导致潜在问题
	 * </pre>
	 * */
	public static <T> T ft(T obj, T obj2){
		return OpUtil.isNotEmpty(obj) ? obj : obj2;
	}
	
	/***
	 * 没价钱转换成 0.00
	 * 有价钱保留两位小数
	 * @param price
	 * @return
	 */
	public static BigDecimal getSafePrice(BigDecimal price){
		if(OpUtil.isNotEmpty(price)){
			return price.setScale(2, RoundingMode.HALF_UP);
		}else{
			return new BigDecimal("0.00");
		}
	}
	
	/**
	 * 如果 number 为 null， 响应 0
	 * @param number
	 * @return
	 */
	public static Number getSafeNumber(Number number){
		return number != null ? number : 0; 
	}
	
	/**
	 * 如果字符串为 null 或 '  ' 的空串，都响应 ''
	 * @param str
	 * @return
	 */
	public static String getSafeString(String str){
		return isNotEmpty(str) ? str : "";
	}



	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * StringUtil
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 */
	public static int parseInt(Object obj){
		return parseInt(obj,0);
	}
	public static int parseInt(Object obj, int defaultValue){
		if(obj == null){
			return defaultValue;
		}
		if(obj instanceof Number){
			return ((Number)obj).intValue();
		}

		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			// e.printStackTrace();
			return defaultValue;
		}
	}
	public static Integer[] parseIds(String idStr){
		return parseIds(idStr, ",");
	}
	public static Integer[] parseIds(String idStr, String delimiter){
		Integer[] ids = {};
		if(OpUtil.isNotEmpty(idStr)){
			String[] idStrs = idStr.split(delimiter);
			ids = new Integer[idStrs.length];
			for (int i=0; i<idStrs.length; i++) {
				ids[i] = Integer.valueOf(idStrs[i]);
			}
		}
		return ids;
	}


	/**
	 * 将 params 追加到 url 中
	 * @param url
	 * @param params
	 * @return
	 */
	public static String putUrlParams(String url, Map<String,Object> params){
		return putUrlParams(url, params, url.contains("?") ? "&" : "?");
	}
	public static String putUrlParams(String url, Map<String,Object> params, String separator){
		// a=aa&b=bb
		StringBuffer sbf = new StringBuffer();

		if(params != null && !params.isEmpty()){
			for( String key : params.keySet() ){
				Object value = params.get(key);
				if(value != null ){
					String encodedValue = value.toString();
					try{
						encodedValue = URLEncoder.encode(encodedValue.toString(),"UTF-8");
					}catch (Exception e){ }
					sbf.append(key).append("=").append(encodedValue).append("&");
				}
			}
		}
		if(sbf.length() > 0){
			sbf.deleteCharAt(sbf.length() - 1);
			return url + separator + sbf.toString();
		}else{
			return url;
		}
	}
	/**
	 * 从 url 中抽取 params
	 * @param url
	 * @return
	 */
	public static Map<String,Object> extractUrlParams(String url){
		Map<String, Object> params = new HashMap<>();
		if(OpUtil.isEmpty(url)){
			return params;
		}
		if(url.lastIndexOf("?") != -1){
			url = url.substring(url.lastIndexOf("?") + 1);
			String[] kvs = url.split("&");
			for (String kv : kvs) {
				params.put(kv.split("=")[0], kv.split("=")[1]);
			}
		}
		return params;
	}

	public static boolean isMobileNumber(String mobileNumber){
		return mobileNumber != null && mobileNumber.trim().length() > 0
				&& mobileNumber.matches("^1\\d{10}$");
	}

	public static boolean isInteger(String integer){
		return integer != null && integer.trim().length() > 0
				&& integer.matches("\\d+");
	}


	/**
	 * 验证是否为负数
	 * @param number
	 * @throws BusinessException
	 */
	public  static boolean validateNegative(Comparable number) throws BusinessException {
		Comparable instance;

		if (!(number instanceof Number)) {
			throw new BusinessException(String.format("%s 不是数值类型", number));
		} else if (number instanceof Integer) {
			instance = 0;
		} else if (number instanceof Double) {
			instance = 0.0;
		} else if (number instanceof BigDecimal) {
			instance = new BigDecimal(0);
		} else if (number instanceof Short) {
			instance = (short) 0;
		} else if (number instanceof Float) {
			instance = (float) 0.0;
		} else {
			throw new BusinessException(String.format("%s 暂不支持该类型的校验", number));
		}
		return !(number.compareTo(instance) >= 0);
	}



	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * DateUtil
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 */
	public static final SimpleDateFormat sdf = new SimpleDateFormat();
	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	public static final String datePattern = "yyyy-MM-dd";
	public static final String timePattern = "HH:mm:ss";
	public static final String timeSimplePattern = "HH:mm";
	public static final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

	public static final DateTimeFormatter defaultPatternObj = DateTimeFormatter.ofPattern(defaultPattern);
	public static final DateTimeFormatter datePatternObj = DateTimeFormatter.ofPattern(datePattern);
	public static final DateTimeFormatter timePatternObj = DateTimeFormatter.ofPattern(timePattern);
	public static final DateTimeFormatter timeSimplePatternObj = DateTimeFormatter.ofPattern(timeSimplePattern);
	public static final DateTimeFormatter dateTimePatternObj = DateTimeFormatter.ofPattern(dateTimePattern);




	/*
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    * 时间类型的 parse 和 format
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    */
	public static String format(Date date){
		return format(date, defaultPattern);
	}
	public static String format(Date date, String pattern){
		if(OpUtil.isEmpty(date) || OpUtil.isEmpty(pattern)){
			return null;
		}
		sdf.applyPattern(pattern);
		return sdf.format(date);
	}
	public static Date parse(String dateStr){
		return parse(dateStr, defaultPattern);
	}
	public static Date parse(String dateStr, String pattern){
		if(OpUtil.isEmpty(dateStr) || OpUtil.isEmpty(pattern)){
			return null;
		}
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("格式错误！");
			return null;
		}
	}

	/**
	 *
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param pattern
	 * 		格式字符串
	 * 			"%s时" 或 "%s"
	 * 			"%s时%s分" 或 "%s:%s"
	 * 			"%s时%s分%s秒" 或 "%s:%s:%s"
	 * @return
	 * 		01时20分, 01:20
	 * 		如果数据不正确, 响应 ""
	 */
	public static String diffTimeInfo(Date start, Date end, String pattern){
		if(start == null || end == null || isEmpty(pattern)){
			// throw new RuntimeException("参数不能为空！");
			return null;
		}

		// 计算时分秒
		long totalSeconds = Math.abs(start.getTime() - end.getTime()) / 1000;
		long hour = totalSeconds / 60 / 60;
		long minute = (totalSeconds / 60) % 60;
		long second = totalSeconds - (hour * 60 * 60 + minute * 60);

		String argText = "%02d";
		pattern = pattern.replaceAll("%s",argText);

		// 确定出现的次数
		int index = 0;
		int count = 0;
		while((index = pattern.indexOf(argText,index)) != -1){
			index = index + argText.length();
			count++;
		}

		// 格式化响应
		if(count == 1) {
			return String.format(pattern, hour);
		} else if(count == 2) {
			return String.format(pattern, hour, minute);
		} else if(count == 3) {
			return String.format(pattern, hour, minute, second);
		} else {
			return pattern;
		}
	}

	/*
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    * Date to LocalDateTime
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    */
	public static LocalDateTime dateToLocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	public static LocalDate dateToLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone).toLocalDate();
	}
	public static LocalTime dateToLocalTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone).toLocalTime();
	}

	/*
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    * LocalDateTime to Date
    * -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
    */
	public static Date localDateTimeToDate(LocalDateTime dateTime){
		Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	public static Date localDateToDate(LocalDate date){
		return localDateToDate(date,"00:00:00");
	}
	public static Date localDateToDate(LocalDate date, String timestr){
		return localDateToDate(date, LocalTime.parse(timestr, DateTimeFormatter.ofPattern("HH:mm:ss")));
	}
	public static Date localDateToDate(LocalDate date, LocalTime time){
		notNull(date, "LocalDate 为空！");
		return localDateTimeToDate(date.atTime(time));
	}


	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * Assert && Throwing
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new BusinessException(message);
		}
	}
	public static void notEmpty(Object object,String message) {
		if(isEmpty(object,true)){
			throw new BusinessException(message);
		}
	}


	/**
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 * others
	 * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
	 */


}
