package top.zhost.bean;

import java.util.List;

public class BaiduTextBean {
	
	private Long log_id;
	private String text;
	private List<BaiduTextItemBean> items;
	
	public Long getLog_id() {
		return log_id;
	}
	public void setLog_id(Long log_id) {
		this.log_id = log_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<BaiduTextItemBean> getItems() {
		return items;
	}
	public void setItems(List<BaiduTextItemBean> items) {
		this.items = items;
	}
	
}
