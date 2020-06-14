package com.sicau.ins_note.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicau.ins_note.dataobject.SysLog;
import com.sicau.ins_note.mapper.SysLogMapper;
import com.sicau.ins_note.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogMapper sysLogMapper;

	public void saveLog(SysLog sysLog) {
		sysLogMapper.save(sysLog);
	}

	
}
