package com.then.core.exception;

import java.text.MessageFormat;

import lombok.Getter;

@Getter
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1017100175563909925L;

	private Object data;

	public BusinessException(long code, String message, Object... params) {
		super(code, MessageFormat.format(message, params));
	}

	public BusinessException(long code, Object data, String message, Object... params) {
		super(code, MessageFormat.format(message, params));
		this.data = data;
	}

	public BusinessException(ErrorInfo errorInfo, Object... params) {
		super(errorInfo.getCode(), errorInfo.getMessage(params));
	}

	public BusinessException(Object data, ErrorInfo errorInfo, Object... params) {
		super(errorInfo.getCode(), errorInfo.getMessage(params));
		this.data = data;
	}

}
