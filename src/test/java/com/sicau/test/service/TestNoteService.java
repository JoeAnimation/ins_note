package com.sicau.test.service;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.test.common.BaseTest;

public class TestNoteService extends BaseTest{
	private NoteService ns;
	private NoteResult<List<Map>> result;
	@Before
	public void init() {
		ns = super.getContext().getBean("ns",NoteService.class);
	}
	@Test
	public void test() {
		NoteResult<List<Map<String,Object>>> result = ns.loadBookNotes("12119052874c4341b85d6529e171ed83");
		System.out.println(result.getStatus());
		System.out.println(result.getMsg());
	}
	@Test
	public void testNote() {
		NoteResult<Note> result = ns.loadNote("2013b41944394109b4dbc2cc1b58a1b9");
		System.out.println(result.getStatus());
		System.out.println(result.getMsg());
		System.out.println(result.getData());
	}
	@Test
	public void testUpdate() {
		String noteId="051538a60f8e472c8765251a795bc88f";
		String noteTitle="Java应用";
		String noteBody="JDBC应用开发";
		NoteResult<Object> result = ns.updateNote(noteId,noteTitle,noteBody);
		System.out.println(result);
	}
	
	@Test
	public void testAddNote() {
		NoteResult<Note> result = ns.addNote("48595f52b22c44859244f4004255b972", "bfe73e2c289f4eab82b2b82ba17c1396", "黑盒测试");
		System.out.println(result);
	}
	
	@Test
	public void testDeleteNote() {
		ns.deleteNotes("c9b5df78baea4cd3b47ec26ca63708fc","id2","id3");
	}

}

