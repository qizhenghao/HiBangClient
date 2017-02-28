package cn.hibang.liuzhiwei.testentity;

public class FeedComment {
	private String name;
	private String avatar;
	private String content;
	private String time;
	
	public FeedComment(String name, String avatar, String content, String time) {
		super();
		this.name = name;
		this.avatar = avatar;
		this.content = content;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
