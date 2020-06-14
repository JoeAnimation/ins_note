package com.sicau.test.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.mapper.NoteBookMapper;

public class TestNoteBookMapper {
	private ApplicationContext ac;
	private NoteBookMapper noteBookMapper;
	@Before
	public void init() {
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ac = new ClassPathXmlApplicationContext(conf);
		noteBookMapper = ac.getBean("noteBookMapper",NoteBookMapper.class);
	}
	
	@Test
	public void testSelect() {
		List<NoteBook> list = noteBookMapper.findByUserId("48595f52b22c44859244f4004255b972");
		for(NoteBook book : list) {
			System.out.println(book.getNotebook_id()+","+book.getNotebook_name());
		}
		
	}
	
	@Test
	public void testSave() {
		NoteBook noteBook = new NoteBook();
		noteBook.setNotebook_id("edh73e2c289f4eab24b2b82ba17c1363");
		noteBook.setUser_id("48595f52b22c44859244f4004255b972");
		noteBook.setNotebook_name("CentOS系统开机流程详解");
		noteBook.setNotebook_type_id("1");
		noteBook.setNotebook_create_time(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	
		noteBook.setNotebook_desc("虚拟技术解读");
		noteBookMapper.save(noteBook);
	}
}

