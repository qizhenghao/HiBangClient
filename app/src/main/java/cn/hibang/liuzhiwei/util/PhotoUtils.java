package cn.hibang.liuzhiwei.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.hibang.bruce.control.BaseApplication;
import cn.hibang.huxing.clientmessage.CPhotoRequestMsg;
import cn.hibang.liaohongxian.dao.DBManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class PhotoUtils {
	
	/**
	 * 获取颜色的圆角bitmap
	 * 
	 * @param context
	 * @param color
	 * @return
	 */
	public static Bitmap getRoundBitmap(Context context, int color) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 12.0f, metrics));
		int height = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4.0f, metrics));
		int round = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 2.0f, metrics));
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		canvas.drawRoundRect(new RectF(0.0F, 0.0F, width, height), round,
				round, paint);
		return bitmap;
	}
	
	/**
	 * 将bitmap转化为二进制数组
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		 return baos.toByteArray();
	 }
	
	public static Bitmap getPortraitById(int id,BaseApplication app) {
		String path = DBManage.getPhotoPathById(id);
		Bitmap portrait = null;
		try {
			if(path == null || "".equals(path)) {
				return null;
			} else {
				portrait = BitmapFactory
						.decodeStream(new FileInputStream(path));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if (portrait == null) {
				CPhotoRequestMsg msg = new CPhotoRequestMsg();
				msg.setUserID(id);
				app.client.sendMessage(msg);
			} else {
				return portrait;
			}
		}
		return null;
	}

}
