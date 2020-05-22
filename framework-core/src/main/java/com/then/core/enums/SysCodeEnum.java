package com.then.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysCodeEnum {

	SUCCESS(0, "执行成功"), 
	ILLEGAL_PARAM(1, "非法参数"), 
	BUSINESS_ERROR(2, "业务异常"), 
	ILLEGAL_PARAMETER(3, "非法参数"),
	TIME_OUT(4, "内部请求超时"),
	REQUEST_ERROR(5, "内部请求错误"), 
	PARAM_FORMAT_ERROR(6, "格式化错误"),
	VALIDATE_PARAM_ERROR(7, "校验参数失败"),
	FILE_EXCEED_LIMIT(8, "文件大小超出限制，最大：{0}"),
	INTERNAL_ERROR(9, "系统内部错误")
	;

	private long code;
	private String msg;

}
