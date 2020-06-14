package com.sicau.test.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.service.UserService;
import com.sicau.ins_note.util.NoteResult;

public class TestUserService {
	private UserService service;
	@Before
	public void init() {
		String[] conf= {"conf/spring-mybatis.xml","conf/spring-mvc.xml","conf/spring-transaction.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		service = ac.getBean("userService",UserService.class);
	}
	@Test
	public void testUserService() {
		NoteResult<User> result = service.checkLogin("king","lyx1711");
		System.out.println(service.getClass().getName());
//	    System.out.println(result.getStatus());
//	    System.out.println(result.getMsg());
//	    System.out.println(result.getData());
	}
	@Test
	public void testSave() {
		String name="lucky";
		String password="lyx1996";
		String nick="曼巴";
		NoteResult<Object> result = service.addUser(name,password,nick);
		System.out.println(result.getStatus());
		System.out.println(result.getMsg());
	}
}

