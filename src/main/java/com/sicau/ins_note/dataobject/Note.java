package com.sicau.ins_note.dataobject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Note implements Serializable {
	private static final long serialVersionUID = -196968599892464245L;
	private String note_id;
	private String notebook_id;
	private String user_id;
	private String note_status_id;
	private String note_type_id;
	private String note_title;
	private String note_body;
	private Long note_create_time;
	private Long note_last_modify_time;
}
