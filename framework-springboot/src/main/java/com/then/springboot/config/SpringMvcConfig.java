package com.then.springboot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.then.springboot.mvc.request.ParamResolveHandler;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new ParamResolveHandler(applicationContext));
	}
	
	

}
