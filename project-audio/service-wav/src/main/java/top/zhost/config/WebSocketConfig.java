package top.zhost.config;

import java.net.URISyntaxException;

import org.java_websocket.WebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.zhost.ByteUtil;

@Configuration
public class WebSocketConfig {

    @Bean
    public MyWebSocketClient myWebSocketClient() {
  
        String uid = "zhengzhang-test";
        String token = "456";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ws://121.196.49.33:8000/uid/");
        stringBuilder.append(uid);
        stringBuilder.append("/ws?token=");
        stringBuilder.append(token);
        String url = stringBuilder.toString();
        String result = "";
        try {
            //MyWebSocketClient client = new MyWebSocketClient("ws://192.168.1.118:8080/websocket");
        	MyWebSocketClient client = new MyWebSocketClient(url);
//        	if(1==1){
//        		return client;
//        	}
            client.connect();
            int connCount = 0;
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            	if(connCount>50){
            		System.out.println("连接超时");
            		//return null;
            		throw new RuntimeException("连接超时");
            	}
                System.out.println("还没有打开");
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                connCount++;
            }
            return client;
            
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } 
        return null;
    }
}
