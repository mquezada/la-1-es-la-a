package cl.la1eslaa.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cl.la1eslaa.model.UserSQLAdapter;
import cl.la1eslaa.model.beans.User;

public class UserManager {

	private SQLiteDatabase db;
	
	public UserManager(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	public User getUser() {
		Cursor c = UserSQLAdapter.getUser(db);
		User u = new User();
		
		while(c.moveToNext()) {
//			System.out.println("User fetched");
			u.setName(c.getString(0));
			u.setServerId(c.getInt(1));
			u.setPoints(c.getInt(2));
			u.setLevel(c.getInt(3));
			u.setPhotoUri(c.getString(4));
			u.setRecord(c.getInt(5));
		}
		
//		System.out.println(u);
		c.close();
		return u;
	}
	
	public void updateUsername(String name) {
		UserSQLAdapter.update(db, name, 0, 0, 0);
	}
	
	public void updateServerId(int id) {
		UserSQLAdapter.update(db, null, id, 0, 0);
	}
	
	public void updateRecord(int rec) {
		UserSQLAdapter.setRecord(db, rec);
	}
	
	public void levelUp() {
		User u = getUser();
		int level = u.getLevel() + 1;
		UserSQLAdapter.update(db, null, 0, 0, level);
	}
	
	public void addPoints(int points) {
		User u = getUser();
		int new_points = u.getPoints() + points;
		
		if(new_points > 0)
			UserSQLAdapter.update(db, null, 0, new_points, 0);
	}
	
	public void setProfilePhotoURI( String uri ) {
		UserSQLAdapter.setProfilePhotoUri(db, uri);
	}
	
}
