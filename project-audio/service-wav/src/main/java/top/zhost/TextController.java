package top.zhost;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhost.utils.TextUtil;

import com.baidu.aip.nlp.AipNlp;

@RestController
@RequestMapping("/text")
public class TextController {

    public static final String APP_ID = "22831682";
    public static final String API_KEY = "7VpM1uL4KyYwytmVRAQRMXbf";
    public static final String SECRET_KEY = "xvVc4zaRvc6oxRuTPhGh87s9KQFiuvwj";
    
    @PostMapping("/dealText")
    public String dealText(@RequestBody String text) throws Exception {	
    	String changText = TextUtil.beforeDealText(text);
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.lexer(changText, options);
        String result = TextUtil.dealText(res);
        JSONArray items = res.getJSONArray("items");
    	return items.toString();
    }
    
  
    public static void main(String[] args) {
		int a=3;
		float b = 1.5f;
		int c = (int) (a*b);
		System.out.println(c);
	}
    
}
