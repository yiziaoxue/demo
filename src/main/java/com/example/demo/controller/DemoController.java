package com.example.demo.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

//import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.annotation.RequestLimit;
import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.util.RedisUtil;

@Controller
@RequestMapping("/user")
public class DemoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MineInfoAutoConfiguration mineInfoAutoConfiguration;
	
	@Autowired  
	RedisUtil redisUtil;  
	
	@Autowired  
	MongoTemplate mongoTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	 
//	@Autowired
//	private SqlSessionTemplate sqlSessionTemplate;
	 
    @RequestLimit(count=3,time=60000)
    @RequestMapping("/view/{name}")
//    @Cacheable(value="user")
	public String home(ModelMap map,HttpServletRequest request,@PathVariable("name") String name) throws UnknownHostException {
    	userService.sys();
    	User user = mineInfoAutoConfiguration.getMineInfo();
    	System.out.println(user.getUsername());
    	redisUtil.set(name, user.getUsername());
    	mongoTemplate.insert(user,"user");
    	map.addAttribute("host", "阿尔萨斯重振天灾军团");
//    	System.out.println("Mysql_Name: "+jdbcTemplate.getDataSource());
    	return "login";
	 }
}
