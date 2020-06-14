package com.sicau.ins_note.controller.share;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.service.ShareService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/sharenote")
public class FavorShareNoteController {

	@Resource
	private NoteService noteService;
	
	@RequestMapping("/favorShareNote.do")
	@ResponseBody
	public NoteResult<Note> execute(String shareId){
		NoteResult<Note> result = noteService.favorShareNote(shareId);
		return result;
	}

}
