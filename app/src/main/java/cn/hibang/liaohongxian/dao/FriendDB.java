package cn.hibang.liaohongxian.dao;

import java.util.ArrayList;
import java.util.List;

import cn.hibang.bruce.config.Config;
import cn.hibang.huxing.clientmessage.GENDER;
import cn.hibang.huxing.msgutility.FriendState;
import cn.hibang.huxing.msgutility.HelpRelation;
import cn.hibang.huxing.msgutility.HiBangUser;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "FriendDB.db";
	private final static String TABLE_NAME = "FriendList";
	private final static int VERSION = 1;

	public FriendDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static SQLiteOpenHelper instance = null;

	public static FriendDB newInstance(Context context) {
		if (instance == null) {
			synchronized (FriendDB.class) {
				if (instance == null)
					instance = new FriendDB(context, DATABASE_NAME, null,
							VERSION);
			}
		}
		return (FriendDB) instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "( "
				+ "userID INTEGER NOT NULL primary key,"
				+ "userName VARCHAR NOT NULL,"
				+ "password VARCHAR,"
				+ "school VARCHAR," 
				+ "email VARCHAR,"
				+ "phone VARCHAR,"
				+ "photo VARCHAR,"
				+ "vipUser BOOLEAN," 
				+ "hiCoin INTEGER,"
				+ "userAge INTEGER,"
				+ "gender VARCHAR,"
				+ "ps VARCHAR,"
				+ "friendState VARCHAR NOT NULL,"
				+ "helpRel VARCHAR NOT NULL"
				+ ")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);

	}

	public void addFriendList(List<HiBangUser> list) {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				addFriend(list.get(i));
			}
		}
	}

	public void addFriend(HiBangUser user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("userID", user.getUserID());
		cv.put("userName", user.getUsername());
		cv.put("password", user.getPassword());
		cv.put("school", user.getSchool());
		cv.put("email", user.getEmail());
		cv.put("phone", user.getPhone());
		cv.put("photo", user.getPhoto());
		cv.put("vipUser", user.isVipUser());
		cv.put("hiCoin", user.getHiCoin());
		cv.put("userAge", user.getUserAge());
		cv.put("gender", user.getGender().toString());
		cv.put("ps", user.getPs());
		cv.put("friendState", user.getFriendState().toString());
		cv.put("helpRel", user.getHelpRel().toString());
		if (getFriendById(user.getUserID()) == null) {
			db.insert(TABLE_NAME, null, cv);
		} else {
			db.update(TABLE_NAME, cv, "userID="+user.getUserID(), null);
		}
		
	}

	public void deleteFriendById(int friendId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, " reqItemID=?",
				new String[] { Integer.toString(friendId) });
		db.close();

	}

	public void changeFriendStatusById(int friendId, FriendState state) {
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("friendState", state.toString());
		db.update(TABLE_NAME, cv, "friendID=?", new String[] { friendId + " " });

	}

	public HiBangUser getFriendById(int friendId) {

		SQLiteDatabase db = this.getReadableDatabase();
		HiBangUser myHiBangUser = null;
		Cursor cursor;
		cursor = db.rawQuery("select * from " + TABLE_NAME + " where userID =?",
				new String[] { friendId + " " });
		if (cursor.moveToFirst()) {
			myHiBangUser = new HiBangUser();
			myHiBangUser.setUserID(cursor.getInt(cursor
					.getColumnIndex("userID")));
			myHiBangUser.setUsername(cursor.getString(cursor
					.getColumnIndex("userName")));
			myHiBangUser.setPassword(cursor.getString(cursor
					.getColumnIndex("password")));
			myHiBangUser.setSchool(cursor.getString(cursor
					.getColumnIndex("school")));
			myHiBangUser.setEmail(cursor.getString(cursor
					.getColumnIndex("email")));
			myHiBangUser.setPhone(cursor.getString(cursor
					.getColumnIndex("phone")));

			if (cursor.getInt(cursor.getColumnIndex("vipUser")) == 1) {
				myHiBangUser.setVipUser(true);
			} else
				myHiBangUser.setVipUser(false);
			myHiBangUser.setHiCoin(cursor.getInt(cursor
					.getColumnIndex("hiCoin")));
			myHiBangUser.setUserAge(cursor.getInt(cursor
					.getColumnIndex("userAge")));

			if (cursor.getString(cursor.getColumnIndex("gender"))
					.equals("MALE")) {
				myHiBangUser.setGender(GENDER.MALE);
			} else
				myHiBangUser.setGender(GENDER.FEMALE);

			myHiBangUser.setPs(cursor.getString(cursor.getColumnIndex("ps")));

			String friendSate = cursor.getString(cursor
					.getColumnIndex("friendState"));
			if (friendSate.equals("NOTRADE"))
				myHiBangUser.setFriendState(FriendState.NOTRADE);
			else if (friendSate.equals("TRADING"))
				myHiBangUser.setFriendState(FriendState.TRADING);
			else if (friendSate.equals("SUCCESS"))
				myHiBangUser.setFriendState(FriendState.SUCCESS);
			else
				myHiBangUser.setFriendState(FriendState.FAIL);

			if (cursor.getString(cursor.getColumnIndex("helpRel")).equals(
					"HELP_ME")) {
				myHiBangUser.setHelpRel(HelpRelation.HELP_ME);
			} else
				myHiBangUser.setHelpRel(HelpRelation.ME_HELP);
		}

		return myHiBangUser;
	}

	public List<HiBangUser> getFriendByCount(int count, FriendState fState,
			HelpRelation hRelation) {

		SQLiteDatabase db = this.getWritableDatabase();
		int index;
		if (count == 1) {
			index = 0;
		} else
			index = Config.DB_REQUIER_SIZE * count - 1;

		String sql = "select * from " + TABLE_NAME + " where friendState= '"
				+ fState.toString() + "' and helpRel='" + hRelation.toString()
				+ "' order by userID desc limit " + index + ","
				+ Config.DB_REQUIER_SIZE;
		Cursor cursor = db.rawQuery(sql, null);
		return ConvertToList(cursor);
	}

	private List<HiBangUser> ConvertToList(Cursor cursor) {
		List<HiBangUser> myList = new ArrayList<HiBangUser>();
		HiBangUser newUser = null;
		if (!cursor.moveToFirst()) {
			return myList;
		}

		do {
			newUser = new HiBangUser();
			newUser.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
			newUser.setUsername(cursor.getString(cursor
					.getColumnIndex("userName")));
			newUser.setPassword(cursor.getString(cursor
					.getColumnIndex("password")));
			newUser.setSchool(cursor.getString(cursor.getColumnIndex("school")));
			newUser.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			newUser.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
			// myHiBangUser[i].setPhoto(cursor.getString(cursor
			// .getColumnIndex("photo")));

			if (cursor.getInt(cursor.getColumnIndex("vipUser")) == 1) {
				newUser.setVipUser(true);
			} else
				newUser.setVipUser(false);
			newUser.setHiCoin(cursor.getInt(cursor.getColumnIndex("hiCoin")));
			newUser.setUserAge(cursor.getInt(cursor.getColumnIndex("userAge")));

			if (cursor.getString(cursor.getColumnIndex("gender"))
					.equals("MALE")) {
				newUser.setGender(GENDER.MALE);
			} else
				newUser.setGender(GENDER.FEMALE);

			newUser.setPs(cursor.getString(cursor.getColumnIndex("ps")));

			String friendSate = cursor.getString(cursor
					.getColumnIndex("friendState"));
			if (friendSate.equals("NOTRADE"))
				newUser.setFriendState(FriendState.NOTRADE);
			else if (friendSate.equals("TRADING"))
				newUser.setFriendState(FriendState.TRADING);
			else if (friendSate.equals("SUCCESS"))
				newUser.setFriendState(FriendState.SUCCESS);
			else
				newUser.setFriendState(FriendState.FAIL);

			if (cursor.getString(cursor.getColumnIndex("helpRel")).equals(
					"HELP_ME")) {
				newUser.setHelpRel(HelpRelation.HELP_ME);
			} else
				newUser.setHelpRel(HelpRelation.ME_HELP);

			myList.add(newUser);
		} while (cursor.moveToNext());
		return myList;
	}

}
