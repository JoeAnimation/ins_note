package com.sicau.ins_note.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicau.ins_note.service.UserService;
import com.sicau.ins_note.util.NoteResult;

@Controller
@RequestMapping("/user")
public class ChangeUserPasswordController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/change.do")
	@ResponseBody
	public NoteResult<Object> execute(String userName, String last_password, String final_password){
		NoteResult<Object> result = userService.changePassword(userName, last_password, final_password);
		return result;
	}
}