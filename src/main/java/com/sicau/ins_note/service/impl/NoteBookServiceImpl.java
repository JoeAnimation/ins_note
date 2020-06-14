package com.sicau.ins_note.service.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sicau.ins_note.dataobject.NoteBook;
import com.sicau.ins_note.enums.ResultEnum;
import com.sicau.ins_note.exception.NoteException;
import com.sicau.ins_note.mapper.NoteBookMapper;
import com.sicau.ins_note.mapper.NoteBookTypeMapper;
import com.sicau.ins_note.service.NoteBookService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.ins_note.util.NoteUtil;

import lombok.extern.slf4j.Slf4j;

@Service("bs")
@Slf4j
public class NoteBookServiceImpl implements NoteBookService{
	@Resource
	private NoteBookMapper noteBookMapper;

	@Resource
	private NoteBookTypeMapper noteBookTypeMapper;
	
	public NoteResult<List<NoteBook>> loadUserBooks(String userId) {
		List<NoteBook> list= noteBookMapper.findByUserId(userId);
	
		if (list.isEmpty()) {
			log.error("该笔记本列表没有记录");
		}
		
		//构建返回结果(result)
		NoteResult<List<NoteBook>> result = new NoteResult<List<NoteBook>>();
		
		result.setStatus(0);
		result.setMsg("查询笔记本成功");
		result.setData(list);
		
		return result;
	}
	
	//新增笔记本
	public NoteResult<NoteBook> addNoteBook(String userId, String title) {
		NoteBook book=new NoteBook();
		
		String bookId=NoteUtil.md5(NoteUtil.createId());
		book.setNotebook_id(bookId);
		
		book.setUser_id(userId);
		
		book.setNotebook_name(title);
		
		book.setNotebook_type_id("1");
		
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		book.setNotebook_create_time(time);
		
		noteBookMapper.save(book);
		
		NoteResult<NoteBook> result = new NoteResult<NoteBook>();
		
		result.setStatus(0);
		result.setMsg("添加笔记本成功");
		result.setData(book);
		return result;
	}
	
	public NoteResult<NoteBook> renameNoteBook(String userId,String bookId,String bookName) {
		NoteBook noteBook = new NoteBook();
		
		noteBook.setUser_id(userId);
		noteBook.setNotebook_id(bookId);
		noteBook.setNotebook_name(bookName);
		
		int records = noteBookMapper.updateNoteBook(noteBook);
		
		NoteResult<NoteBook> result = new NoteResult<NoteBook>();
		if (records<1) {
			result.setStatus(1);
			result.setMsg("重命名笔记本出错啦!");
		}
		result.setStatus(0);
		result.setMsg("重命名笔记本成功");
		result.setData(noteBook);
		
		return result;
	}
	
	//彻底删除笔记本
	public NoteResult<Object> deleteNoteBookById(String bookId) {
		if(bookId == null)
			throw new NoteException(ResultEnum.NOTEBOOK_ID_NOT_EXIST);
		int rows = noteBookMapper.deleteNoteBook(bookId);
		
		NoteResult<Object> result = new NoteResult<Object>();
		if (rows < 1) {
			result.setStatus(1);
			result.setMsg("彻底删除笔记本失败");
		}
		result.setStatus(0);
		result.setMsg("彻底删除笔记本成功");
		
		return result;
	}
	
 }

