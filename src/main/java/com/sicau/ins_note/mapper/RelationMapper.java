package com.sicau.ins_note.mapper;

import java.util.List;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.dataobject.User;

public interface RelationMapper {
	//关联多个对象
	User findUserAndBooks(String userId);
	
	User findUserAndNoteBooks(String userId);
	//关联单个对象
	List<NoteBook> findNoteBookAndUser();
}
