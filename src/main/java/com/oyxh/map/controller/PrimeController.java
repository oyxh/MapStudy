package com.oyxh.map.controller;



import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.oyxh.map.common.annotation.Log;
import com.oyxh.map.common.utils.MD5Utils;
import com.oyxh.map.common.utils.R;
import com.oyxh.map.domain.UserDO;
import com.oyxh.map.service.UserService;



@Controller
public class PrimeController extends BaseController {
	private String prefix = "study";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	  private UserService userService;
	 
	
	@GetMapping("/")
	String welcome(Model model) {
		return "redirect:/login";
	}
	@GetMapping("/login" )
	public String  getLogin() {
		// 查询列表数据
		
       
   	 return  "login";
	}

	
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		
		password = MD5Utils.encrypt(username, password);
		
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}
	

	
	@Log("请求访问主页")
	@GetMapping({"/index"})
	public String  getIndex(Model model) {
		// 查询列表数据
		
		model.addAttribute("name", getUser().getName());
		logger.info(getUser().getName());
		return  "index";
	}
	/*
	@GetMapping("/index1" )
	public String  getIndex1() {
		// 查询列表数据
		
       
   	 return  "index1";
	}
	*/

	   @GetMapping("/queryUsers")
	   @ResponseBody
	   public List<UserDO> queryAllUsers(){
		  
	    	List<UserDO> userList = userService.list();
	    	
	    	 //model.addAttribute("users", userList);
	        
	    	return userList;
	    }
	
}
