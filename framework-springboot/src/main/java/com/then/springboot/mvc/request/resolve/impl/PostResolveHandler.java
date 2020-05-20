package com.then.springboot.mvc.request.resolve.impl;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.then.core.utils.JsonUtils;
import com.then.springboot.mvc.request.resolve.IResolveHandler;

public class PostResolveHandler extends IResolveHandler {

	@Override
	public Object resolve(HttpServletRequest request, MethodParameter parameter) throws Exception {

		String contentType = request.getContentType() == null ? "" : request.getContentType();

		if (contentType.equals("application/json")) {
			StringBuilder sb = new StringBuilder();
			String readLine = "";

			try (BufferedReader br = request.getReader()) {
				while ((readLine = br.readLine()) != null) {
					sb.append(readLine);
				}
			}

			String paramString = sb.toString();
			if (StringUtils.isEmpty(paramString)) {
				return null;
			}
			return JsonUtils.parseObject(paramString, parameter.getParameterType());

		} else if (contentType.startsWith("multipart/form-data") && request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String paramName = parameter.getParameterName();
			Class<?> paramClass = parameter.getParameterType();
			if (paramClass == MultipartFile.class) {
				return multipartRequest.getFile(paramName);
			} else {
				return super.getReflectionParam(request, parameter);
			}
		}

		return super.getReflectionParam(request, parameter);
	}

}
