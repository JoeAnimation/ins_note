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
public class Share implements Serializable {
	private static final long serialVersionUID = 8917042595686126867L;
	private String share_id;
	private String share_title;
	private String share_body;
	private String note_id;
}
