package com.frc.utility;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * @author songbin
 */
public class JSONProvider {
    private static String getClassName() {
	return "JSONProvider";
    }

    /**
     * json string �? 对象
     * */
    public static <T> Object parseObject(long logIndex, String text,
	    Class<T> clazz) {
	String flag = getClassName() + ".parseObject";
	try {
	    if (StringUtils.isBlank(text))
		return null;
	    return JSON.parseObject(text, clazz);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return null;
	}
    }

    /**
     * json string �? 对象
     * */
    public static <T> Object parseArrayObject(long logIndex, String text,
	    Class<T> clazz) {
	String flag = getClassName() + ".parseArrayObject";
	try {
	    if (StringUtils.isBlank(text))
		return null;
	    return JSON.parseArray(text, clazz);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return null;
	}
    }

    /**
     * 对象 转json string
     * */
    public static String toJSONString(long logIndex, Object object) {
	String flag = getClassName() + ".toJSONString";
	try {
	    if (null == object)
		return null;
	    return JSON.toJSONString(object);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return null;
	}
    }
}
