package com.sicau.ins_note.dataobject;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteBook implements Serializable {
	private static final long serialVersionUID = -3486555275414323336L;
	private String notebook_id;
	private String user_id;
	private String notebook_type_id;
	private String notebook_name;
	private String notebook_desc;
	private Timestamp notebook_create_time;
	private User user;
	
}
