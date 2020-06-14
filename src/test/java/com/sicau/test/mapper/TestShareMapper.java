package com.sicau.test.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sicau.ins_note.dataobject.Share;
import com.sicau.ins_note.mapper.NoteMapper;
import com.sicau.ins_note.mapper.ShareMapper;
import com.sicau.test.common.BaseTest;

public class TestShareMapper extends BaseTest{

	private ShareMapper shareMapper;
	@Before
	public void init() {
		shareMapper = super.getContext().getBean("shareMapper",ShareMapper.class);
	}
	
	@Test
	public void testShareMapper() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title","测试");
		map.put("begin",1);
		List<Share> list = shareMapper.findLikeTitle(map);
		for (Share share : list) {
			System.out.println(share);
		}
	}

}
