package com.sicau.ins_note.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ResultEnum {
	FAILURE(1, "密码加密失败！"),
	NOTE_ID_NOT_EXIST(2,"noteId不存在"),
	NOTEBOOK_ID_NOT_EXIST(3,"notebookId不存在"),
	NOTE_HAS_FAVORED(4,"该笔记已经分享过，不能重复分享！"),
	SHARE_ID_NOT_EXIST(5,"shareId不存在"),
	DELETE_NOTE_FAILED(6,"删除笔记失败"),
	USER_ID_NOT_EXIST(7,"用户id不存在"),
	NOTE_TITLE_IS_NULL(8,"笔记标题为空"),
	;
	private Integer code;
	private String msg;
    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
