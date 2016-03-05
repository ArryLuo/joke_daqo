package com.example.xiaohua;

public class Data {
	private String content;
	private String updatetime;
	
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Data(String content, String updatetime) {
		super();
		this.content = content;
		this.updatetime = updatetime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString() {
		return "Data [content=" + content + ", updatetime=" + updatetime + "]";
	}
	
}
