package top.zz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

public class TestSample {
	public static void main(String[] args) {
		AipOcr client = new AipOcr("16926287", "g0Lev5IKgZQDVXIdaN3eioEX", "B7yMau42mnzPdcWDydz3mn3d8gLiQ9fB");
		TestSample testSample = new TestSample();
		testSample.sample2(client);
	}
	
	public void sample(AipOcr client) {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_direction", "true");
	    options.put("detect_risk", "false");
	    
	    String idCardSide = "front";
	    
	    // 参数为本地图片路径
	    String image = "E:/test.jpg";
	    JSONObject res = client.idcard(image, idCardSide, options);
	    System.out.println(res.toString(2));

	    // 参数为本地图片二进制数组
	    byte[] file = null;
		try {
			file = readImageFile(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //byte[] file = null;
	    res = client.idcard(file, idCardSide, options);
	    System.out.println(res.toString(2));

	}
	
	public void sample2(AipOcr client) {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("language_type", "CHN_ENG");
	    options.put("detect_direction", "true");
	    options.put("detect_language", "true");
	    options.put("probability", "true");
	    
	    // 参数为本地图片二进制数组
	    byte[] file = null;
		try {
			file = readImageFile("E:/test2.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JSONObject res = client.basicGeneral(file, options);
	    System.out.println(res.toString(2));

	}

    public byte[] readImageFile(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
        throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }
}
