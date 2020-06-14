package com.sicau.ins_note.service;

import java.util.List;
import java.util.Map;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.util.NoteResult;

public interface NoteService {
	//回收站笔记
	NoteResult<Note> recycleNote(String noteId);
	//将收藏笔记放入回车站
	NoteResult<Note> recycleFavorNote(String noteId);
	//将收藏分享笔记放入回车站
	NoteResult<Note> recycleFavorShareNote(String noteId);
	//恢复笔记
	NoteResult<Note> replayNote(String noteId,String bookId);
	
	NoteResult<List<Map<String,Object>>> loadBookNotes(String bookId);

	NoteResult<Note> loadNote(String noteId);
	
	NoteResult<Note> loadRecycleNote(String noteId);
	//加载收藏笔记信息
	NoteResult<Note> loadFavorNote(String noteId);
	//加载收藏分享笔记信息
	NoteResult<Note> loadFavorShareNote(String noteId);
	
	NoteResult<Object> updateNote(String noteId,String noteTitle,String noteBody);

	NoteResult<Note> addNote(String userId,String bookId,String title);
	
	//彻底删除笔记
	NoteResult<Object> deleteNoteById(String noteId);
	
	NoteResult<Note> moveNote(String noteId, String bookId);
	
	void deleteNotes(String... ids);
	
	//加载回收站笔记
	NoteResult<List<Map>> loadDeleteNotes(String userId);
	
	//收藏笔记
	NoteResult<Note> favorNote(String noteId);
	
	//加载收藏笔记
	NoteResult<List<Map<Note,Object>>> loadFavorNotes(String userId);
	
	//加载收藏分享笔记
	NoteResult<List<Map<Note,Object>>> loadFavorShareNotes(String userId);
	
	//收藏分享笔记
	NoteResult<Note> favorShareNote(String shareId);
}
