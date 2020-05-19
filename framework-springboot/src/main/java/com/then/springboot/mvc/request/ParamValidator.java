package com.then.springboot.mvc.request;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.InvalidParamException;
import com.then.core.exception.SystemException;

public class ParamValidator {
	
	private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
	
	private static final String MESSAGE_APPEND = ";";
	
	public static void validate(Object param) {
		if(param == null) {
			return;
		}
		StringBuffer message = new StringBuffer();
		try {
			validateMethod(param, message);
		} catch (Exception e) {
			throw new SystemException(SysCodeEnum.VALIDATE_PARAM_ERROR, e);
		}
		
		if(message.length() > 0) {
			message.delete(message.length() - 1, message.length());
			throw new InvalidParamException(message.toString());
		}
	}
	
	private static void validateMethod(Object param, StringBuffer message) throws Exception {
		message.append(validateResult(param));
		Field[] fields = param.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			field.setAccessible(true);
			Class<?> paramClass = field.getType();
			ClassLoader classLoader = paramClass.getClassLoader();
			Object childObject = field.get(param);
			if(classLoader != null && childObject != null) {
				validateMethod(childObject, message);
			} else if(childObject instanceof Collection) {
				dealCollectionField((Collection<?>) childObject, message);
			} else if(childObject instanceof Map) {
				Map<?,?> mapValues = (Map<?, ?>) childObject;
				dealCollectionField(mapValues.keySet(), message);
				dealCollectionField(mapValues.values(), message);
			}
			field.setAccessible(false);
		}
	}
	
	private static void dealCollectionField(Collection<?> collectionValues, StringBuffer message) throws Exception {
		for(Object collectionValue : collectionValues) {
			validateMethod(collectionValue, message);
		}
	}
	
	private static StringBuffer validateResult(Object param) {
		StringBuffer errorMessage = new StringBuffer();
		Set<ConstraintViolation<Object>> validate = VALIDATOR.validate(param);
		if(!validate.isEmpty()) {
			validate.forEach(objectConstraintViolation -> {
				errorMessage.append(objectConstraintViolation.getMessage()).append(MESSAGE_APPEND);
			});
		}
		return errorMessage;
	}

}
