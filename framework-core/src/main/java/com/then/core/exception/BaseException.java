package com.then.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -8956103431451944836L;

	private long code;

	public BaseException(long code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BaseException(long code, String message) {
		super(message);
		this.code = code;
	}

}
