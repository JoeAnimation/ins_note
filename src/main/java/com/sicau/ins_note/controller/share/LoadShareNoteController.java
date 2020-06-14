package com.sicau.ins_note.controller.share;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.service.ShareService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/note")
public class LoadShareNoteController {
	@Resource
	private ShareService shareService;
	
	@RequestMapping("/load_share.do")
	@ResponseBody
	public NoteResult<Share> execute(String shareId){
		NoteResult<Share> result = shareService.loadShareNote(shareId);
		return result;
	}
}
