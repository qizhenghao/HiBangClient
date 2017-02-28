package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.domain.MySMeHelpMsg;
import cn.hibang.huxing.servermessage.SMeHelpMsg;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MeHelpMsgDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "MeHelpMsgDB.db";
	private final static String TABLE_NAME = "MeHelpMsg";
	private final static int VERSION = 1;

	public MeHelpMsgDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static MeHelpMsgDB newInstance(Context context) {
		if (instance == null) {
			synchronized (MeHelpMsgDB.class) {
				if (instance == null)
					instance = new MeHelpMsgDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (MeHelpMsgDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "id INTEGER NOT NULL primary key,"
				+ "helpme_id INTEGER NOT NULL,"
				+ "beHelpedID INTEGER NOT NULL,"
				+ "beHelpedName VARCHAR NOT NULL,"
				+ "helpUserID INTEGER NOT NULL," 
				+ "helpName VARCHAR NOT NULL,"
				+ "reqItemID INTEGER NOT NULL,"
				+ "reqItem VARCHAR NOT NULL,"
				+ "reqItemDetail VARCHAR NOT NULL,"
				+ "msgTime VARCHAR NOT NULL,"
				+ "startTime VARCHAR NOT NULL," 
				+ "endTime VARCHAR NOT NULL,"
				+ "isnoRead BOOLE NOT NULL" + ")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public void addMeHelpMsg(SMeHelpMsg msg, boolean isNoRead) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("id", getCount());
		cv.put("helpme_id", msg.getHelpme_id());
		cv.put("beHelpedID", msg.getBeHelpedUserID());
		cv.put("beHelpedName", msg.getBeHelpedName());
		cv.put("helpUserID", msg.getHelpUserID());
		cv.put("helpName", msg.getHelpName());
		cv.put("reqItemID", msg.getReqItemID());
		cv.put("reqItem", msg.getReqItem());
		cv.put("reqItemDetail", msg.getReqDetail());
		cv.put("msgTime", msg.getMsgTime());
		cv.put("startTime", msg.getStartTime());
		cv.put("endTime", msg.getEndTime());
		cv.put("isnoRead", isNoRead);
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

	public MySMeHelpMsg getMeHelpMsgById(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		MySMeHelpMsg mySMeHelpMsg = new MySMeHelpMsg();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where id =?",
				new String[] { id + " " });
		mySMeHelpMsg.setId(id);
		mySMeHelpMsg.setHelpme_id(cursor.getInt(cursor.getColumnIndex("helpme_id")));
		mySMeHelpMsg.setBeHelpedUserID(cursor.getInt(cursor
				.getColumnIndex("beHelpedID")));
		mySMeHelpMsg.setBeHelpedName(cursor.getString(cursor
				.getColumnIndex("beHelpedName")));
		mySMeHelpMsg.setHelpUserID(cursor.getInt(cursor
				.getColumnIndex("helpUserID")));
		mySMeHelpMsg.setHelpName(cursor.getString(cursor
				.getColumnIndex("helpName")));
		mySMeHelpMsg.setReqItemID(cursor.getInt(cursor
				.getColumnIndex("reqItemID")));
		mySMeHelpMsg.setReqItem(cursor.getString(cursor
				.getColumnIndex("reqItem")));
		mySMeHelpMsg.setReqDetail(cursor.getString(cursor
				.getColumnIndex("reqItemDetail")));
		mySMeHelpMsg.setMsgTime(cursor.getString(cursor.getColumnIndex("msgTime")));
		mySMeHelpMsg.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
		mySMeHelpMsg.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));

		if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1)
			mySMeHelpMsg.setNoRead(true);
		else
			mySMeHelpMsg.setNoRead(false);

		return mySMeHelpMsg;

	}

	public List<MySMeHelpMsg> getMeHelpMsgByCount(int count) {
		SQLiteDatabase db = this.getWritableDatabase();
		int index;
		if (count == 1) {
			index = 0;
		} else
			index = Config.DB_REQUIER_SIZE * count - 1;

		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " order by id desc limit " + index + ","
				+ Config.DB_REQUIER_SIZE, null);
		return ConvertToList(cursor);
	}

	private static List<MySMeHelpMsg> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MySMeHelpMsg> myList = new ArrayList<MySMeHelpMsg>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		
		do {
			MySMeHelpMsg mySMeHelpMsg = new MySMeHelpMsg();
			mySMeHelpMsg.setId(cursor.getInt(cursor.getColumnIndex("id")));
			mySMeHelpMsg.setHelpme_id(cursor.getInt(cursor.getColumnIndex("helpme_id")));
			mySMeHelpMsg.setBeHelpedUserID(cursor.getInt(cursor
					.getColumnIndex("beHelpedID")));
			mySMeHelpMsg.setBeHelpedName(cursor.getString(cursor
					.getColumnIndex("beHelpedName")));
			mySMeHelpMsg.setHelpUserID(cursor.getInt(cursor
					.getColumnIndex("helpUserID")));
			mySMeHelpMsg.setHelpName(cursor.getString(cursor
					.getColumnIndex("helpName")));
			mySMeHelpMsg.setReqItemID(cursor.getInt(cursor
					.getColumnIndex("reqItemID")));
			mySMeHelpMsg.setReqItem(cursor.getString(cursor
					.getColumnIndex("reqItem")));
			mySMeHelpMsg.setReqDetail(cursor.getString(cursor
					.getColumnIndex("reqItemDetail")));
			mySMeHelpMsg.setMsgTime(cursor.getString(cursor.getColumnIndex("msgTime")));
			mySMeHelpMsg.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
			mySMeHelpMsg.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));

			if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1) {
				mySMeHelpMsg.setNoRead(true);
			} else {
				mySMeHelpMsg.setNoRead(false);
			}
			myList.add(mySMeHelpMsg);
			
		}while(cursor.moveToNext());
		return myList;
	}

	public void deleteMeHelpMsgById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, " id=?",
				new String[] { id+ " " });
		db.close();

	}

	public void deleteMeHelpMsgMax(int max) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "delete from " + TABLE_NAME + " order by id limit " + max;
		db.execSQL(sql);
		db.close();

	}

	
	public int getMeHelpMsgNoReadCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " where isNoRead=1", null);
		return cursor.getCount();
	}

}
