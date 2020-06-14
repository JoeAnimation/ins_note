package com.sicau.ins_note.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("result");
		if(obj==null) {
			//未登录，重定向至登录页
			response.sendRedirect("/user/login.do");
			return false;
		}
		//以登陆则放行
		return true;
	}

	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception ex)
			throws Exception {
		
		
	}

	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView ex)
			throws Exception {
		
		
	}



}
