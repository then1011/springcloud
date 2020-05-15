package com.then.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysCodeEnum {

	SUCCESS(0, "执行成功"), 
	ILLEGAL_PARAM(1, "非法参数"), 
	BUSINESS_ERROR(2, "业务异常"), 
	GET_SEQ_FAILURE(3, "数据序列失败"),
	TIME_OUT(4, "内部请求超时"),
	REQUEST_ERROR(5, "内部请求错误"), 
	THREAD_EXECUTE_ERR(6, "执行线程失败"),
	INIT_ERROR(7, "初始化失败"),
	EXECUTE_ERROR(8, "系统执行失败"),
	INTERNAL_ERROR(9, "系统内部错误")
	;

	private long code;
	private String msg;

}
