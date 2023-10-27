package com;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.io.IOException;
import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

@RestController
public class IndexController {
	
	 private static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	public static String publicKeyStrCache = null;
	
	public static String jwtTokenCache = "jwtTokenCache";

    @RequestMapping(value = "/test")
    public String index() {

        return "Hello World spring-boot-study-demo!!!";
    }
    
    //http://10.9.47.3/webservice/pubThirdpartyClientKey.action?clientId=test
    
    @RequestMapping(value = "/test1")
    public String test1() {
    	String result = HttpClientUtil.doGet("http://10.9.47.3/webservice/pubThirdpartyClientKey.action?clientId=ava");
    	JSONObject jsonObject = JSONUtil.parseObj(result);
    	Boolean success = jsonObject.getBool("success", false);
    	String publicKeyStr = null;
    	if(success) {
    		publicKeyStr = jsonObject.getStr("data");
    	}
    	publicKeyStrCache = publicKeyStr;
        return publicKeyStr;
    }
    
    @RequestMapping(value = "/test2")
    public String test2() {
    	String passwordEncryptedText = null;
    	try {
		    //String publicKeyPEMContent = "37885228MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBnbit3kOvVasdeCu7QvLsBaARq3XFlc1Qytv8ewTMg24WNZZHMx6KP2QRkzzBzvS8bj2raWLhLv82xeUJkBjMjjj6D91E0qaL/nSlGUKOHi8t63NHdqqwFmLibzwb6900ntsYkMk1mpRqF1m+xj9Q6PbreNYwBNnvY+c2yMCvsQIDAQAB";
    		logger.info("publicKeyStrCache: " + publicKeyStrCache);	
		    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStrCache);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		    
	        String plaintext = "passwordtest";
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
	        passwordEncryptedText= Base64.getEncoder().encodeToString(encryptedBytes);
	        logger.info("ava Encrypted Text: " + passwordEncryptedText);		    
    	} catch (Exception e) {
    		logger.error("IOException", e);
		}
    	
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("clientId", "ava");
    	param.put("password", passwordEncryptedText);
    	String result = HttpClientUtil.doPost("http://10.9.47.3/webservice/pubThirdpartyClientlogin.action",param);
    	logger.info("result: " + result);
    	JSONObject jsonObject = JSONUtil.parseObj(result);
    	Boolean success = jsonObject.getBool("success", false);
    	String jwtToken = null;
    	if(success) {
    		JSONObject data = jsonObject.getJSONObject("data");
    		jwtToken = data.getStr("jwtToken");
    		jwtTokenCache = jwtToken;
    	}
    
        return jwtToken;
    }
    
    //http://localhost:8888/tpwebservice/pubRecordEquipmentListWithAI.action
    
    @RequestMapping(value = "/test3")
    public String test3() {
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put("jwtToken", jwtTokenCache);
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("PageIndex", "1");
    	param.put("PageSize", "50");
    	String result = HttpClientUtil.doGet("http://10.9.47.3/tpwebservice/pubRecordEquipmentListWithAI.action",param,headers);
    	logger.info("result: " + result);

        return result;
    }
    @RequestMapping(value = "/test4")
    public String test4() {
    	Map<String, String> headers = new HashMap<String, String>();
    	headers.put("jwtToken", jwtTokenCache);
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("PageIndex", "1");
    	param.put("PageSize", "50");
    	param.put("useTimeoutData", "useTimeoutData");
    	//String result = HttpClientUtil.doGet("http://10.9.47.3/tpwebservice/pubRecordEquipmentListWithAI.action",param,headers);
    	String resultString = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost("http://10.9.47.3/tpwebservice/pubRecordEquipmentListWithAI.action");
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
    		if (headers != null && headers.size() != 0) {
    			for (Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator(); iterator.hasNext();) {
    				Map.Entry<String, String> data = iterator.next();
    				String key = data.getKey();
    				String value = data.getValue();
    				httpPost.setHeader(key, value);
    			}
    		}
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
        	logger.error("Exception", e);
        } finally {
            try {
            	if(response != null)
            		response.close();
            } catch (IOException e) {
            	logger.error("IOException", e);
            }
        }
    	
    	logger.info("result: " + resultString);

        return resultString;
    }
    
    @RequestMapping(value = "/test5")
    public String test5() {
    	String resultString = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost("http://10.9.47.3/tpwebservice/pubRecordEquipmentListWithAI.action");
            httpPost.setHeader("jwtToken", jwtTokenCache);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<>();
            paramList.add(new BasicNameValuePair("PageIndex", "1"));
            paramList.add(new BasicNameValuePair("PageSize", "50"));
            paramList.add(new BasicNameValuePair("nameEq", "教学楼A201"));
//            paramList.add(new BasicNameValuePair("useTimeoutData", "true"));
//            paramList.add(new BasicNameValuePair("thirdpartyNo", "100578340"));
//            paramList.add(new BasicNameValuePair("thirdpartyNo", "100633447"));
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
            httpPost.setEntity(entity);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
        	logger.error("Exception", e);
        } finally {
            try {
            	if(response != null)
            		response.close();
            } catch (IOException e) {
            	logger.error("IOException", e);
            }
        }
    	logger.info("result: " + resultString);
        return resultString;
    }
}
