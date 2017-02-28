package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.domain.MyPubMeHelp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PubMeHelpDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "PubMeHelpDB.db";
	private final static String TABLE_NAME = "PubMeHelp";
	private final static int VERSION = 1;

	public PubMeHelpDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static PubMeHelpDB newInstance(Context context) {
		if (instance == null) {
			synchronized (PubMeHelpDB.class) {
				if (instance == null)
					instance = new PubMeHelpDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (PubMeHelpDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "id INTEGER NOT NULL primary key,"
				+ "helpUserID INTEGER NOT NULL,"
				+ "reqItemID INTEGER NOT NULL,"
				+ "reqItemName VARCHAR NOT NULL,"
				+ "startTime VARCHAR NOT NULL," 
				+ "endTime VARCHAR NOT NULL"
				+ ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public void addCPubMeHelp(MyPubMeHelp msg) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("id", getCount());
		cv.put("reqItemName", msg.getReqItemName());
		cv.put("helpUserID", msg.getHelpUserID());
		cv.put("reqItemID", msg.getReqItemID());
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

	public List<MyPubMeHelp> getAllCPubMeHelp() {

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME , null);
		return ConvertToList(cursor);
	}

	private static List<MyPubMeHelp> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MyPubMeHelp> myList = new ArrayList<MyPubMeHelp>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		do {
			MyPubMeHelp myCPubMeHelpMsg = new MyPubMeHelp();
			myCPubMeHelpMsg.setId(cursor.getInt(cursor.getColumnIndex("id")));
			myCPubMeHelpMsg.setReqItemName(cursor.getString(cursor.getColumnIndex("reqItemName")));
			myCPubMeHelpMsg.setHelpUserID(cursor.getInt(cursor
					.getColumnIndex("helpUserID")));
			myCPubMeHelpMsg.setReqItemID(cursor.getInt(cursor
					.getColumnIndex("reqItemID")));
			myCPubMeHelpMsg.setStartTime(cursor.getString(cursor
					.getColumnIndex("startTime")));
			myCPubMeHelpMsg.setEndTime(cursor.getString(cursor
					.getColumnIndex("endTime")));
			
			myList.add(myCPubMeHelpMsg);
		}while(cursor.moveToNext());
		return myList;
	}

	public MyPubMeHelp getCPubMeHelpById(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		MyPubMeHelp myCPubMeHelpMsg = new MyPubMeHelp();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where id =?",
				new String[] { id + " " });
		myCPubMeHelpMsg.setHelpUserID(cursor.getInt(cursor
				.getColumnIndex("helpUserID")));
		myCPubMeHelpMsg.setReqItemID(cursor.getInt(cursor
				.getColumnIndex("reqItemID")));
		myCPubMeHelpMsg.setReqItemName(cursor.getString(cursor.getColumnIndex("reqItemName")));
		myCPubMeHelpMsg.setStartTime(cursor.getString(cursor
				.getColumnIndex("startTime")));
		myCPubMeHelpMsg.setEndTime(cursor.getString(cursor
				.getColumnIndex("endTime")));
		return myCPubMeHelpMsg;
	}

	public void deleteCPubMeHelp(int id) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id=?", new String[] { id + " " });
		db.close();

	}

}
