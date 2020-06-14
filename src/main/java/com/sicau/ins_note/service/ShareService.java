package com.sicau.ins_note.service;

import java.util.List;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.util.NoteResult;

public interface ShareService {
	NoteResult<Object> shareNote(String noteId);
	
	/**
	 * NoteResult<List<Share>> searchNote(String keyword,int page);
	 */
	NoteResult<List<Share>> searchShareNote(String keyWord,int currentPage,int pageSize);
	//点击搜索后的收藏笔记，从而查看笔记信息
	NoteResult<Share> loadShareNote(String shareId);
	
}
