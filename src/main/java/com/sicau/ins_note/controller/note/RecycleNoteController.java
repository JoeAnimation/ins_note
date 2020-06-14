package com.sicau.ins_note.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/note")
public class RecycleNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/recycle.do")
	@ResponseBody
	public NoteResult<Note> execute(String noteId){
		NoteResult<Note> result = noteService.recycleNote(noteId);
		return result;
	}
	
}
