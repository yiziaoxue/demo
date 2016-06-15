package com.example.demo.annotation.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.annotation.AccessRequired;

/**
 * 拦截url中的access_token
 * @author Nob
 * 
 */
public class UserAccessApiInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		AccessRequired annotation = method.getAnnotation(AccessRequired.class);
		if (annotation != null) {
		   System.out.println("你遇到了：@AccessRequired");
		   String accessToken = request.getParameter("access_token");
			/**
			 * Do something
			 */
		    response.getWriter().write("没有通过拦截，accessToken的值为：" + accessToken);
		}
		// 没有注解通过拦截
		return true;
	}
}
