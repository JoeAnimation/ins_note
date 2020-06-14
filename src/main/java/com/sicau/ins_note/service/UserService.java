package com.sicau.ins_note.service;

import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.util.NoteResult;

public interface UserService {
	NoteResult<User> checkLogin(String name,String password);
	NoteResult<Object> addUser(String name,String password,String nick);
	NoteResult<Object> changePassword(String userName,String last_password,String final_password);
}
