package com.sicau.ins_note.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@RequestMapping("/note")
@Controller
public class DeleteRecycleNoteController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/deleteRecycleNote.do")
	@ResponseBody
	public NoteResult<Object> execute(String noteId) {
		NoteResult<Object> result = noteService.deleteNoteById(noteId);
		return result;
	}

}
