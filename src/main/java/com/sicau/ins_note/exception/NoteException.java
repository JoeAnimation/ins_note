package com.sicau.ins_note.exception;

import com.sicau.ins_note.enums.ResultEnum;

import lombok.Data;

@Data
public class NoteException extends RuntimeException{
	private static final long serialVersionUID = -7675738082203687147L;
	private Integer code;
	
	public NoteException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
	
    public NoteException(Integer code, String message){
        super(message);
        this.code = code;
    }

}
