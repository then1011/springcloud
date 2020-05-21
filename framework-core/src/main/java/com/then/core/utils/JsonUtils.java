package com.then.core.utils;

import java.io.InputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.SystemException;

public class JsonUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String toJsonString(Object obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new SystemException(SysCodeEnum.GET_SEQ_FAILURE, e);
		}
	}

	public static <T> T parseObject(String str, Class<T> formatClass) {
		try {
			return MAPPER.readValue(str, formatClass);
		} catch (Exception e) {
			throw new SystemException(SysCodeEnum.GET_SEQ_FAILURE, e);
		}
	}

	public static <T> T parseObject(String str, TypeReference<T> valueTypeRef) {
		try {
			return MAPPER.readValue(str, valueTypeRef);
		} catch (Exception e) {
			throw new SystemException(SysCodeEnum.GET_SEQ_FAILURE, e);
		}
	}

	public static <T> T parseObject(InputStream inputStream, Class<T> formatClass) {
		try {
			return MAPPER.readValue(inputStream, formatClass);
		} catch (Exception e) {
			throw new SystemException(SysCodeEnum.GET_SEQ_FAILURE, e);
		}
	}

	public static <T> T parseObject(InputStream inputStream, TypeReference<T> valueTypeRef) {
		try {
			return MAPPER.readValue(inputStream, valueTypeRef);
		} catch (Exception e) {
			throw new SystemException(SysCodeEnum.GET_SEQ_FAILURE, e);
		}
	}
}
