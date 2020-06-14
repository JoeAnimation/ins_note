package com.sicau.test.mapper;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.mapper.RelationMapper;
import com.sicau.test.common.BaseTest;

public class TestRelationMapper extends BaseTest{
	private RelationMapper relationMapper;
	
	@Before
	public void init() {
		relationMapper = super.getContext().getBean("relationMapper",RelationMapper.class);
	}
	
	@Test
	public void testMany() {
		User user = relationMapper.findUserAndNoteBooks("48595f52b22c44859244f4004255b972");
		System.out.println("==========用户信息=========");
		System.out.println("名字:"+user.getUser_name());
		System.out.println("笔记本数量："+user.getBooks().size());
		System.out.println("==========笔记本列表=======");
		for(NoteBook book:user.getBooks()) {
			System.out.println(book.getNotebook_name());
		}	
	}
	
	@Test 
	public void testOne() {
		List<NoteBook> books = relationMapper.findNoteBookAndUser();
		for(NoteBook book:books) {
			System.out.println(book.getNotebook_name());
			if(book.getUser()!=null) {
				System.out.println(book.getUser().getUser_name());
			}
		}
	}
}