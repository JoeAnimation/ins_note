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
public class RenameNoteBookController {

	@Resource
	private NoteBookService noteBookService;
	
	@RequestMapping("/renameNoteBook.do")
	@ResponseBody
	public NoteResult<NoteBook> execute(String userId,String bookId,String bookName){
		NoteResult<NoteBook> result = noteBookService.renameNoteBook(userId,bookId,bookName);
		return result;
	}

}
