package com.sicau.ins_note.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/sharenote")
public class RecycleFavorShareNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/deleteFavorShareNote.do")
	@ResponseBody
	public NoteResult<Note> execute(String noteId){
		NoteResult<Note> result = noteService.recycleFavorShareNote(noteId);
		return result;
	}

}
