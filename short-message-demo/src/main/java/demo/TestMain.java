package demo;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMain {
	static int appid = 1400215162; // SDK AppID 以1400开头

	// 短信应用 SDK AppKey
	static String appkey = "53c70c83ba53a75e9eee3d0a36e70efe";

	// 需要发送短信的手机号码
	static String[] phoneNumbers = {"18950361314"};

	static // 短信模板 ID，需要在短信应用中申请
	int templateId = 343995; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请

	// 签名
	static String smsSign = "随风而去网"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
	
	public static void main(String[] args) {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    //SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],"【随风而去网】3378您的验证码，请5分钟内确认。", "", "");
		    ArrayList<String> lits = new ArrayList<String>();
		    lits.add("张");
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],templateId, lits, smsSign, "", "");
		    
		    System.out.println(result);
		} catch (HTTPException e) {
		    // HTTP 响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // JSON 解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络 IO 错误
		    e.printStackTrace();
		}

	}

}
