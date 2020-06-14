package com.sicau.ins_note.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mysql.cj.log.Log;
import com.mysql.cj.util.StringUtils;
import com.sicau.ins_note.dataobject.User;
import com.sicau.ins_note.mapper.UserMapper;
import com.sicau.ins_note.redis.RedisCacheService;
import com.sicau.ins_note.service.UserService;
import com.sicau.ins_note.util.NoteResult;
import com.sicau.ins_note.util.NoteUtil;
import com.sicau.ins_note.util.Sha1Util;

import lombok.extern.slf4j.Slf4j;

@Service("userService")  //扫描Spring容器
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
	@Resource
	private RedisCacheService redisCacheService;
	@Resource
	private RedisTemplate<String,User> redisTemplate;
	@Resource
    private UserMapper userMapper;
	public NoteResult<User> checkLogin(String name, String password) {
		if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(password)) 
			return null;

	    String key = "user-"+name;
	    ValueOperations<String, User> operations = redisTemplate.opsForValue();
	    //接收结果数据
	  	NoteResult<User> result = new NoteResult<User>();
	  	String sha1Password = Sha1Util.string2Sha1(password);
	  	
	  	RedisConnection connection = null;
	  	//先查询缓存服务器
	  	User user = operations.get(key);
	  	if (user != null) {
	  		if (!name.equals(user.getUser_name()) || !sha1Password.equals(user.getUser_password())) {
				result.setStatus(3);
				result.setMsg("用户名或密码错误！");
				
				return result;
			}
			//缓存中有数据
			log.info("从缓存中获取用户信息 >> "+"id："+user.getUser_id()+", username："+user.getUser_name(),",password："+user.getUser_password());
			
			result.setStatus(0);
			result.setMsg("缓存命中");
			result.setData(user);
			return result;
		}
		
	  	try {
	  		//申请分布式锁
	  		connection = redisTemplate.getConnectionFactory().getConnection();
	  		if (connection.setNX("lock".getBytes(), "1".getBytes())) {
				//给分布式锁设置一个超时时间
	  			connection.expire("lock".getBytes(), 5);
	  			
	  			log.info("缓存中没有，到数据库中查询用户信息");
	  			user = userMapper.findByName(name);
	  			
	  			 //如果用户不存在
	  			if(user==null) {
	  				result.setStatus(1);
	  				result.setMsg("该用户不存在!");
	  				return result;
	  			}
	  			//用户存在，比较密码
//	  			String md5PassWord = NoteUtil.md5(password);
	  			
	  			if(!user.getUser_password().equals(sha1Password)) {
	  				result.setStatus(2);
	  				result.setMsg("密码错误!");
	  				return result;
	  			}
	  			
	  			//将结果放入缓存中
	  			operations.set(key, user, 5, TimeUnit.MINUTES);
	  			//redisTemplate.expire(key, 30, TimeUnit.MINUTES);
	  			//释放分布式锁
	  			connection.del("lock".getBytes());
			}else {
				try {
					Thread.sleep(60);
					checkLogin(name, password);
				} catch (InterruptedException e) {
					if (e.getMessage() != null) {
						log.error("异常信息:"+e.getLocalizedMessage());
					}
				}finally {
					if (connection != null) {
						connection.close();
					}
				}
			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				log.info("异常信息："+e.getLocalizedMessage());
			}
			
		}
	  	
	    //用户存在且密码正确
		result.setStatus(0);
		result.setMsg("登录成功!");
		result.setData(user);
		
		return result;
	}

	public NoteResult<Object> addUser(String name, String password, String nick) {
		NoteResult<Object> result = new NoteResult<Object>();
		//用户检测
		User hasUser = userMapper.findByName(name);
		//用户名存在
		if(hasUser!=null) {
			result.setStatus(1);
			result.setMsg("用户名被占用");
			return result;
		}
		
		//添加用户
		User user = new User();
		user.setUser_name(name);
		//设置用户密码
		//String md5Password = NoteUtil.md5(password);
		String sha1Password = Sha1Util.string2Sha1(password);
		user.setUser_password(sha1Password);
		
		user.setUser_nick(nick);
		//创建Id
		String id = NoteUtil.md5(NoteUtil.createId());
		user.setUser_id(id);
		userMapper.save(user);
		
		//构建返回结果
		result.setStatus(0);
		result.setMsg("注册成功");
		
		return result;
	}
	
	
	//修改密码操作
	public NoteResult<Object> changePassword(String userName, String last_password, String final_password) {
		//构建返回结果
		NoteResult<Object> result = new NoteResult<Object>();
		//查找已经登陆的用户信息
		User user = userMapper.findByName(userName);
		//获得原密码
		String password=user.getUser_password();
		//对传入的密码需加密才能比较
		last_password=Sha1Util.string2Sha1(last_password);
		final_password=Sha1Util.string2Sha1(final_password);
		System.out.println(password+" "+last_password+" "+final_password);
		//进行比较 密码不相等的话
		if(!password.equals(last_password)) {
			result.setStatus(1);
			result.setMsg("原密码不正确");
			return result;
		}else if(password.equals(final_password)) {
			result.setStatus(2);
			result.setMsg("新密码与原密码不能相同!");
			return result;
		}else {
			//密码正确，执行修改密码操作
			user.setUser_password(final_password);
			userMapper.change(user);
			result.setStatus(0);
			result.setMsg("修改密码成功!");
			return result;
		}
	}
    
}


