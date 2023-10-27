package com.zz.test.wechat;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HtmlController {  
   
	@GetMapping("/index")
	String test(HttpServletRequest request) {
		//逻辑处理
		request.setAttribute("key", "hello world zz");
		return "index";
	}  
}
