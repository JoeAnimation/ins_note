package com.sicau.ins_note.mapper;

import com.sicau.ins_note.dataobject.User;

public interface UserMapper {
	 //根据用户名查找用户
	  User findByName(String name);

	  //注册方法
	  void save(User user);

	  //修改密码方法
	  void change(User user);
}
