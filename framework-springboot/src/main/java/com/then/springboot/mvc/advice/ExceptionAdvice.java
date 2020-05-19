package com.then.springboot.mvc.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.then.core.constants.Constants;
import com.then.core.entity.ResponseResult;
import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.BaseException;
import com.then.core.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

	@ExceptionHandler(BusinessException.class)
	public ResponseResult<Object> businessExceptionHandler(BusinessException exception) throws Throwable {
		return new ResponseResult<Object>(exception);
	}

	@ExceptionHandler(BaseException.class)
	public ResponseResult<Object> baseExceptionHandler(BaseException exception) throws Throwable {
		log.error("程序或业务异常", exception);
		return new ResponseResult<Object>(exception);
	}

	@ExceptionHandler(Exception.class)
	public ResponseResult<Object> exceptionHandler(Exception exception) throws Throwable {
		log.error("系统异常", exception);
		return new ResponseResult<Object>(SysCodeEnum.INTERNAL_ERROR);
	}

	@Value("${spring.servlet.multipart.max-file-size:" + Constants.DEFAULT_MAX_FILE_SIZE + "}")
	private String maxFileSize;

	@ExceptionHandler(MultipartException.class)
	public ResponseResult<Object> multipartExceptionHandler(MultipartException exception) {
		log.error("上传文件异常", exception);
		return new ResponseResult<Object>(SysCodeEnum.FILE_EXCEED_LIMIT, maxFileSize);
	}

}
