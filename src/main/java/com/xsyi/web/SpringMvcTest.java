package com.xsyi.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMvcTest {
	
	@ModelAttribute
	public String t0(){
		System.out.println("11111111111111111");
		return "111";
	}

	/**
	 * 1.测试使用jstl标签展示集合数据,如Map
	 * 2.利用ModelAndView传递Map数据
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView t1(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("m1", "提高1");
		map.put("m2", "提高2");
		map.put("m3", "提高3");
		return new ModelAndView("showList", "map", map);
	}
}
