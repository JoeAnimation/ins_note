package com.sicau.ins_note.dataobject;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
   private static final long serialVersionUID = -7934800286100778139L;
   private String user_id;
   private String user_name;
   private String user_password;
   private String user_desc;
   private String user_nick;
   private List<NoteBook> books;
   
}
