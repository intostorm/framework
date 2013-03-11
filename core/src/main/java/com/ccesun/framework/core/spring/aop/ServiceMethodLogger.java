package com.ccesun.framework.core.spring.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.ccesun.framework.util.StringUtils;

@Aspect
@Component
public class ServiceMethodLogger {

	private static final Log logger = LogFactory.getLog(ServiceMethodLogger.class);
	
	@Before("target(com.ccesun.framework.core.service.IService)")
	public void log(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		String argsString = getArgsString(args);
		
		if (logger.isDebugEnabled())
			logger.debug(String.format("SERVICE - %s@%s(%s)", className, methodName, argsString));
	}

	private String getArgsString(Object[] args) {
		String[] tmp = new String[args.length];
		for(int i = 0; i < args.length; i++) {
			tmp[i] = args[i] == null ? "null" : args[i].toString();
		}
		return StringUtils.join(tmp, ",");
	}
}
