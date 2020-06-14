package com.sicau.ins_note.service;

import java.util.List;
import java.util.Map;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.util.NoteResult;

public interface NoteBookService {
	NoteResult<List<NoteBook>> loadUserBooks(String userId);
	
	NoteResult<NoteBook> addNoteBook(String userId,String title);
	
	NoteResult<NoteBook> renameNoteBook(String userId,String bookId,String bookName);
	
	NoteResult<Object> deleteNoteBookById(String bookId);
}
