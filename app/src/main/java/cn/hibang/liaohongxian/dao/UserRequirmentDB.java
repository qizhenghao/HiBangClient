package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.domain.MyUserRequire;
import cn.hibang.bruce.config.*;
import cn.hibang.huxing.msgutility.UserRequirement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UserRequirmentDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "UserRequirmentDB.db";
	private final static String TABLE_NAME = "UserRequirment";
	private final static int VERSION = 1;

	public UserRequirmentDB(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static UserRequirmentDB newInstance(Context context) {
		if (instance == null) {
			synchronized (UserRequirmentDB.class) {
				if (instance == null)
					instance = new UserRequirmentDB(context, DATABASE_NAME,
							null, VERSION);
			}
		}
		return (UserRequirmentDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "requrId integer NOT NULL PRIMARY KEY,"
				+ "userID integer NOT NULL," + "userName VARCHAR NOT NULL,"
				+ "reqItemID INTEGER NOT NULL,"
				+ "reqItemName VARCHAR NOT NULL,"
				+ "reqDetail VARCHAR NOT NULL," + "startTime VARCHAR NOT NULL,"
				+ "endTime VARCHAR NOT NULL," + "isNoRead BOOLEAN NOT NULL"
				+ ")";

		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS" + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public void addRequirement(List<UserRequirement> list, boolean isNoRead) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ContentValues cv = new ContentValues();
				cv.put("requrId", list.get(i).getHelpme_id());
				cv.put("userID", list.get(i).getUserID());
				cv.put("userName", list.get(i).getUserName());
				cv.put("reqItemID", list.get(i).getReqItemID());
				cv.put("reqItemName", list.get(i).getReqItemName());
				cv.put("reqDetail", list.get(i).getReqDetail());
				cv.put("startTime", list.get(i).getStartTime());
				cv.put("endTime", list.get(i).getEndTime());
				cv.put("isNoRead", isNoRead);
				db.insert(TABLE_NAME, null, cv);
			}
		}
	}

	public MyUserRequire getReqireById(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		MyUserRequire conMyUserRequire = new MyUserRequire();
		Cursor cursor = db.rawQuery(
				"select * from " + TABLE_NAME + " where requrId=?",
				new String[] { id + "" });
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToNext();
		int index = 0;
		index = cursor.getColumnIndex("requrId");
		conMyUserRequire.setHelpme_id(cursor.getInt(index));
		conMyUserRequire.setUserID(cursor.getInt(cursor
				.getColumnIndex("userID")));
		conMyUserRequire.setUserName(cursor.getString(cursor
				.getColumnIndex("userName")));
		conMyUserRequire.setReqItemID(cursor.getInt(cursor
				.getColumnIndex("reqItemID")));
		conMyUserRequire.setReqItemName(cursor.getString(cursor
				.getColumnIndex("reqItemName")));
		conMyUserRequire.setReqDetail(cursor.getString(cursor
				.getColumnIndex("reqDetail")));
		conMyUserRequire.setStartTime(cursor.getString(cursor
				.getColumnIndex("startTime")));
		conMyUserRequire.setEndTime(cursor.getString(cursor
				.getColumnIndex("endTime")));

		if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1)
			conMyUserRequire.setNoRead(true);
		else
			conMyUserRequire.setNoRead(false);

		return conMyUserRequire;
	}

	public void deleteRequireById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String sql = "delete from TABLE_NAME WHERE seqItemID=id";
		db.delete(TABLE_NAME, " requrId=?", new String[] { id + " " });
		db.close();
		// db.execSQL(sql);

	}

	public List<MyUserRequire> getRequireByCount(int count) {
		SQLiteDatabase db = this.getWritableDatabase();
		int index;
		if (count == 1) {
			index = 0;
		} else
			index = Config.DB_REQUIER_SIZE * count - 1;

		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " order by requrId desc limit " + index + ",?",
				new String[] { Config.DB_REQUIER_SIZE + " " });
		return ConvertToList(cursor);

	}

	private List<MyUserRequire> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MyUserRequire> myList = new ArrayList<MyUserRequire>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		do {
			MyUserRequire myUserRequires = new MyUserRequire();
			myUserRequires
					.setHelpme_id(cursor.getInt(cursor.getColumnIndex("requrId")));
			myUserRequires.setUserID(cursor.getInt(cursor
					.getColumnIndex("userID")));
			myUserRequires.setUserName(cursor.getString(cursor
					.getColumnIndex("userName")));
			myUserRequires.setReqItemID(cursor.getInt(cursor
					.getColumnIndex("reqItemID")));
			myUserRequires.setReqItemName(cursor.getString(cursor
					.getColumnIndex("reqItemName")));
			myUserRequires.setReqDetail(cursor.getString(cursor
					.getColumnIndex("reqDetail")));
			myUserRequires.setStartTime(cursor.getString(cursor
					.getColumnIndex("startTime")));
			myUserRequires.setEndTime(cursor.getString(cursor
					.getColumnIndex("endTime")));

			if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1) {
				myUserRequires.setNoRead(true);
			} else {
				myUserRequires.setNoRead(false);
			}
			myList.add(myUserRequires);

		} while (cursor.moveToNext());
		return myList;
	}

	public void deleteRequireMax(int max) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "delete from " + TABLE_NAME + " order by requrId limit "
				+ max;
		db.execSQL(sql);
		db.close();

	}

	public int getRequireNoReadCoun() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " where isNoRead=1", null);
		return cursor.getCount();
	}

}
