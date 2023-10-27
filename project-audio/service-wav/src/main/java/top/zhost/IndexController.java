package top.zhost;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.speech.restapi.asrdemo.AsrBean;
import com.baidu.speech.restapi.asrdemo.AsrMain;

import top.zhost.config.MyWebSocketClient;
import top.zhost.utils.SoundUtil;

@RestController
@RequestMapping("/wav")
public class IndexController {
	@Autowired
	MyWebSocketClient myWebSocketClient;
	
	private static byte[] fileCacheBytes;

    @RequestMapping(value = "/test")
    public String index() {

        return "Hello World spring-boot-study-demo!!!";
    }
    
    @PostMapping("/getFileInfo")
    public String getFileInfo(@RequestParam("file") MultipartFile mFile,@RequestParam(name="remark") String remark) throws Exception {	
		File f = new File("C://Users//zz//Desktop//upload//音频相关//语速分析//语速0.7.wav");
		RandomAccessFile rdf = null;
		rdf = new RandomAccessFile(f, "r");
		System.out.println("audio size: " + ByteUtil.toInt(read(rdf, 4, 4))); // 音频文件大小
		System.out.println("audio format: " + ByteUtil.toShort(read(rdf, 20, 2))); // 音频格式，1-PCM
		System.out.println("num channels: " + ByteUtil.toShort(read(rdf, 22, 2))); // 1-单声道；2-双声道
		System.out.println("sample rate: " + ByteUtil.toInt(read(rdf, 24, 4))); // 采样率、音频采样级别
		System.out.println("byte rate: " + ByteUtil.toInt(read(rdf, 28, 4))); // 每秒波形的数据量
		System.out.println("block align: " + ByteUtil.toShort(read(rdf, 32, 2))); // 采样帧的大小
		System.out.println("bits per sample: " + ByteUtil.toShort(read(rdf, 34, 2))); // 采样位数
    	
		//System.out.println(myWebSocketClient);
    	fileCacheBytes = mFile.getBytes();
    	byte[] mBytes = mFile.getBytes();
    	System.out.println("file bytes:"+mBytes.length);
    	System.out.println(new String(ByteUtil.getArrayCopy(mBytes, 0, 4)));
		String info1 = " audio size: " + ByteUtil.toLong(ByteUtil.getArrayCopy(mBytes, 4, 4));// 音频文件大小
		System.out.println(info1);
		System.out.println(new String(ByteUtil.getArrayCopy(mBytes, 8, 4)));
		System.out.println(new String(ByteUtil.getArrayCopy(mBytes, 12, 4)));
		System.out.println(ByteUtil.toLong(ByteUtil.getArrayCopy(mBytes, 16, 4)));
		String info2 = " audio format: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 20, 2));// 音频格式，1-PCM
		String info3 = " num channels: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 22, 2));// 1-单声道；2-双声道
		String info4 = " sample rate: " + ByteUtil.toInt(ByteUtil.getArrayCopy(mBytes, 24, 4));// 采样率、音频采样级别
		String info5 = " byte rate: " + ByteUtil.toInt(ByteUtil.getArrayCopy(mBytes, 28, 4));// 每秒波形的数据量
		String info6 = " block align: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 32, 2));// 采样帧的大小
		String info7 = " bits per sample: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 34, 2));// 采样位数
		System.out.println(new String(ByteUtil.getArrayCopy(mBytes, 36, 4))); 
		System.out.println(ByteUtil.toLong(ByteUtil.getArrayCopy(mBytes, 40, 4))); 
		System.out.println(info2); 
		System.out.println(info3); 
		System.out.println(info4); 
		System.out.println(info5); 
		System.out.println(info6); 
		System.out.println(info7); 
		return info1+info2+info3+info4+info5+info6+info7;
    }
    
    /**
     * https://blog.csdn.net/qq_25827845/article/details/79498075
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile mFile,@RequestParam(name="remark") String remark) throws Exception {
    	System.out.println(mFile);
//		File f = new File("C://Users//zz//Desktop//upload//音频相关//test.wav");
//		RandomAccessFile rdf = null;
//		rdf = new RandomAccessFile(f, "r");
//		System.out.println("audio size: " + ByteUtil.toInt(read(rdf, 4, 4))); // 音频文件大小
//		System.out.println("audio format: " + ByteUtil.toShort(read(rdf, 20, 2))); // 音频格式，1-PCM
//		System.out.println("num channels: " + ByteUtil.toShort(read(rdf, 22, 2))); // 1-单声道；2-双声道
//		System.out.println("sample rate: " + ByteUtil.toInt(read(rdf, 24, 4))); // 采样率、音频采样级别
//		System.out.println("byte rate: " + ByteUtil.toInt(read(rdf, 28, 4))); // 每秒波形的数据量
//		System.out.println("block align: " + ByteUtil.toShort(read(rdf, 32, 2))); // 采样帧的大小
//		System.out.println("bits per sample: " + ByteUtil.toShort(read(rdf, 34, 2))); // 采样位数
		
		byte[] mBytes = mFile.getBytes();
		System.out.println(mBytes.length);
		System.out.println("audio size: " + ByteUtil.toInt(ByteUtil.getArrayCopy(mBytes, 4, 4))); // 音频文件大小
		System.out.println("audio format: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 20, 2))); // 音频格式，1-PCM
		System.out.println("num channels: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 22, 2))); // 1-单声道；2-双声道
		System.out.println("sample rate: " + ByteUtil.toInt(ByteUtil.getArrayCopy(mBytes, 24, 4))); // 采样率、音频采样级别
		System.out.println("byte rate: " + ByteUtil.toInt(ByteUtil.getArrayCopy(mBytes, 28, 4))); // 每秒波形的数据量
		System.out.println("block align: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 32, 2))); // 采样帧的大小
		System.out.println("bits per sample: " + ByteUtil.toShort(ByteUtil.getArrayCopy(mBytes, 34, 2))); // 采样位数

		
//        String uid = "zhengzhang-test"+remark;
//        String token = "456";
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("ws://121.196.49.33:8000/uid/");
//        stringBuilder.append(uid);
//        stringBuilder.append("/ws?token=");
//        stringBuilder.append(token);
//        String url = stringBuilder.toString();
        String result = "";
        try {
//        	MyWebSocketClient client = new MyWebSocketClient(url);
//            client.connect();
//            int connCount = 0;
//            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
//            	if(connCount>50){
//            		System.out.println("连接超时");
//            		throw new RuntimeException("连接超时");
//            	}
//                System.out.println("还没有打开");
//                try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//                connCount++;
//            }
//            System.out.println("建立websocket连接");
            //一帧大小1280字节
            int n = 1;
            while(1280*n < mBytes.length-1280){
            	byte[] destBytes = (ByteUtil.getArrayCopy(mBytes, 1280*n, 1280));
            	myWebSocketClient.send(destBytes);
                try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	n++;
            }
            System.out.println("数据发送完毕");
            int connCount = 0;
            while(connCount<30){
                try {
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}   
                connCount++;
                if(!"".equals(myWebSocketClient.getMessage())){
                	break;
                }
            }
            Thread.sleep(500);
            result = myWebSocketClient.getMessage();
            myWebSocketClient.setMessage("");
            
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } 
        return result;
    }
    
    @PostMapping("/uploadBaidu")
    public String uploadBaidu(@RequestParam("file") MultipartFile mFile,@RequestParam(name="remark") String remark) throws Exception {
    	byte[] mBytes = mFile.getBytes();
    	AsrBean demo = new AsrBean();
        // 填写下面信息
        String result = demo.run(mFile);
        System.out.println("识别结束：结果是：");
        System.out.println(result);
        return result;
        
    }

    @PostMapping("/uploadBaidu2")
    public String uploadBaidu2(@RequestParam("file") MultipartFile mFile,@RequestParam(name="remark") String remark) throws Exception {
    	byte[] mBytes = mFile.getBytes();
    	AsrBean demo = new AsrBean();
        // 填写下面信息
        String result = demo.run(mFile);
        System.out.println("识别结束：结果是：");
        System.out.println(result);
        return result;
        
    }
    
    @GetMapping(value = "/getFile")

	public void getFile(HttpServletRequest request,HttpServletResponse response) {
    	if(fileCacheBytes==null){
    		return;
    	}
		
        response.setContentLengthLong(fileCacheBytes.length);
        response.setContentType("audio/wav");  
//        if("1".equals(request.getParameter("svgShow"))){
//            response.setContentType("image/svg+xml");    
//        }else {
//        	response.setContentType(fileExt.getContentType());
//		}
		response.setHeader("Content-Disposition", "attachment;filename=wav-test.wav");

		//byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(fileCacheBytes, 0, fileCacheBytes.length);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
    @GetMapping(value = "/soundUp")

	public void soundUp(HttpServletRequest request,HttpServletResponse response) {
    	if(fileCacheBytes==null){
    		return;
    	}
    	
    	for(int i=44;i<fileCacheBytes.length;i=i+2){
    		byte[] volumebytes = ByteUtil.getArrayCopy(fileCacheBytes, i, 2);
    		byte[] volumeRatioBytes = SoundUtil.getDealVolume(volumebytes, 2.0f);
    		fileCacheBytes[i] = volumeRatioBytes[0];
    		fileCacheBytes[i+1] = volumeRatioBytes[1];

    	}
		
        response.setContentLengthLong(fileCacheBytes.length);
        response.setContentType("audio/wav");  
		response.setHeader("Content-Disposition", "attachment;filename=wav-test.wav");

		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(fileCacheBytes, 0, fileCacheBytes.length);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
 
	private static byte[] read(RandomAccessFile rdf, int pos, int length) throws IOException {
		rdf.seek(pos);
		byte result[] = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = rdf.readByte();
		}
		return result;
	}
    
    
}
