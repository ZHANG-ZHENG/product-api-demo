package top.zhost.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
 
public class MyWebSocketClient extends WebSocketClient{
	
	private String message = "";
 
    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }
 
    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("握手...");
        for(Iterator<String> it=shake.iterateHttpFields();it.hasNext();) {
            String key = it.next();
            System.out.println(key+":"+shake.getFieldValue(key));
        }
    }
 
    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息："+paramString);
        this.message += paramString;
    }
 
    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("关闭...");
    }
 
    @Override
    public void onError(Exception e) {
        System.out.println("异常"+e);
        
    }
    
    public static void main(String[] args) {
        String uid = "zhengzhang-test";
        String token = "456";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ws://121.196.49.33:8000/uid/");
        stringBuilder.append(uid);
        stringBuilder.append("/ws?token=");
        stringBuilder.append(token);
        String url = stringBuilder.toString();
        URI uri = URI.create(url);
        try {
            //MyWebSocketClient client = new MyWebSocketClient("ws://192.168.1.118:8080/websocket");
        	MyWebSocketClient client = new MyWebSocketClient(url);
            client.connect();
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            System.out.println("建立websocket连接");
            //一帧大小1280字节
            byte[] destBytes = new byte[1280]; 
            client.send(destBytes);
            //client.send("asd");
            client.close();
            
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } 
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


    

}