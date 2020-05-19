package com.then.springboot.mvc.request.resolve.impl;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;

import com.then.springboot.mvc.request.resolve.IResolveHandler;
import com.then.springboot.utils.JsonUtils;

public class PostResolveHandler extends IResolveHandler{

	@Override
	public Object resolve(HttpServletRequest request, MethodParameter parameter) throws Exception {
		
		String contentType = request.getContentType();
		
		if("application/json".equalsIgnoreCase(contentType)) {
			StringBuilder sb = new StringBuilder();
			String readLine = "";
			
			try(BufferedReader br = request.getReader()) {
				while((readLine = br.readLine()) != null) {
					sb.append(readLine);
				}
			}
			
			String paramString = sb.toString();
			if(StringUtils.isEmpty(paramString)) {
				return null;
			}
			return JsonUtils.parseObject(paramString, parameter.getParameterType());
			
		}else if("multipart/form-data".equalsIgnoreCase(contentType)) {
			
		}
		
		return null;
	}

}
