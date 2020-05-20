package com.then.springboot.mvc.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.then.core.entity.ResponseResult;
import com.then.core.enums.SysCodeEnum;

public class ReturnResolveHandler implements HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;

	public ReturnResolveHandler(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {

		if (returnValue instanceof ResponseResult 
				|| returnValue instanceof byte[]
				|| returnValue instanceof InputStreamResource) {
			delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
		} else {
			ResponseResult<Object> responseResult = new ResponseResult<Object>(SysCodeEnum.SUCCESS);
			responseResult.setSuccess(true);
			responseResult.setData(returnValue);
			delegate.handleReturnValue(responseResult, returnType, mavContainer, webRequest);
		}
	}

}
