package cn.hibang.huxing.msgutility;

import java.io.Serializable;

import cn.hibang.huxing.clientmessage.GENDER;


public class HiBangUser implements Serializable{	
	
	private Integer userID;
	private String username;
	private String password;
	private String school;
	private String email;
	private String phone;
	private byte[] photo;
	private boolean vipUser;
	private Integer hiCoin; /* hi coin */
	private Integer userAge;
	private GENDER gender;
	private String ps;
	
	/* Represents friend category while describe friend */
	private FriendState friendState;
	/*  */
	private HelpRelation helpRel;
	
	public HiBangUser() {
		super();
		ps = "";
		gender = GENDER.FEMALE;
		userID = -1;
		username = password = "";
		school = "1";
		userAge = 10;
		hiCoin = 10;
		email = "123@163.com";
		phone = "110";
		photo = new byte[]{};
		vipUser = false;		
	}
	
	public FriendState getFriendState() {
		return friendState;
	}
	public void setFriendState(FriendState friendState) {
		this.friendState = friendState;
	}
	public HelpRelation getHelpRel() {
		return helpRel;
	}
	public void setHelpRel(HelpRelation helpRel) {
		this.helpRel = helpRel;
	}
	
	
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public Integer getUserAge() {
		return userAge;
	}
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Integer getHiCoin() {
		return hiCoin;
	}
	public void setHiCoin(Integer hiCoin) {
		this.hiCoin = hiCoin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public boolean isVipUser() {
		return vipUser;
	}
	public void setVipUser(boolean vipUser) {
		this.vipUser = vipUser;
	}
}
