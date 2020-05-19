package com.then.springboot.mvc.request.resolve.impl;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;

import com.then.springboot.mvc.request.resolve.IResolveHandler;
import com.then.springboot.utils.JsonUtils;

public class GetResolveHandler extends IResolveHandler{

	@Override
	public Object resolve(HttpServletRequest request, MethodParameter parameter) throws Exception{
		
		String paramString = request.getQueryString();
		
		if(StringUtils.isEmpty(paramString)) {
			return null;
		}
		
		Object result = null;
		
		try {
			paramString = URLDecoder.decode(paramString, "utf-8");
			result = JsonUtils.parseObject(paramString, parameter.getParameterType());
		} catch (Exception e) {
			result = super.getReflectionParam(request, parameter);
		}
		
		return result;
	}
	
	
	
}
