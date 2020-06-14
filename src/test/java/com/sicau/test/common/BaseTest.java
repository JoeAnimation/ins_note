package com.sicau.test.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTest {
	public ApplicationContext getContext() {
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml","conf/spring-aop.xml","conf/spring-transaction.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		return ac;
	}
}