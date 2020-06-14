package com.sicau.ins_note.controller.notebook;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.NoteBookService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/notebook")
public class DeleteNoteBookController {

	@Resource
	private NoteBookService noteBookService;
	
	@RequestMapping("/deleteNoteBook.do")
	@ResponseBody
	public NoteResult<Object> execute(String bookId) {
		NoteResult<Object> result = noteBookService.deleteNoteBookById(bookId);
		return result;
	}

}
