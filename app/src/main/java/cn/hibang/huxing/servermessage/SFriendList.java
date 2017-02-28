package cn.hibang.huxing.servermessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.hibang.bruce.config.JSONContext;
import cn.hibang.huxing.clientmessage.IMessage;
import cn.hibang.huxing.msgutility.HiBangUser;




public class SFriendList extends IMessage{

	private static final long serialVersionUID = -8766065098598750335L;
	
	private List<HiBangUser> friendList = new ArrayList<HiBangUser>();

	public SFriendList() {
		super();
	}
	
	
	
	public List<HiBangUser> getFriendList() {
		return friendList;
	}



	public void setFriendList(List<HiBangUser> friendList) {
		this.friendList = friendList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addHiBangUser(HiBangUser user){
		friendList.add(user);
	}
	
	


	@Override
	public String toString() {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(JSONContext.mapper.writeValueAsString(this));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getSerialversionuid()+jObj.toString();
	}
	
	
	@Override
	public Class relatedClass() {
		return HiBangUser.class;
	}
}
