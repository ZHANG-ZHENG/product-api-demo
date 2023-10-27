package top.zhost.test;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.nlp.AipNlp;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "22831682";
    public static final String API_KEY = "7VpM1uL4KyYwytmVRAQRMXbf";
    public static final String SECRET_KEY = "xvVc4zaRvc6oxRuTPhGh87s9KQFiuvwj";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        //String text = "百度是一家高科技公司";
        String text = "给张三加1分";
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.lexer(text, options);
        System.out.println(res.toString(2));
        
        
        // 词法分析（定制版）
        JSONObject res2 = client.lexerCustom(text, options);
        System.out.println(res2.toString(2));
    }
}