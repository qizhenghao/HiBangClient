package cn.hibang.bruce.domain;

public class MyPhoto {

	public MyPhoto(long serialversionuid, String path2) {
		this.friendId = serialversionuid;
		this.path = path2;
	}
	private long friendId;
	private String path;
	public long getFriendId() {
		return friendId;
	}
	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
