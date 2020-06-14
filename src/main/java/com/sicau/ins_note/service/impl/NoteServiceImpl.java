package com.sicau.ins_note.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sicau.ins_note.dataobject.Note;
import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.enums.ResultEnum;
import com.sicau.ins_note.exception.NoteException;
import com.sicau.ins_note.mapper.NoteBookMapper;
import com.sicau.ins_note.mapper.NoteMapper;
import com.sicau.ins_note.mapper.ShareMapper;
import com.sicau.ins_note.service.NoteService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.ins_note.util.NoteUtil;

import lombok.extern.slf4j.Slf4j;
import sun.print.resources.serviceui;
import sun.util.logging.resources.logging;

@Service("ns")
@Slf4j
public class NoteServiceImpl implements NoteService{
	@Resource
    private NoteMapper noteMapper;
	@Resource
	private ShareMapper shareMapper;
	
	public NoteResult<List<Map<String,Object>>> loadBookNotes(String bookId) {
		List<Map<String,Object>> list = noteMapper.findByBookId(bookId);
		NoteResult<List<Map<String,Object>>> result = new NoteResult<List<Map<String,Object>>>();
		result.setStatus(0);
		result.setMsg("加载笔记成功!");
		result.setData(list);
		
		return result;
	}
	
	//加载分享笔记内容
	public NoteResult<Note> loadNote(String noteId) {
		NoteResult<Note> result = new NoteResult<Note>();
		Note note = noteMapper.findByNoteId(noteId);
		if(note == null) {
			result.setStatus(1);
			result.setMsg("该笔记不存在!");
			return result;
		}
		
		result.setStatus(0);
		result.setMsg("加载笔记内容成功");
		result.setData(note);
		
		return result;
	}
	
	//加载回收站笔记内容
	public NoteResult<Note> loadRecycleNote(String noteId) {
		if (noteId == null) {
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		}
		NoteResult<Note> result = new NoteResult<Note>();
		Note note = noteMapper.findByNoteId(noteId);
		if(note == null) {
			result.setStatus(1);
			result.setMsg("该回收站笔记不存在!");
			return result;
		}
		
		result.setStatus(0);
		result.setMsg("加载回收站笔记内容成功");
		result.setData(note);
		
		return result;
	}
	
	//加载收藏笔记内容
	public NoteResult<Note> loadFavorNote(String noteId) {
		if (noteId == null)
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		
		NoteResult<Note> result = new NoteResult<Note>();
		Note note = noteMapper.findByNoteId(noteId);
		if(note == null) {
			result.setStatus(1);
			result.setMsg("该收藏笔记不存在!");
			return result;
		}
		
		result.setStatus(0);
		result.setMsg("加载收藏笔记内容成功");
		result.setData(note);
		
		return result;
	}
	
	//加载收藏分享笔记信息
	public NoteResult<Note> loadFavorShareNote(String noteId){
		if (noteId == null)
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		
		NoteResult<Note> result = new NoteResult<Note>();
		Note note = noteMapper.findByNoteId(noteId);
		if(note == null) {
			result.setStatus(1);
			result.setMsg("该收藏分享笔记不存在!");
			return result;
		}
		
		result.setStatus(0);
		result.setMsg("加载收藏分享笔记内容成功");
		result.setData(note);
		
		return result;
	}
	
	@Transactional
	public NoteResult<Object> updateNote(String noteId, String noteTitle, String noteBody) {
		if (noteTitle == null)
			throw new NoteException(ResultEnum.NOTE_TITLE_IS_NULL);
		
		NoteResult<Object> result = new NoteResult<Object>();
		Note note = new Note();
		note.setNote_id(noteId);
		note.setNote_title(noteTitle);
		note.setNote_body(noteBody);
		Long time=System.currentTimeMillis();
		note.setNote_last_modify_time(time);
		
		int rows = noteMapper.updateNote(note);
		
		if(rows>=1) {
			result.setStatus(0);
			result.setMsg("更新笔记成功");
			result.setData(note);
			return result;
		}
		result.setStatus(1);
		result.setMsg("更新笔记出错啦");
		
		return result;
	}
	
	//添加笔记事件
	public NoteResult<Note> addNote(String userId, String bookId, String title) {
		NoteResult<Note> result = new NoteResult<Note>();
		Note note = new Note();
		//用户ID
		note.setUser_id(userId);
		//笔记本ID
		note.setNotebook_id(bookId);
		
		//笔记标题
		note.setNote_title(title);
		
		String noteId=NoteUtil.md5(NoteUtil.createId());
		note.setNote_id(noteId);
		//笔记内容
		note.setNote_body("");
		
		Long time=System.currentTimeMillis();
		note.setNote_create_time(time);
		
		note.setNote_last_modify_time(time);
		//状态：1-normal 2-delete
		note.setNote_status_id("1");
		//类型：1-normal 2-favorite 3-share
		note.setNote_type_id("1");
		
		List<Map<String,Object>> list = noteMapper.findByBookId(bookId);
		for (Map<String, Object> map : list) {
			if (map.get("note_title").equals(title)) {
				log.error("该笔记："+note.getNote_title()+" 已存在!");
				result.setStatus(1);
				result.setMsg("该笔记名称已存在!");
				return result;
			}
		}
		
		noteMapper.save(note);
		
		result.setStatus(0);
		result.setMsg("添加笔记成功!");
		result.setData(note);
		
		return result;
	}

	//转移笔记
	public NoteResult<Note> moveNote(String noteId, String bookId) {
		Note note = new Note();
		note.setNote_id(noteId);//设置笔记ID
		note.setNotebook_id(bookId);//设置笔记本ID
//		int rows = noteMapper.updateBookId(note);//更新
		int rows = noteMapper.dynamicUpdate(note);
		//创建返回结果
		NoteResult<Note> result = new NoteResult<Note>();
		if(rows<1){
			result.setStatus(1);
			result.setMsg("转移笔记失败!");
			return result;
		}
		result.setStatus(0);
		result.setMsg("转移笔记成功");
		
		return result;
	}
	
	@Transactional
	public void deleteNotes(String... ids) {
		//String... 相当于String[]
		for(String id:ids) {
			int n = noteMapper.deleteNoteById(id);
			if(n<1) {
				log.error("删除笔记出错啦，异常信息:",ResultEnum.DELETE_NOTE_FAILED);
			}
		}
	}
	
	//加载回收站笔记
	public NoteResult<List<Map>> loadDeleteNotes(String userId) {
		NoteResult<List<Map>> result = new NoteResult<List<Map>>();
		List<Map> list = noteMapper.findDeleteNote(userId);
		result.setStatus(0);
		result.setData(list);
		result.setMsg("加载回收站笔记成功");
		return result;
	}

	//恢复笔记
	public NoteResult<Note> replayNote(String noteId,String bookId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noteId", noteId);
		map.put("bookId", bookId);
		noteMapper.replayNote(map);
		
		NoteResult<Note> result = new NoteResult<Note>();
		result.setStatus(0);
		result.setMsg("恢复笔记成功");
		
		return result;
	}
	//回收站笔记
	public NoteResult<Note> recycleNote(String noteId) {
		noteMapper.updateStatus(noteId);
		NoteResult<Note> result = new NoteResult<Note>();
		result.setStatus(0);
		result.setMsg("将笔记放入回收站成功");
		
		return result;
	}
	
	//将收藏笔记放入回车站
	public NoteResult<Note> recycleFavorNote(String noteId) {
		if (noteId == null)
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);

		noteMapper.updateStatus(noteId);
		
		NoteResult<Note> result = new NoteResult<Note>();
		result.setStatus(0);
		result.setMsg("将收藏笔记放入回收站成功");
		
		return result;
	}
	
	//将收藏分享笔记放入回车站
	public NoteResult<Note> recycleFavorShareNote(String noteId){
		if (noteId == null)
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);

		noteMapper.updateStatus(noteId);
		
		NoteResult<Note> result = new NoteResult<Note>();
		result.setStatus(0);
		result.setMsg("将收藏分享笔记放入回收站成功");
		
		return result;
	}
	//实现彻底删除笔记
	public NoteResult<Object> deleteNoteById(String noteId) {
		if(noteId == null)
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		
		Note note = new Note();
		note.setNote_id(noteId);
		note.setNote_status_id("2");
		
		int rows = noteMapper.deleteNote(note);
		
		NoteResult<Object> result = new NoteResult<Object>();
		if(rows >= 1){
			result.setStatus(0);
			result.setMsg("彻底删除笔记成功");
		}else{
			result.setStatus(1);
			result.setMsg("彻底删除笔记异常");
		}
		return result;
	}

	//收藏笔记
	public NoteResult<Note> favorNote(String noteId) {
		if (noteId == null) 
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		
		int rows = noteMapper.updateNoteStatus(noteId);
		NoteResult<Note> result = new NoteResult<Note>();
		if (rows < 1) {
			result.setStatus(1);
			result.setMsg("收藏笔记异常");
			return result;
		}
		result.setStatus(0);
		result.setMsg("收藏笔记成功");
		
		return result;
	}
	
	//查询收藏笔记
	public NoteResult<List<Map<Note, Object>>> loadFavorNotes(String userId) {
		NoteResult<List<Map<Note,Object>>> result = new NoteResult<List<Map<Note,Object>>>();
		List<Map<Note,Object>> list = noteMapper.findFavorNotes(userId);
		result.setStatus(0);
		result.setData(list);
		result.setMsg("加载收藏笔记成功");
		return result;
	}
	
	//查询收藏分享笔记
	public NoteResult<List<Map<Note, Object>>> loadFavorShareNotes(String userId) {
		if (userId == null)
			throw new NoteException(ResultEnum.USER_ID_NOT_EXIST);
		
		NoteResult<List<Map<Note,Object>>> result = new NoteResult<List<Map<Note,Object>>>();
		List<Map<Note,Object>> list = noteMapper.findFavorShareNotes(userId);
		
		result.setStatus(0);
		result.setData(list);
		result.setMsg("加载收藏笔记成功");
		return result;
	}

	//收藏分享笔记
	public NoteResult<Note> favorShareNote(String shareId) {
		if (shareId == null)
			throw new NoteException(ResultEnum.SHARE_ID_NOT_EXIST);
		
		NoteResult<Note> result = new NoteResult<Note>();
		
		Share share = shareMapper.findByShareId(shareId);
		//检查该笔记是否被分享过
		Note note = noteMapper.findByNoteId(share.getNote_id());
		if (note.getNote_status_id().equals("4")) {
			result.setStatus(2);
			result.setMsg("该分享笔记已被收藏，不能重复收藏");
			return result;
		}
	
		int rows = noteMapper.updateShareStatus(share.getNote_id());

		if (rows < 1) {
			result.setStatus(1);
			result.setMsg("收藏分享笔记异常");
			return result;
		}
		
		result.setStatus(0);
		result.setMsg("收藏分享笔记成功");
		
		return result;
	}

}


