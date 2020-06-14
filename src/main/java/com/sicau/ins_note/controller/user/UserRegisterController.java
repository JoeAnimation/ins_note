package com.sicau.ins_note.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.UserService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserRegisterController {
	@Resource
	private UserService service;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult<Object> execute(String name,String password,String nick){
		NoteResult<Object> result = service.addUser(name,password,nick);
		
		return result;
		
	}
 
}
