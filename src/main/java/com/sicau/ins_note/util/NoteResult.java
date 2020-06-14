package com.sicau.ins_note.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoteResult<T> implements Serializable {
	private static final long serialVersionUID = -4878395347126892446L;
	private int status;
	private String msg;
	private T data;
 
}
