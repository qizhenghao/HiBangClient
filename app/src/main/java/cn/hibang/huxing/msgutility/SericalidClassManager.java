package cn.hibang.huxing.msgutility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hibang.bruce.config.Config;


public class SericalidClassManager {

	private static Map<Long, Class> serialidClassMap;
	private static SericalidClassManager instance = null;
	
	public static SericalidClassManager getInstance(){
		if(instance == null){
			synchronized (SericalidClassManager.class) {
				if(instance == null){
					instance = new SericalidClassManager();
					mappingSerialidMessageClass();
				}
			}
		}
		return instance;
	}

	public static void mappingSerialidMessageClass() {
		Class cls;
		String className;
		Method method;
		Long classID;
		serialidClassMap = new HashMap<Long, Class>();
//		List<String> list = null;
		List<String> list = new ArrayList<String>();
		try {
			//list = new JavaPackageExplorer(Config.MSG_PACKAGE_NAME).explore();
			list.add("cn.hibang.huxing.servermessage.SChatMessage");
			list.add("cn.hibang.huxing.servermessage.SFriendList");
			list.add("cn.hibang.huxing.servermessage.SHelpMeMsg");
			list.add("cn.hibang.huxing.servermessage.SLoginMsg");
			list.add("cn.hibang.huxing.servermessage.SMeHelpMsg");
			list.add("cn.hibang.huxing.servermessage.SNotificationMsg");
			list.add("cn.hibang.huxing.servermessage.SPhotoRequestMsg");
			list.add("cn.hibang.huxing.servermessage.SQueryResultMsg");
			list.add("cn.hibang.huxing.servermessage.SRecommendListMsg");
			list.add("cn.hibang.huxing.servermessage.SRegisterMsg");
			list.add("cn.hibang.huxing.servermessage.SUserInfoRequest");
			list.add("cn.hibang.huxing.servermessage.SOrderMsg");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (String str : list) {
			className = str;
			if(className.contains("$")==false){
				try {
					cls = Class.forName(className);
					method = cls.getDeclaredMethod("getSerialversionuid");
					classID = (Long) method.invoke(cls.newInstance(), null);
					serialidClassMap.put(classID, cls);
				} catch (Exception e) {					
				}
			}
			else
				continue;               
			
		}
	}
	
	
	public Class findClassById(long serialId){
		return  serialidClassMap.get(serialId);		
	}
	
	public static void main(String[] args) {
		SericalidClassManager.getInstance();		
		Set<Map.Entry<Long, Class>> set  = serialidClassMap.entrySet();
		Iterator<Map.Entry<Long, Class>> Iter = set.iterator();
		while (Iter.hasNext()) {
			Map.Entry<Long, Class> entry = Iter.next();
			System.out.println(entry.getKey() +"    " + entry.getValue());
		}
	}
}
