package com.sicau.ins_note.dataobject;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {
	private static final long serialVersionUID = 5284087326082685393L;

	private  Long logId;
	private  String userName;
	private  String className;
	private  String methodName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private  LocalDateTime createTime;
	private  String ip;
	private  String method;
	private  Integer times;
}
