package com.sicau.ins_note.controller.notebook;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.service.NoteBookService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/notebook")
public class AddNoteBookController {
	@Resource
	private NoteBookService bs;
	
	@RequestMapping("/addNoteBook.do")
	@ResponseBody
	public NoteResult<NoteBook> execute(String userId,String title){
		NoteResult<NoteBook> result=bs.addNoteBook(userId,title);
		
		return result;
		
	}

}
