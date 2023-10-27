package demo;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMain {
	static int appid = 1400215162; // SDK AppID ��1400��ͷ

	// ����Ӧ�� SDK AppKey
	static String appkey = "53c70c83ba53a75e9eee3d0a36e70efe";

	// ��Ҫ���Ͷ��ŵ��ֻ�����
	static String[] phoneNumbers = {"18950361314"};

	static // ����ģ�� ID����Ҫ�ڶ���Ӧ��������
	int templateId = 343995; // NOTE: �����ģ�� ID`7839`ֻ��ʾ������ʵ��ģ�� ID ��Ҫ�ڶ��ſ���̨������

	// ǩ��
	static String smsSign = "����ȥ��"; // NOTE: ǩ������ʹ�õ���`ǩ������`��������`ǩ��ID`�������ǩ��"��Ѷ��"ֻ��ʾ������ʵ��ǩ����Ҫ�ڶ��ſ���̨����
	
	public static void main(String[] args) {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    //SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],"������ȥ����3378������֤�룬��5������ȷ�ϡ�", "", "");
		    ArrayList<String> lits = new ArrayList<String>();
		    lits.add("��");
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],templateId, lits, smsSign, "", "");
		    
		    System.out.println(result);
		} catch (HTTPException e) {
		    // HTTP ��Ӧ�����
		    e.printStackTrace();
		} catch (JSONException e) {
		    // JSON ��������
		    e.printStackTrace();
		} catch (IOException e) {
		    // ���� IO ����
		    e.printStackTrace();
		}

	}

}
