package com.sicau.ins_note.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/note")
public class UpdateNoteController {
	@Resource
	private NoteService ns;
	
	@RequestMapping("/update.do")
	@ResponseBody
	public NoteResult<Object> execute(String noteId,String noteTitle,String noteBody){
		NoteResult<Object> result = ns.updateNote(noteId,noteTitle,noteBody);
		return result;
	}

}
