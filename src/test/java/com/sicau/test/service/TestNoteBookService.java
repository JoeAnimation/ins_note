package com.sicau.test.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.service.NoteBookService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.test.common.BaseTest;

public class TestNoteBookService extends BaseTest{
	private NoteBookService bs;
	private NoteResult<List<NoteBook>> result;
	private NoteResult<Object> rs;
	@Before
	public void init() {
		bs = super.getContext().getBean("bs",NoteBookService.class);
	}
	@Test
	public void test() {
		result = bs.loadUserBooks("48595f52b22c44859244f4004255b972");
	    System.out.println(result.getStatus());
		System.out.println(result.getMsg());
	    for(NoteBook book : result.getData()) {
	    	System.out.println(book.getNotebook_name()+","+book.getNotebook_create_time());
	    }
	}
}

