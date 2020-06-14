package com.sicau.ins_note.aspect;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 保存异常信息到日志中
 * @author Longyx
 *
 */
@Component//扫描到spring容器里面
@Slf4j
@Aspect//将该类作为切面组件
public class ExceptionBean {
	//指定异常通知和切入点表达式 e是目标组件抛出来的异常对象
	@AfterThrowing(throwing="e",pointcut="within(com.sicau.ins_note.service..*)")
	public void execute(Exception e) {
		try {
			//将e对象信息写入sys_error.log文件
			FileWriter fw = new FileWriter("F:\\sys_error.log",true);
			//利用pw对象写入异常信息
			PrintWriter pw = new PrintWriter(fw);
			//获取异常发生时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			//打印异常头部描述
			pw.println("******************************");
			pw.println("*发生时间："+time);
			pw.println("*异常类型："+e);
			pw.println("**********异常详情*************");
			//将异常栈信息打印
			log.error("出错啦，异常信息： {}",pw);
//			e.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception ex) {
			log.error("捕获异常信息，异常信息: {}",ex.getMessage());
		}
	}
}

