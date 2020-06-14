package com.sicau.ins_note.mapper;

import java.util.List;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.dataobject.NoteBookType;
import com.sicau.ins_note.util.NoteResult;

public interface NoteBookTypeMapper {
	
	List<NoteBookType> findAllType();
	
	
	NoteResult<Object> updateBook(NoteBook book);
}
