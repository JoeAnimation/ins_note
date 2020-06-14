package com.sicau.test.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.mapper.UserMapper;

public class TestUserMapper {
	private ApplicationContext ac;
	private UserMapper userMapper;
	@Before
	public void init() {
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ac = new ClassPathXmlApplicationContext(conf);
		userMapper =  ac.getBean("userMapper",UserMapper.class);
	}
	
	@Test
	public void testUserMapper() {
		  User user = userMapper.findByName("king");
		  System.out.println(user);
	}
	
	@Test
	public void testSave() {
		User user = new User();
	    user.setUser_id("9863f744-7283-9b2h-8uf5-4a6757we372t");
	    user.setUser_name("cafebabe");
	    user.setUser_password("flower");
	    user.setUser_desc("romantic");
	    user.setUser_nick("小可爱");
	    userMapper.save(user);
		System.out.println(user);
	}

  
}
