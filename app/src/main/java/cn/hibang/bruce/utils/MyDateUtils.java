package cn.hibang.bruce.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {

	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String formatDateTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//今天
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar tomorrow = Calendar.getInstance();	//明天
		
		tomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
		tomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
		tomorrow.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)+1);
		tomorrow.set( Calendar.HOUR_OF_DAY, 0);
		tomorrow.set( Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		
        Calendar afterTomorrow = Calendar.getInstance();	//后天
		
        afterTomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        afterTomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        afterTomorrow.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)+2);
        afterTomorrow.set( Calendar.HOUR_OF_DAY, 0);
        afterTomorrow.set( Calendar.MINUTE, 0);
        afterTomorrow.set(Calendar.SECOND, 0);
		
        Calendar bigAfterTomorrow = Calendar.getInstance();	//大后天
		
        bigAfterTomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        bigAfterTomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        bigAfterTomorrow.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)+3);
        bigAfterTomorrow.set( Calendar.HOUR_OF_DAY, 0);
        bigAfterTomorrow.set( Calendar.MINUTE, 0);
        bigAfterTomorrow.set(Calendar.SECOND, 0);
        
		current.setTime(date);
		
		if(current.after(today) && current.before(tomorrow)){
			return "今天  ";
		}else if(current.before(afterTomorrow) && current.after(tomorrow)){
			return "明天  ";
		}else if(current.before(bigAfterTomorrow) && current.after(afterTomorrow)){
			return "后天  ";
		}else{
//			int index = time.indexOf("-")+1;
//			return time.substring(index, time.length());
			return "";
		}
	}

	public static String getHourAndMinute(String time) {
		if(time==null ||"".equals(time)){
			return "";
		}
		String hourMinute = "";
		hourMinute = time.substring(11, 16);
		return hourMinute;
	}

}
