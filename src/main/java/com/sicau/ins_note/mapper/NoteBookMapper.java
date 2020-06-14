package com.sicau.ins_note.mapper;

import java.util.List;

import com.sicau.ins_note.dataobject.NoteBook;

public interface NoteBookMapper {
	List<NoteBook> findByUserId(String userId);
	
	void save(NoteBook noteBook);
	
	int updateNoteBook(NoteBook noteBook);

	NoteBook findById(String bookId);

	int deleteNoteBook(String bookId);
}
