package com.example.demo.controller;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.User;

@Configuration
@EnableConfigurationProperties(MineInfo.class)
public class MineInfoAutoConfiguration {
	@Autowired
    private MineInfo mineInfo;
	
	@Bean
	@ConditionalOnMissingBean
	public User getMineInfo() throws UnknownHostException {
		User u = new User();
		u.setUsername(mineInfo.getName());
		u.setPassword(mineInfo.getPassword());
		return u;
	}
}
