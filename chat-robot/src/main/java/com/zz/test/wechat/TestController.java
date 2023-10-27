package com.zz.test.wechat;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController{	
//@RequestParam(value="question") String question
	@RequestMapping("/test")
	public String test(@RequestBody QuestionBean questionBean) {
		//String question = "福州天气";
		String questionJson = "{"+
	"'reqType':0,"+
    "'perception': {"+
        "'inputText': {"+
            "'text': '"+questionBean.getQuestion()+"'"+
        "}"+
    "},"+
    "'userInfo': {"+
        "'apiKey': '9352d48da3c849819a25d7fd3802f740',"+
        "'userId': '406311'"+
    "}"+
"}";
		String result =  HttpUtils.submitPostJsonData("http://openapi.tuling123.com/openapi/api/v2", questionJson, "UTF-8");
		
		System.out.println("result "+result);
		return result;
	}
	
	@RequestMapping("/test2")
	public String test2() {
		return "result test2";
	}
}
