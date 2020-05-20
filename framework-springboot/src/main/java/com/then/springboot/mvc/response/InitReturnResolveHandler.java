package com.then.springboot.mvc.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Configuration
public class InitReturnResolveHandler implements InitializingBean{
	
	@Qualifier("requestMappingHandlerAdapter")
    @Autowired
    private RequestMappingHandlerAdapter adapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(returnValueHandlers);
		decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);
	}
	
	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for(int i=0; i< handlers.size(); i++) {
			HandlerMethodReturnValueHandler handler = handlers.get(i);
			if(handler instanceof RequestResponseBodyMethodProcessor) {
				ReturnResolveHandler returnResolveHandler = new ReturnResolveHandler(handler);
				handlers.set(i, returnResolveHandler);
				break;
			}
		}
    }

}
