package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.config.Config;
import cn.hibang.bruce.domain.MyChatMsg;
import cn.hibang.huxing.clientmessage.CChatMessage;
import cn.hibang.huxing.servermessage.SChatMessage;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatMsgDB extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "ChatMsgDB.db";
	private final static String TABLE_NAME = "ChatMsg";
	private final static int VERSION = 1;



	public ChatMsgDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static ChatMsgDB newInstance(Context context) {
		if (instance == null) {
			synchronized (ChatMsgDB.class) {
				if (instance == null)
					instance = new ChatMsgDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (ChatMsgDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "msgID VARCHAR NOT NULL PRIMARY KEY,"
				+ "friendID INTEGER NOT NULL,"
				+ "sendID INTEGER NOT NULL,"
				+ "content VARCHAR NOT NULL," 
				+ "chatTime VARCHAR NOT NULL,"
				+ "isNoRead BOOLEAN NOT NULL" + ")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS" + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public void addCChatMsg(CChatMessage msg) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
//		
		cv.put("msgID", getCount());
		cv.put("sendID", msg.getUserID());
		cv.put("friendID", msg.getFriendID());
		cv.put("content", msg.getChatContent());
		cv.put("chatTime", msg.getChatTime());
		cv.put("isNoRead", false);
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
	
	public void addSChatMsg(SChatMessage msg, boolean noRead) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("msgID", getCount());
		cv.put("friendID",msg.getSenderID());
		cv.put("sendID", msg.getSenderID());
		cv.put("content", msg.getChatContent());
		cv.put("chatTime", msg.getChatTime());
		cv.put("isNoRead", noRead);
		db.insert(TABLE_NAME, null, cv);

	}

	public void deleteChatMsgById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, " msgID=?", new String[] { id + " " });
		db.close();

	}

	public void deleteChatMsgByFriendId(int friendId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, " friendID=?",
				new String[] { Integer.toString(friendId) });
		db.close();

	}

	public MyChatMsg getChatMsgById(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		MyChatMsg myChatMsg = new MyChatMsg();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where msgID =?",
				new String[] { id + " " });

		myChatMsg.setMsgId(cursor.getInt(cursor.getColumnIndex("msgID")));
		myChatMsg.setFriendId(cursor.getInt(cursor.getColumnIndex("friendId")));
		myChatMsg.setSenderId(cursor.getInt(cursor.getColumnIndex("sendId")));
		myChatMsg.setMsgContent(cursor.getString(cursor
				.getColumnIndex("content")));

//		Date d = new Date();
//		String string = cursor.getString(cursor.getColumnIndex("publishTime"));
//		SimpleDateFormat sDateFormat = new SimpleDateFormat();
//		try {
//			d = sDateFormat.parse(string);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		myChatMsg.setChatTime(d);
		myChatMsg.setChatTime(cursor.getString(cursor.getColumnIndex("publishTime")));

		if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1) {
			myChatMsg.setNoRead(true);
		} else
			myChatMsg.setNoRead(false);

		return myChatMsg;
	}

	public List<MyChatMsg> getChatMsgByFriendId(int friendId, int count) {

		SQLiteDatabase db = this.getWritableDatabase();
		int index;
		if (count == 1) {
			index = 0;
		} else
			index = Config.DB_REQUIER_SIZE * count - 1;

		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " where friendID = " + friendId + " order by msgID desc limit "
				+ index + "," + Config.DB_REQUIER_SIZE, null);
		return ConvertToList(cursor);
	}

	private List<MyChatMsg> ConvertToList(Cursor cursor) {
		int resultCounts = cursor.getCount();
		List<MyChatMsg> myList = new ArrayList<MyChatMsg>();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return myList;
		}
		
		do {
			MyChatMsg myChatMsg =new MyChatMsg();
			myChatMsg = new MyChatMsg();
			myChatMsg.setMsgId(cursor.getInt(cursor.getColumnIndex("msgID")));
			myChatMsg.setFriendId(cursor.getInt(cursor
					.getColumnIndex("friendID")));
			myChatMsg.setSenderId(cursor.getInt(cursor
					.getColumnIndex("sendID")));
			myChatMsg.setMsgContent(cursor.getString(cursor
					.getColumnIndex("content")));

			myChatMsg.setChatTime(cursor.getString(cursor.getColumnIndex("chatTime")));

			if (cursor.getInt(cursor.getColumnIndex("isNoRead")) == 1) {
				myChatMsg.setNoRead(true);
			} else
				myChatMsg.setNoRead(false);
			myList.add(myChatMsg);
		}while(cursor.moveToNext());
		return myList;
	}

	
	public int getChatMsgNoReadCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME
				+ " where isNoRead=1", null);
		return cursor.getCount();
	}
}
