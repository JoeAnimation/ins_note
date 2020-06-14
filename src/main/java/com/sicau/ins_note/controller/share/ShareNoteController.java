package com.sicau.ins_note.controller.share;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.ShareService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/share")
public class ShareNoteController {
	@Resource
	private ShareService ss;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult<Object> execute(String noteId){
		NoteResult<Object> result = ss.shareNote(noteId);
		return result;
	}

}