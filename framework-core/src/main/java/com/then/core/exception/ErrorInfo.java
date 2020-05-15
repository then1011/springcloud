package com.then.core.exception;

public interface ErrorInfo {
	long getCode();
	String getMessage(Object... params);
}
