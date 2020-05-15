package com.then.core.exception;

import com.then.core.enums.SysCodeEnum;

public class InvalidParamException extends BaseException{

	private static final long serialVersionUID = 1902683160450747555L;

	public InvalidParamException(String message) {
		super(SysCodeEnum.ILLEGAL_PARAM.getCode(), message);
	}


}
