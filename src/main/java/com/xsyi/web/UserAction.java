package com.xsyi.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.xsyi.model.User;
import com.xsyi.service.UserService;


@Controller
//@SessionAttributes({"a"})
public class UserAction {

	private static final Logger log=Logger.getLogger(UserAction.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping("/showUser")
	public String showUser(HttpServletRequest request,HttpServletResponse response){
//		AbstractController
		String id=request.getParameter("id");
		log.info("===id="+id);
		User user=userService.getUserById(id);
		request.setAttribute("user", user);
		return "showUser";
	}
	
	@RequestMapping("/showImage")
	public String showImage(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		log.info("===id="+id);
		User user=userService.getUserById(id);
		request.setAttribute("user", user);
		return "showImage";
	}
	
	//接收参数和返回参数的方式
	@RequestMapping("/showUser2")
	public ModelAndView showUserParam(String id,Model model){
		log.info("===id="+id);
		User user=userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("showUser2");
	}
	
	@RequestMapping("/toGetUser")
	public String toGetUser(){
		return "getUser";
	}
	
	//form表单参数传递,通过对象传递
	@RequestMapping("/getUser")
	public String getUser(User user){
		log.info("username :"+user.getUsername());
		log.info("ename : "+user.getEnname());
		
		//SimpleUrlHandlerMapping,DefaultAnnotationHandlerMapping  
		//
		return "showUser";
	}
	
	@RequestMapping("/insertUser")
	public String insertUser(User user){
		user.setUserid("4");
		user.setUsername("444test");
		userService.insertUser(user);
		return null;
	}
	
	@RequestMapping("/showUR")
	public ModelAndView showUserAndRole(String userid,Model model){
		Map<String, String> param=new HashMap<String, String>();
		param.put("userid", userid);
		User user=userService.selectUser(param);
		model.addAttribute("user", user);
		return new ModelAndView("showUser");
	}
	
	@RequestMapping("/showUser3")
	public String regUser(@RequestParam("id") String userId,ModelMap map,
				HttpServletRequest request,HttpServletResponse response){
		System.out.println(userId);
		map.addAttribute("a","测试sd");
		request.setAttribute("b", "测试request");
		request.getSession().setAttribute("c", "的淡淡的sdfd");
		return "showUser3";
	}
	
	//测试ModelAttribues注解,配合SessionAttributes使用
	@RequestMapping("/showUser4")
	public String regUser1(@ModelAttribute("a") String userId,ModelMap map,
				HttpServletRequest request,HttpServletResponse response){
		System.out.println("userid="+userId);
		map.addAttribute("a","测试sd");
		request.setAttribute("b", "测试request");
		request.getSession().setAttribute("c", "的淡淡的sdfd");
		return "showUser3";
	}
	
	//测试转发操作实现
	@RequestMapping("/showUser5")
	public String regUser2(){
		System.out.println("regUser2");
		return  "redirect:http://www.baidu.com";
	}
	
}
