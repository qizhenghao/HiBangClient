package cn.hibang.bruce.control;

import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.HiBangUser;
import android.content.Context;
import android.content.SharedPreferences;

public class HiBang {
	
	public final String VERSON = "1.0";
	private Context context = null;
	
	private long mExpriesTime;
	private long mCreateTime;
	
	private String mAccessToken;
	
	public HiBangUser getUser() {
		return user;
	}

	public void setUser(HiBangUser user) {
		this.user = user;
	}


	private HiBangUser user = null;

	public HiBang(Context context) {
		this.context = context;
		user = new HiBangUser();
		initAccessToken();
	}

	
	public void storeAccessToken(HiBangUser user,String psd, String accesstoken, long expriesTime) {
		this.user = user;
		this.mAccessToken = accesstoken;
		this.mExpriesTime = expriesTime;
		this.mCreateTime = System.currentTimeMillis();
		SharedPreferences sp = context.getSharedPreferences(HiBang_CONFIG,
				Context.MODE_PRIVATE);
		sp.edit().putString(HiBang_CONFIG_ACCESSTOKEN, this.mAccessToken)
		         .putLong(HiBang_CONFIG_EXPRIESTIME, this.mExpriesTime)
				 .putLong(HiBang_CONFIG_CREATETIME, this.mCreateTime)
		         .putString(HiBang_CONFIG_ID, user.getUserID().toString())
		         .putString(HiBang_CONFIG_NAME, user.getUsername())
		         .putString(HiBang_CONFIG_PASSWORD, psd)
		         .putString(HiBang_CONFIG_email, user.getEmail())
		         .putString(HiBang_CONFIG_phone, user.getPhone())
		         .putInt(HiBang_CONFIG_userAge, user.getUserAge())
		         .putString(HiBang_CONFIG_SCHOOL, user.getSchool())
		         .putString(HiBang_CONFIG_gender, user.getGender().toString())
		         .putBoolean(HiBang_CONFIG_vipUser, user.isVipUser())
		         .putInt(HiBang_CONFIG_hiCoin, user.getHiCoin())
		         .putString(HiBang_CONFIG_ps, user.getPs())
		         .commit();
		initAccessToken();
	}
	
	private void initAccessToken() {
		if (context == null) {
			return;
		}
		SharedPreferences sp = context.getSharedPreferences(HiBang_CONFIG,
				Context.MODE_PRIVATE);
		this.mAccessToken = sp.getString(HiBang_CONFIG_ACCESSTOKEN, null);
		user.setUserID(Integer.valueOf(sp.getString(HiBang_CONFIG_ID, "000")));
		user.setUsername(sp.getString(HiBang_CONFIG_NAME, NO_RESULT));
		user.setUserAge(sp.getInt(HiBang_CONFIG_userAge, 000));
		user.setEmail(sp.getString(HiBang_CONFIG_email, NO_RESULT));
		if(sp.getString(HiBang_CONFIG_gender, NO_RESULT).equals(GENDER.MALE.toString())) {
			user.setGender(GENDER.MALE);
		} else {
			user.setGender(GENDER.FEMALE);
		}
		user.setHiCoin(sp.getInt(HiBang_CONFIG_hiCoin, 000));
		user.setPhone(sp.getString(HiBang_CONFIG_phone, "000"));
		user.setPassword(sp.getString(HiBang_CONFIG_PASSWORD, NO_RESULT));
		user.setSchool(sp.getString(HiBang_CONFIG_SCHOOL, NO_RESULT));
		user.setPs(sp.getString(HiBang_CONFIG_ps, NO_RESULT));
		user.setVipUser(sp.getBoolean(HiBang_CONFIG_vipUser, false));
		
//		long expries = mCreateTime + mExpriesTime - ONE_HOUR;
//		long current = System.currentTimeMillis();
//		// AccessToken过期
//		if (current > expries) {
//			clearAccessToken();
//		}
	}
	
	/**
	 * 返回当前是否存储AccessToken
	 * 
	 * @return true-存在 false-不存在
	 */
	public boolean isAccessTokenExist() {
		if (mAccessToken == null) {
			return false;
		}
		return true;
	}


	public void clearAccessToken() {
		SharedPreferences sp = context.getSharedPreferences(HiBang_CONFIG,
				Context.MODE_PRIVATE);
		sp.edit()
		.remove(HiBang_CONFIG_ACCESSTOKEN)
		.remove(HiBang_CONFIG_CREATETIME)
		.remove(HiBang_CONFIG_EXPRIESTIME)
		.remove(HiBang_CONFIG_ID)
		.remove(HiBang_CONFIG_NAME)
		.remove(HiBang_CONFIG_PASSWORD)
		.remove(HiBang_CONFIG_SCHOOL)
		.remove(HiBang_CONFIG_email)
		.remove(HiBang_CONFIG_gender)
		.remove(HiBang_CONFIG_hiCoin)
		.remove(HiBang_CONFIG_phone)
		.remove(HiBang_CONFIG_ps)
		.remove(HiBang_CONFIG_userAge)
		.remove(HiBang_CONFIG_vipUser)
		.commit();
		this.mAccessToken = null;
		this.mExpriesTime = 0;
		this.mCreateTime = 0;
	}



	private static final String NO_RESULT = "无法获取";
	private static final String HiBang_CONFIG_ACCESSTOKEN = "HiBang_config_accesstoken";
	private static final String HiBang_CONFIG_EXPRIESTIME = "HiBang_config_expriestime";
	private static final String HiBang_CONFIG_CREATETIME = "HiBang_config_createtime";
	private static final String HiBang_CONFIG = "HiBang_config";
	private static final String HiBang_CONFIG_ID = "HiBang_config_id";
    private static final String HiBang_CONFIG_NAME = "HiBang_config_name";
    private static final String HiBang_CONFIG_PASSWORD = "HiBang_config_password";
    private static final String HiBang_CONFIG_SCHOOL = "HiBang_config_school";
    private static final String HiBang_CONFIG_email = "HiBang_config_email";
    private static final String HiBang_CONFIG_vipUser = "HiBang_config_vipUser";
    private static final String HiBang_CONFIG_hiCoin = "HiBang_config_hiCoin";
    private static final String HiBang_CONFIG_userAge = "HiBang_config_userAge";
    private static final String HiBang_CONFIG_gender = "HiBang_config_gender";
    private static final String HiBang_CONFIG_ps = "HiBang_config_ps";
    private static final String HiBang_CONFIG_phone = "HiBang_config_phone";
    private static final long ONE_HOUR = 60 * 60 * 1000;
}
