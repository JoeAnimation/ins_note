package com.sicau.ins_note.controller.notebook;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.service.NoteBookService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/book")
public class LoadNoteBooksController {
	@Resource
	private NoteBookService bs;
	@RequestMapping("/loadBooks.do")
	@ResponseBody
	public NoteResult<List<NoteBook>> execute(String userId){
		
		NoteResult<List<NoteBook>> result = bs.loadUserBooks(userId);
		
		return result;
		
	}

}