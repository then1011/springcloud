package com.then.springboot.mvc.request.resolve;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

import com.then.core.enums.SysCodeEnum;
import com.then.core.exception.SystemException;
import com.then.springboot.mvc.request.resolve.impl.GetResolveHandler;
import com.then.springboot.mvc.request.resolve.impl.PostResolveHandler;

public class ResolveHandlerFactory {

	private ResolveHandlerFactory() {
	}

	public static IResolveHandler getInstance(String method) {
		if (StringUtils.isBlank(method)) {
			throw new SystemException(SysCodeEnum.PARAM_FORMAT_ERROR);
		}

		if (method.equals(HttpMethod.GET.name())) {
			return new GetResolveHandler();
		} else if (method.equals(HttpMethod.POST.name())) {
			return new PostResolveHandler();
		} else {
			throw new SystemException(SysCodeEnum.PARAM_FORMAT_ERROR);
		}
	}

}
