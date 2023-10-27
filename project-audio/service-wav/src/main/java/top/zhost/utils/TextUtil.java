package top.zhost.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class TextUtil {

	public static String beforeDealText(String str){
		if(str.contains("加一分") || str.contains("减一分")){
			str = str.replace("一分", "1分");
		}
		if(str.contains("加二分") || str.contains("减二分")){
			str = str.replace("二分", "2分");
		}
		if(str.contains("加三分") || str.contains("减三分")){
			str = str.replace("三分", "3分");
		}
		if(str.contains("加四分") || str.contains("减四分")){
			str = str.replace("四分", "4分");
		}
		if(str.contains("加五分") || str.contains("减五分")){
			str = str.replace("五分", "5分");
		}
		if(str.contains("加六分") || str.contains("减六分")){
			str = str.replace("六分", "6分");
		}
		if(str.contains("加七分") || str.contains("减七分")){
			str = str.replace("七分", "7分");
		}
		if(str.contains("加八分") || str.contains("减八分")){
			str = str.replace("八分", "8分");
		}
		if(str.contains("加九分") || str.contains("减九分")){
			str = str.replace("九分", "9分");
		}
		if(str.contains("加十分") || str.contains("减十分")){
			str = str.replace("十分", "10分");
		}		
		return str;
	}
	
	public static String dealText(JSONObject jsonObj){
		System.out.println(jsonObj.getString("text"));
        JSONArray items = jsonObj.getJSONArray("items");
        System.out.println(items);
        String[] resultArr = {"","",""};
        for(int i=0;i<items.length();i++){
        	JSONObject item = items.getJSONObject(i);
        	System.out.println(item);
        	
        }
		return "";
	}
}
