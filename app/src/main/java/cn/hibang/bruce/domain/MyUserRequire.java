package cn.hibang.bruce.domain;

import java.io.Serializable;

import cn.hibang.huxing.msgutility.UserRequirement;

public class MyUserRequire extends UserRequirement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1129793409231283L;
	boolean noRead = true;

	public boolean isNoRead() {
		return noRead;
		
	}

	public void setNoRead(boolean noRead) {
		this.noRead = noRead;
	}
	
}
