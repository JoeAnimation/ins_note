package com.sicau.ins_note.controller.note;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/note")
public class LoadNotesController {
	@Resource
	private NoteService ns;
	
	@RequestMapping("/loadnotes.do")
	@ResponseBody
	public NoteResult<List<Map<String,Object>>> execute(String bookId){
		NoteResult<List<Map<String, Object>>> result = ns.loadBookNotes(bookId);
		return result;
	}

}
