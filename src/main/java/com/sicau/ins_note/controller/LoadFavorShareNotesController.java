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
@RequestMapping("/sharenote")
public class LoadFavorShareNotesController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/loadFavorShareNotes.do")
	@ResponseBody
	public NoteResult<List<Map<Note,Object>>> execute(String userId) {
		NoteResult<List<Map<Note,Object>>> result = noteService.loadFavorShareNotes(userId);
		return result;
	}

}
