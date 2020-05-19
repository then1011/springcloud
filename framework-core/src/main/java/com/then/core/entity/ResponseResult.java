package com.then.core.entity;

import java.io.Serializable;
import java.text.MessageFormat;

import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.BaseException;
import com.then.core.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = -5371548220309172397L;

	private boolean success;

	private long code;

	private String msg;

	private T data;

	public ResponseResult(BaseException exception) {
		this.code = exception.getCode();
		this.msg = exception.getMessage();
	}

	@SuppressWarnings("unchecked")
	public ResponseResult(BusinessException exception) {
		this.code = exception.getCode();
		this.msg = exception.getMessage();
		this.data = (T) exception.getData();
	}

	public ResponseResult(SysCodeEnum sysCodeEnum, Object... params) {
		this.code = sysCodeEnum.getCode();
		this.msg = MessageFormat.format(sysCodeEnum.getMsg(), params);
	}

}
