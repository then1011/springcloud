package com.then.springboot.mvc.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.then.core.annotation.RequestData;
import com.then.springboot.mvc.request.resolve.IResolveHandler;
import com.then.springboot.mvc.request.resolve.ResolveHandlerFactory;

public class ParamResolveHandler implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestData.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		IResolveHandler resolveHandler = ResolveHandlerFactory.getInstance(request.getMethod());
		
		Object result = resolveHandler.resolve(request, parameter);
		
//		ParamValidator.validate(result);
		
		return result;
	}

}
