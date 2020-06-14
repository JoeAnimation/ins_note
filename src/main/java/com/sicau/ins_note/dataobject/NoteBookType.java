package com.sicau.ins_note.dataobject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteBookType implements Serializable {
	private static final long serialVersionUID = -3149135208833444851L;
	private String notebook_type_id;
	private String notebook_type_status;
	private String notebook_type_name;
	private String notebook_type_desc;
}
