package com.then.core.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseResult<T> implements Serializable {

	private static final long serialVersionUID = -5371548220309172397L;

	private boolean success = true;

	private long code;

	private String msg;

	private T data;

}
