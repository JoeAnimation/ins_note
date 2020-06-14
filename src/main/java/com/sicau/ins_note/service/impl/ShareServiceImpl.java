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
import com.sicau.ins_note.mapper.RelationMapper;
import com.sicau.ins_note.mapper.ShareMapper;
import com.sicau.ins_note.service.ShareService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.ins_note.util.NoteUtil;

@Service("sm")
@Transactional
public class ShareServiceImpl implements ShareService{
	@Resource
	private ShareMapper shareMapper;
	@Resource
	private NoteBookMapper noteBookMapper;
	@Resource
	private NoteMapper noteMapper;

	//分享笔记处理
	public NoteResult<Object> shareNote(String noteId) {
		if (noteId == null) 
			throw new NoteException(ResultEnum.NOTE_ID_NOT_EXIST);
		
		NoteResult<Object> result = new NoteResult<Object>();
		Note note = noteMapper.findByNoteId(noteId);
		//检查该笔记是否被分享过
		Share has_share = shareMapper.findByNoteId(noteId);
		if (has_share != null) {
			if (has_share.getNote_id().equals(noteId)) {
				result.setStatus(1);
				result.setMsg("该笔记已被分享，不能重复分享");
				return result;
			}
		}
		
		Share share = new Share();
		String shareId=NoteUtil.md5(NoteUtil.createId());
		share.setShare_id(shareId);
		share.setNote_id(noteId);
		
		share.setShare_title(note.getNote_title());
		share.setShare_body(note.getNote_body());
		shareMapper.save(share);
		//update note useGeneratedKeys="true" keyProperty=""
		
		result.setStatus(0);
		result.setMsg("分享笔记成功!");
		
		return result;
	}

	public NoteResult<Share> loadShareNote(String shareId) {
		Share share =shareMapper.findByShareId(shareId);
		NoteResult<Share> result = new NoteResult<Share>();
		result.setStatus(0);
		result.setMsg("加载分享笔记成功");
		result.setData(share);
		return result;
	}

	//搜索分享笔记
	public NoteResult<List<Share>> searchShareNote(String keyWord, int currentPage, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		params.put("begin", (currentPage-1)*pageSize);
		params.put("pageSize", pageSize);
		
		List<Share> list = shareMapper.findLikeTitle(params);
		NoteResult<List<Share>> result = new NoteResult<List<Share>>();
		if (list.isEmpty()) {
			result.setStatus(1);
			result.setMsg("该分享笔记不存在");
			return result;
		}
		result.setStatus(0);
		result.setMsg("搜索成功!");
		result.setData(list);
		
		return result;
	}

	/**
	 * public NoteResult<List<Share>> searchNote(String keyword, int page) {
		String title="%"+keyword+"%";
		//计算抓取记录的起点
		int begin=(page-1)*3;
		Map<String,Object> params= new HashMap<String,Object>();
		params.put("title",title);
		params.put("begin",begin);
		List<Share> list = shareMapper.findLikeTitle(params);

		NoteResult<List<Share>> result = new NoteResult<List<Share>>();
		if (list == null) {
			result.setStatus(1);
			result.setMsg("该分享笔记不存在");
			return result;
		}
		result.setStatus(0);
		result.setMsg("搜索成功!");
		result.setData(list);
		
		return result;
	}
	*/
	
}

