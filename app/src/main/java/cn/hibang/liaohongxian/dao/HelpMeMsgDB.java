package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.domain.MySHelpMeMsg;
import cn.hibang.huxing.servermessage.SHelpMeMsg;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HelpMeMsgDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "HelpMeMsgDB.db";
	private final static String TABLE_NAME = "HelpMeMsg";
	private final static int VERSION = 1;

	public HelpMeMsgDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static HelpMeMsgDB newInstance(Context context) {
		if (instance == null) {
			synchronized (HelpMeMsgDB.class) {
				if (instance == null)
					instance = new HelpMeMsgDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (HelpMeMsgDB) instance;
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
				+ "reqItem VARCHAR NUT NULL,"
				+ "reqItemDetail VARCHAR NOT NULL," 
				+ "msgTime VARCHAR NOT NULL,"
				+ "startTime VARCHAR NOT NULL," 
				+ "endTime VARCHAR NOT NULL,"
				+ "isNoRead BOOLEAN NOT NULL" + ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public void addHelpMeMsg(SHelpMeMsg msg, boolean isNoRead) {
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

	public MySHelpMeMsg getHelpMeMsgById(int id) {

		SQLiteDatabase db = this.getReadableDatabase();
		MySHelpMeMsg mySHelpMeMsg = new MySHelpMeMsg();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where id =?",
				new String[] { id + " " });
		mySHelpMeMsg.setId(id);
		mySHelpMeMsg.setHelpme_id(cursor.getInt(cursor.getColumnIndex("helpme_id")));
		mySHelpMeMsg.setBeHelpedUserID(cursor.getInt(cursor
				.getColumnIndex("beHelpedID")));
		mySHelpMeMsg.setBeHelpedName(cursor.getString(cursor
				.getColumnIndex("beHelpedName")));
		mySHelpMeMsg.setHelpUserID(cursor.getInt(cursor
				.getColumnIndex("helpUserID")));
		mySHelpMeMsg.setHelpName(cursor.getString(cursor
				.getColumnIndex("helpName")));
		mySHelpMeMsg.setReqItemID(cursor.getInt(cursor
				.getColumnIndex("reqItemID")));
		mySHelpMeMsg.setReqItem(cursor.getString(cursor
				.getColumnIndex("reqItem")));
		mySHelpMeMsg.setReqDetail(cursor.getString(cursor
				.getColumnIndex("reqItemDetail")));
		mySHelpMeMsg.setMsgTime(cursor.getString(cursor.getColumnIndex("msgTime")));
		mySHelpMeMsg.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
		mySHelpMeMsg.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));

		if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1)
			mySHelpMeMsg.setNoRead(true);
		else
			mySHelpMeMsg.setNoRead(false);

		return mySHelpMeMsg;
	}

	public List<MySHelpMeMsg> getHelpMeMsgByCount(int count) {
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

	private List<MySHelpMeMsg> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MySHelpMeMsg> myList = new ArrayList<MySHelpMeMsg>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		
		do {
			MySHelpMeMsg sHelpMeMsg = new MySHelpMeMsg();
			sHelpMeMsg.setId(cursor.getInt(cursor.getColumnIndex("id")));
			sHelpMeMsg.setHelpme_id(cursor.getInt(cursor.getColumnIndex("helpme_id")));
			sHelpMeMsg.setBeHelpedUserID(cursor.getInt(cursor
					.getColumnIndex("beHelpedID")));
			sHelpMeMsg.setBeHelpedName(cursor.getString(cursor
					.getColumnIndex("beHelpedName")));
			sHelpMeMsg.setHelpUserID(cursor.getInt(cursor
					.getColumnIndex("helpUserID")));
			sHelpMeMsg.setHelpName(cursor.getString(cursor
					.getColumnIndex("helpName")));
			sHelpMeMsg.setReqItemID(cursor.getInt(cursor
					.getColumnIndex("reqItemID")));
			sHelpMeMsg.setReqItem(cursor.getString(cursor
					.getColumnIndex("reqItem")));
			sHelpMeMsg.setReqDetail(cursor.getString(cursor
					.getColumnIndex("reqItemDetail")));
			sHelpMeMsg.setMsgTime(cursor.getString(cursor.getColumnIndex("msgTime")));
			sHelpMeMsg.setEndTime(cursor.getString(cursor.getColumnIndex("endTime")));
			sHelpMeMsg.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
			
			if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1) {
				sHelpMeMsg.setNoRead(true);
			} else {
				sHelpMeMsg.setNoRead(false);
			}
			myList.add(sHelpMeMsg);
			
		}while(cursor.moveToNext());
		return myList;
	}

	public void deleteHelpMeMsgById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id=?",
				new String[] { Integer.toString(id) });
		db.close();

	}

	public void deleteHelpMeMsgMax(int max) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "delete from " + TABLE_NAME + " order by id limit " + max;
		db.execSQL(sql);
		db.close();

	}

	public int getHelpMeMsgNoReadCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where isNoRead=1",null);
		return cursor.getCount();
	}
}
