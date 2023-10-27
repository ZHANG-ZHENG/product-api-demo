package com;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/4/27.
 */
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    
    public static String doGet(String url, Map<String, String> param) {
        
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            
            // 执行请求
            response = httpclient.execute(httpGet);
            logger.info("请求arm文件="+response.toString());
            // 判断返回状态是否为200
            
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
        	logger.error("", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
            	logger.error("", e);
            }
        }
        return resultString;
    }
    
public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {
        
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            applyHeaderParameters(httpGet, headers);
            
            // 执行请求
            response = httpclient.execute(httpGet);
            logger.info("请求arm文件="+response.toString());
            // 判断返回状态是否为200
            
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
        	logger.error("Exception", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
            	logger.error("IOException", e);
            }
        }
        return resultString;
    }
    
    
    public static InputStream doStreamGet(String url, Map<String, String> param) {
        
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
    
        InputStream resultString = null;
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String testStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("素材获取结果"+testStr);
                resultString = response.getEntity().getContent();
            }
        } catch (Exception e) {
        	logger.error("Exception", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
            	logger.error("IOException", e);
            }
        }
        return resultString;
    }
    
    
    
    public static String doGet(String url) {
        return doGet(url, null);
    }
    
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
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
        
        return resultString;
    }
    
    public static String doPost(String url) {
        return doPost(url, null);
    }
    
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
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
        
        return resultString;
    }
    
    public static String doPostJson(String url, String json, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            
            applyHeaderParameters(httpPost, headers);
            
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
        
        return resultString;
    }
    
	private static void applyHeaderParameters(HttpRequestBase httpRequestBase, Map<String, String> headers) {
		if (headers != null && headers.size() != 0) {
			for (Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, String> data = iterator.next();
				String key = data.getKey();
				String value = data.getValue();
				httpRequestBase.setHeader(key, value);
			}
		}
	}
    
//    public static void saveFile(String requestUrl,File target) {
//        try {
//            //设置请求参数的编码格式 防止中文乱码
//            URL realUrl = new URL(requestUrl);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("Accept-Charset", "utf-8");
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            // 建立实际的连接
//            connection.connect();
//            InputStream inputStream = connection.getInputStream();
//            FileUtil.saveVoiceFile(inputStream,target);
//        } catch (Exception e) {
//            logger.info("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
//    }
    
    
    
}
