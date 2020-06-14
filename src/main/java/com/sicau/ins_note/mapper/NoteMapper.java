package com.sicau.ins_note.mapper;

import java.util.List;
import java.util.Map;

import com.sicau.ins_note.dataobject.Note;

public interface NoteMapper {
	//恢复笔记
	void replayNote(Map params);
	
	List<Map<String,Object>> findByBookId(String bookId);

	Note findByNoteId(String noteId);

	int updateNote(Note note);

	void save(Note note);

	//删除(及移动)笔记事件（即动态的更新笔记的状态id为删除状态）
	int dynamicUpdate(Note note);
	
	//根据笔记ID删除笔记
	int deleteNoteById(String noteId);

	int updateNoteByMap(Map<String,Object> map);

	//批量删除笔记
	int deleteNotes(Map<String,Object> map);

	//彻底删除笔记
	int deleteNote(Note note);

	//查找回收站笔记
	List<Map> findDeleteNote(String userId);
	
	//将笔记放入回收站
	void updateStatus(String noteId);
	
	//收藏笔记
	int updateNoteStatus(String noteId);
	
	//查询收藏笔记
	List<Map<Note, Object>> findFavorNotes(String userId);
	
	//查询收藏分享笔记
	List<Map<Note, Object>> findFavorShareNotes(String userId);
	
	//收藏分享笔记
	int updateShareStatus(String noteId);
}
