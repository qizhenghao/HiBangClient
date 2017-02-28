package cn.hibang.liuzhiwei.testentity;



public class SettingItem {
	
	private int settingPhotoId;
	private String settingName;
	
	public SettingItem(int settingPhotoId, String settingName)
	{
		this.settingPhotoId = settingPhotoId;
		this.settingName = settingName;
	}

	public int getSettingPhotoId() {
		return settingPhotoId;
	}

	public void setSettingPhotoId(int settingPhotoId) {
		this.settingPhotoId = settingPhotoId;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	
	

}

