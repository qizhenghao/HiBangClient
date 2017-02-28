package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.domain.MyPubHelpMe;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PubHelpMeDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "PubHelpMeDB.db";
	private final static String TABLE_NAME = "PubHelpMe";
	private final static int VERSION = 1;

	public PubHelpMeDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static PubHelpMeDB newInstance(Context context) {
		if (instance == null) {
			synchronized (PubHelpMeDB.class) {
				if (instance == null)
					instance = new PubHelpMeDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (PubHelpMeDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "id INTEGER NOT NULL primary key,"
				+ "userID INTEGER NOT NULL," 
				+ "reqItemID INTEGER NOT NULL,"
				+ "reqItemName VARCHAR NOT NULL,"
				+ "reqDetail VARCHAR NOT NULL,"
				+ "startTime VARCHAR NOT NULL,"
				+ "endTime VARCHAR NOT NULL"
				+ ")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS" + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);

	}

	public void addCPubHelpMe(MyPubHelpMe msg) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
	    cv.put("id", getCount());
		cv.put("userID", msg.getUserID());
		cv.put("reqItemName", msg.getReqItemName());
		cv.put("reqItemID", msg.getReqItemID());
		cv.put("reqDetail", msg.getReqDetail());
		cv.put("startTime", msg.getStartTime());
		cv.put("endTime", msg.getEndTime());
		db.insert(TABLE_NAME, null, cv);

	}
	
	public int getCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		int n = 0;
		cursor = db.rawQuery("select * from " + TABLE_NAME , null);
		n = cursor.getCount();
		return n;
	}

	public MyPubHelpMe getCPubHelpMeById(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		MyPubHelpMe myCPubHelpMeMsg = new MyPubHelpMe();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where id =?",
				new String[] { id + " " });
		myCPubHelpMeMsg
				.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
		myCPubHelpMeMsg.setReqItemID(cursor.getInt(cursor
				.getColumnIndex("reqItemID")));
		myCPubHelpMeMsg.setReqItemName(cursor.getString(cursor.getColumnIndex("reqItemName")));
		myCPubHelpMeMsg.setReqDetail(cursor.getString(cursor
				.getColumnIndex("reqDetail")));
		myCPubHelpMeMsg.setStartTime(cursor.getString(cursor
				.getColumnIndex("startTime")));
		myCPubHelpMeMsg.setEndTime(cursor.getString(cursor
				.getColumnIndex("endTime")));
		return myCPubHelpMeMsg;
	}

	public List<MyPubHelpMe> getAllCPubHelpMe() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME , null);
		return ConvertToList(cursor);
	}

	private static List<MyPubHelpMe> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MyPubHelpMe> myList = new ArrayList<MyPubHelpMe>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		do {
			MyPubHelpMe myCPubHelpMeMsg = new MyPubHelpMe();
			myCPubHelpMeMsg.setId(cursor.getInt(cursor.getColumnIndex("id")));
			myCPubHelpMeMsg.setUserID(cursor.getInt(cursor
					.getColumnIndex("userID")));
			myCPubHelpMeMsg.setReqItemID(cursor.getInt(cursor
					.getColumnIndex("reqItemID")));
			myCPubHelpMeMsg.setReqItemName(cursor.getString(cursor.getColumnIndex("reqItemName")));
			myCPubHelpMeMsg.setReqDetail(cursor.getString(cursor
					.getColumnIndex("reqDetail")));
			myCPubHelpMeMsg.setStartTime(cursor.getString(cursor
					.getColumnIndex("startTime")));
			myCPubHelpMeMsg.setEndTime(cursor.getString(cursor
					.getColumnIndex("endTime")));

			myList.add(myCPubHelpMeMsg);
			
		}while(cursor.moveToNext());
		return myList;
	}

	public void deleteCPubHelpMe(int id) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id=?", new String[] { id + " " });
		db.close();

	}

}
