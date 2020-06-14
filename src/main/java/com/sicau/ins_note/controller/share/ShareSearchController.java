package com.sicau.ins_note.controller.share;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.common.PageResult;
import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.service.ShareService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/share")
public class ShareSearchController {
	@Resource
	private ShareService shareService;
	
	@RequestMapping("/search.do")
	@ResponseBody
	public NoteResult<List<Share>> execute(String keyWord,int currentPage){
		NoteResult<List<Share>> result = shareService.searchShareNote(keyWord,currentPage,PageResult.getPageSize());
		return result;
	}
}
