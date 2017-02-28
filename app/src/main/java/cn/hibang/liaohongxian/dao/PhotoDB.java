package cn.hibang.liaohongxian.dao;

import cn.hibang.bruce.domain.MyPhoto;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotoDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "PhotoDB.db";
	private final static String TABLE_NAME = "PhotoList";
	private final static int VERSION = 1;

	public PhotoDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static PhotoDB newInstance(Context context) {
		if (instance == null) {
			synchronized (PhotoDB.class) {
				if (instance == null)
					instance = new PhotoDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (PhotoDB) instance;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);

	}

	public void addPhoto(MyPhoto photo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("friendID", photo.getFriendId());
		cv.put("photoPath", photo.getPath());
		db.insert(TABLE_NAME, null, cv);

	}

	public void deletePhotoById(int friendId) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "friendID=?", new String[] { friendId + " " });
		db.close();

	}

	public void changePhotoById(int friendID, String newPath) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "update PhotoList set photoPath=" + newPath
				+ " where friendID=" + friendID;
		db.execSQL(sql);

	}

	public String getPhotoPathById(int friendId) {

		SQLiteDatabase db = this.getReadableDatabase();
		String gotPath = null;
		Cursor cursor;
		String sql = "select * from " + TABLE_NAME + " where friendID=?";
		cursor = db.rawQuery(sql, new String[] { friendId + "" });
		if(cursor.moveToFirst()) {
			gotPath = cursor.getString(cursor.getColumnIndex("photoPath"));
		}
		
		return gotPath;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "friendID INTEGER NOT NULL," + "photoPath VARCHAR NOT NULL"
				+ ")";
		db.execSQL(sql);
	}

}
