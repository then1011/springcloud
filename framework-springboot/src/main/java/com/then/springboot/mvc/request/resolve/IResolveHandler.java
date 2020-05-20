package com.then.springboot.mvc.request.resolve;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;

import com.then.core.utils.JsonUtils;

public abstract class IResolveHandler {

	abstract public Object resolve(HttpServletRequest request, MethodParameter parameter) throws Exception;

	protected Object getReflectionParam(HttpServletRequest request, MethodParameter parameter) throws Exception {

		Class<?> paramClass = parameter.getParameterType();
		ClassLoader classLoader = paramClass.getClassLoader();
		String paramName = parameter.getParameterName();

		if (classLoader == null) {
			return formatValue(request.getParameter(paramName), paramClass);
		}

		Field[] paramFields = paramClass.getDeclaredFields();

		Object result = paramClass.getDeclaredConstructor().newInstance();

		for (Field paramField : paramFields) {
			if (Modifier.isFinal(paramField.getModifiers())) {
				continue;
			}
			paramField.setAccessible(true);
			paramField.set(result, formatValue(request.getParameter(paramField.getName()), paramField.getType()));
			paramField.setAccessible(false);
		}

		return result;
	}

	private Object formatValue(String value, Class<?> paramFieldClass) {

		if (StringUtils.isBlank(value)) {
			return value;
		}

		if (paramFieldClass == Short.class) {
			return Short.parseShort(value);
		} else if (paramFieldClass == Integer.class) {
			return Integer.parseInt(value);
		} else if (paramFieldClass == Long.class) {
			return Long.parseLong(value);
		} else if (paramFieldClass == Float.class) {
			return Float.parseFloat(value);
		} else if (paramFieldClass == Double.class) {
			return Double.parseDouble(value);
		} else if (paramFieldClass == Character.class) {
			return value.charAt(0);
		} else if (paramFieldClass == Boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (paramFieldClass == Byte.class) {
			return Byte.parseByte(value);
		} else if (Collection.class.isAssignableFrom(paramFieldClass) || Map.class.isAssignableFrom(paramFieldClass)) {
			return JsonUtils.parseObject(value, paramFieldClass);
		}
		return value;
	}
}
