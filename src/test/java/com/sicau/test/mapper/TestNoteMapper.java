package com.sicau.test.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.mapper.NoteMapper;
import com.sicau.test.common.BaseTest;

public class TestNoteMapper extends BaseTest{
	private NoteMapper noteMapper;
	@Before
	public void init() {
		noteMapper = super.getContext().getBean("noteMapper",NoteMapper.class);
	}
	
	@Test
	public void testNoteMapper() {
		List<Map<String,Object>> list = noteMapper.findByBookId("bfe73e2c289f4eab82b2b82ba17c1396");
		for(Map note:list) {
			System.out.println(note.get("note_id")+","+note.get("note_title"));
		}
	}
	
	@Test
	public void testNote() {
		Note note = noteMapper.findByNoteId("f113ba1324a04552a0d5a1ee65241cd2");
		System.out.println(note);
	}
	@Test
	public void testUpdate() {
		Note note = new Note();
		String noteId="1153295fd1a54a39b794980633bdc3d8";
		note.setNote_id(noteId);
		String title="Spring";
		note.setNote_title(title);
		String body="Spring AOP详解";
		note.setNote_body(body);
		Long time = System.currentTimeMillis();
		note.setNote_last_modify_time(time);
		
		int num=noteMapper.updateNote(note);
		System.out.println(num);
		
	}
	@Test
	public void testSave() {
		Note note = new Note();
		String noteId="7kdm9e15096e4dbvz5afef428c14l6h937";
		note.setNote_id(noteId);
		String notebookId="bfe73e2c289f4eab82b2b82ba17c1396";
		note.setNotebook_id(notebookId);
		String userId="48595f52b22c44859244f4004255b972";
		note.setUser_id(userId);
		String note_status_id="1";
		note.setNote_status_id(note_status_id);
		String note_type_id="1";
		note.setNote_type_id(note_type_id);
		String note_title="算法高级";
		note.setNote_title(note_title);
		String note_body="递归、回溯、贪心算法、动态规划";
		note.setNote_body(note_body);
		Long time = System.currentTimeMillis();
		note.setNote_create_time(time);
		note.setNote_last_modify_time(time);
		
		noteMapper.save(note);
		
		System.out.println(note);
	}
	
	@Test
	public void testUpdateNoteByMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title","Java");
		map.put("body","happiness");
		map.put("noteId","0e086e15000e4d3385afef193c18bb89");
		
		noteMapper.updateNoteByMap(map);
	}
	
	@Test
	public void testDeleteNotes() {
		Map<String,Object> map=new HashMap<String,Object>();
		String[] ids= {"99811d28c3e241579ed4176db78d77c2","c9b5df78baea4cd3b47ec26ca63708fc"};
		map.put("ids",ids);
		map.put("status",2);
		int n=noteMapper.deleteNotes(map);
		System.out.println(n);
	}

}

