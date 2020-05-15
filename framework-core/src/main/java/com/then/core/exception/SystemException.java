package com.then.core.exception;

import java.text.MessageFormat;

import com.then.core.enums.SysCodeEnum;

public class SystemException extends BaseException {

	private static final long serialVersionUID = -8708190175289333520L;

	public SystemException(long code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public SystemException(long code, String message) {
		super(code, message);
	}

	public SystemException(SysCodeEnum sysCodeEnum) {
		super(sysCodeEnum.getCode(), sysCodeEnum.getMsg());
	}
	
	public SystemException(SysCodeEnum sysCodeEnum, Object... params) {
		super(sysCodeEnum.getCode(), MessageFormat.format(sysCodeEnum.getMsg(), params));
	}

	public SystemException(SysCodeEnum sysCodeEnum, Throwable cause) {
		super(sysCodeEnum.getCode(), sysCodeEnum.getMsg(), cause);
	}

}
