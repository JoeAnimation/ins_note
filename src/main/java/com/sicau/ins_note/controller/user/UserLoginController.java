package com.sicau.ins_note.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.service.UserService;
import com.sicau.ins_note.util.NoteResult;

@Controller
//匹配请求路径
@RequestMapping("/user")
public class UserLoginController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public NoteResult<User> execute(String name,String password){
		
		System.out.println(name + "," + password);
		NoteResult<User> result = userService.checkLogin(name,password);
		
		return result;
		
	}

}