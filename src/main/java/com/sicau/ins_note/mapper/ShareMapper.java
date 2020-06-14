package com.sicau.ins_note.mapper;

import java.util.List;
import java.util.Map;

import com.sicau.ins_note.dataobject.Share;

public interface ShareMapper {
	void save(Share share);

	List<Share> findLikeTitle(Map params);

	Share findByShareId(String shareId);
	
	Share findByNoteId(String noteId);

}
