package com.sicau.ins_note.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/note")
public class LoadFavorNotesController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/loadFavorNotes.do")
	@ResponseBody
	public NoteResult<List<Map<Note,Object>>> execute(String userId) {
		NoteResult<List<Map<Note,Object>>> result = noteService.loadFavorNotes(userId);
		return result;
	}

}
