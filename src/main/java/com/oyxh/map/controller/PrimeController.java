package com.oyxh.map.controller;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oyxh.map.common.annotation.LayerItemDeserializer;
import com.oyxh.map.common.annotation.Log;
import com.oyxh.map.common.utils.MD5Utils;
import com.oyxh.map.common.utils.R;
import com.oyxh.map.dao.GeometryDao;
import com.oyxh.map.domain.GeometryDO;
import com.oyxh.map.domain.LayerDO;
import com.oyxh.map.domain.UserDO;
import com.oyxh.map.service.GeometryService;
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

	
	@Log("注册")
	@PostMapping("/register")
	@ResponseBody
	R ajaxRegister(String username, String password) {	
		Map<String, Object> map = new HashMap();
		map.put("username", username);
		List<UserDO> userlist = userService.list(map);
		if(userlist.size() > 0) {
			return R.error(601, "用户名已注册");
		}else {
			
			UserDO user = new UserDO();
			user.setName(username);
			user.setPassword(MD5Utils.encrypt(username, password));
			user.setUsername(username);
			user.setGmtCreate(new Date());
			user.setStatus(1);
			int count = userService.save(user);
			System.out.println(count);
			if(count>0) {
				ajaxLogin(username,password);
				return R.ok(user.getUserId());
			}else {
				return R.error();
			}
		
		}
		
	}
	
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
	password = MD5Utils.encrypt(username, password);
		System.out.println(password);
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
	
	@Log("请求用户")
	@GetMapping({"/user"})
	@ResponseBody
	public Map<String,Object>  getCurrentUser() {
		// 查询列表数据
		Map<String,Object> r=new HashMap<String,Object>();
		logger.info(getUser().getName());
	    UserDO user = getUser();
	    r.put("userId", user.getUserId());
	    r.put("username",user.getUsername() );
		r.put("name",user.getName() );
		r.put("email", user.getEmail());
		r.put("mobile", user.getMobile());
		return r;
	}
	
	@Log("保存用户")
	  /** 
   * 请求内容是一个json串,spring会自动把他和我们的参数bean对应起来,不过要加@RequestBody注解 
   *  
   * @param name 
   * @param pwd 
   * @return 
   */  
  @PostMapping(value = "/saveuser")  
	@ResponseBody
  public R  saveUser(@RequestBody String json) {
		System.out.println(json);
		  Gson gson = new Gson();
		UserDO user = gson.fromJson(json,UserDO.class);
		System.out.println(user.getUsername());
		user.setGmtModified(new Date());
		int r = userService.update(user);
		if (r > 0) {
			/*以下为更新session的用户数据*/
			Subject subject = SecurityUtils.getSubject();
			PrincipalCollection principals = subject.getPrincipals();
			//realName认证信息的key，对应的value就是认证的user对象
			String realName= principals.getRealmNames().iterator().next();
			//创建一个PrincipalCollection对象，userDO是更新后的user对象
			PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realName);
			subject.runAs(newPrincipalCollection);
			return R.ok();
		}else {
			return R.error("更新失败");
		}
  }  

	   @GetMapping("/queryUsers")
	   @ResponseBody
	   public List<UserDO> queryAllUsers(){
		  
	    	List<UserDO> userList = userService.list();
	    	
	    	 //model.addAttribute("users", userList);
	        
	    	return userList;
	    }
	
}
