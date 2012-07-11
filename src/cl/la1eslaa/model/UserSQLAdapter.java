package cl.la1eslaa.model;

import cl.la1eslaa.model.beans.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserSQLAdapter {

	public static final String TABLENAME = "user";
	public static final String ROW_ID = "_id";
	public static final String NAME = "name";
	public static final String SRV_ID = "srv_id";
	public static final String POINTS = "points";
	public static final String LEVEL = "level";
	public static final String PHOTO_URI = "photo_uri";
	public static final String LAST_RECORD = "last_record";
	
	public final static String CREATE_STATEMENT = 
			"create table " + TABLENAME +
			"(" + ROW_ID + " integer primary key autoincrement, " +
			NAME + " varchar(100) not null, " +
			SRV_ID + " integer not null, " +
			POINTS + " integer not null, " +
			PHOTO_URI + " varchar(255) not null, " +
			LAST_RECORD + " integer not null, " +
			LEVEL + " integer not null);";
	
	public UserSQLAdapter(Context ctx) { }
	
	static public void init(SQLiteDatabase db) {
		
		String name = User.DEFAULT_NAME;
		int srv_id = 0;
		int points = 0;
		int level = 0;
		int record = 0;
		
		ContentValues row = new ContentValues();
		row.put(NAME, name);
		row.put(SRV_ID, srv_id);
		row.put(POINTS, points);
		row.put(LEVEL, level);
		row.put(PHOTO_URI, "");
		row.put(LAST_RECORD, record);
		
		if(db.isOpen())
			System.out.println(db.insert(TABLENAME, null, row));
		else 
			Log.e("UserSQLAdapter", "base de datos cerrada");
		
	}
	
	static public void update(SQLiteDatabase db, String name, int server_id, int points, int level) {
		ContentValues values = new ContentValues();
		
		if(name != null)
			values.put(NAME, name);
		if(server_id != 0) 
			values.put(SRV_ID, server_id);
		if(points != 0)
			values.put(POINTS, points);
		if(level != 0)
			values.put(LEVEL, level);
		
		if(db.isOpen()) {
			db.update(TABLENAME, values, ROW_ID + " = 1", null);
		} else {
			Log.e("UserSQLAdapter", "base de datos cerrada");
		}
	}
	
	static public void setProfilePhotoUri(SQLiteDatabase db, String uri) {
		ContentValues values = new ContentValues();
		
		values.put(PHOTO_URI, uri);
		if(db.isOpen()) {
			db.update(TABLENAME, values, ROW_ID + " = 1", null);
		} else {
			Log.e("UserSQLAdapter", "base de datos cerrada");
		}
	}
	
	static public void setRecord(SQLiteDatabase db, int record) {
		ContentValues values = new ContentValues();
		
		values.put(LAST_RECORD, record);
		if(db.isOpen()) {
			db.update(TABLENAME, values, ROW_ID + " = 1", null);
		} else {
			Log.e("UserSQLAdapter", "base de datos cerrada");
		}
	}
	
	static public Cursor getUser(SQLiteDatabase db) {
		return db.query(TABLENAME, new String[]{NAME, SRV_ID, POINTS, LEVEL, PHOTO_URI, LAST_RECORD}, ROW_ID + " = 1" , null, null, null, null);
	}
	
}
