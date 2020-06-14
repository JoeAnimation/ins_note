package com.sicau.ins_note.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sicau.ins_note.dataobject.SysLog;
import com.sicau.ins_note.service.SysLogService;
import com.sicau.ins_note.util.IdGeneratorUtil;
import com.sicau.ins_note.util.IpUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 性能审计用来查看执行各个业务需要的时间
 * 切面，用来性能测试 在service执行之前和service执行之后
 * @author Longyx
 *
 */
@Component//扫描,起到定义<bean>的作用
@Aspect//指定为切面
@Slf4j
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
    
	@Around("within(com.sicau.ins_note.service..*) && !within(com.sicau.ins_note.service.SysLogService) && !within(com.sicau.ins_note.service.impl.SysLogServiceImpl)")//指定通知类型/切入点表达式
	public Object audit(ProceedingJoinPoint point) {
		Object result = null;
		try{
			Long startTime = System.currentTimeMillis();
			result = point.proceed();//执行service
			Signature str = point.getSignature();//获取service名称
			Long endTime=System.currentTimeMillis();
			Long time = endTime - startTime;
			System.out.println(str+"耗时:"+time);
			//保存日志
			saveLog(point,time);
		} catch (Throwable e) {
			if (e.getMessage() != null) {
				log.error("系统异常!,异常信息: {}",e.getLocalizedMessage());
			}
		}
		return result;
	}
	
	private void saveLog(ProceedingJoinPoint joinPoint, Long time){
		SysLog sysLog = new SysLog();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = attributes.getRequest();
	    log.info("<=====================================================");
        log.info("请求来源： =》" + request.getRemoteAddr());
        log.info("请求URL：" + request.getRequestURL().toString());
        log.info("请求方式：" + request.getMethod());
        log.info("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数：" + Arrays.toString(joinPoint.getArgs()));
        log.info("=====================================================>");
		try {
			Long timeStart=System.currentTimeMillis();
			IdGeneratorUtil snowFlake = new IdGeneratorUtil(1, 3);
			Long logId = snowFlake.nextId();
			
			//获取登录名
			String[] names = request.getParameterValues("name");
			
			//获取请求类名
			String className = joinPoint.getTarget().getClass().getName();
			
			//获取请求方法名
			 String methodName = joinPoint.getSignature().getName();
			 
			//请求方式
			String method = request.getMethod();
			
			sysLog.setLogId(logId);
			
			for (String name : names) {
				sysLog.setUserName(name);
			}
			sysLog.setClassName(className);
			sysLog.setMethodName(methodName);
			
			//设置IP地址
			sysLog.setIp(IpUtils.getIpAddr(request));
			sysLog.setMethod(method);
			Long timeEnd = System.currentTimeMillis();
			log.info("耗时（毫秒）：" +  (timeEnd - timeStart));
			//执行时长
			sysLog.setTimes(Integer.valueOf((int) (System.currentTimeMillis() - timeStart)));
			
			//保存系统日志
			sysLogService.saveLog(sysLog);
		} catch (Exception e) {
			if (e.getMessage() != null) {
				log.error("错误信息："+e.getLocalizedMessage());
			} 
		}
	}
	
}