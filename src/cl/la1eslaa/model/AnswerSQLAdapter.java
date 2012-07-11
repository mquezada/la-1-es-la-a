package cl.la1eslaa.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AnswerSQLAdapter {

	public static final String TABLENAME = "answer";
	public static final String ROW_ID = "_id";
	public static final String ID_QUESTION = "id_question";
	public static final String CONTENT = "content";
	
	public final static String CREATE_STATEMENT = 
			"create table " + TABLENAME +
			"(" + ROW_ID + " integer primary key autoincrement, " +
			ID_QUESTION + " integer not null, " +
			CONTENT + " text not null);";
	
//	private SQLiteDatabase db;
//	private La1EsLaADBHelper dbHelper;
//	private Context ctx;
	
	public AnswerSQLAdapter(Context ctx) {
//		this.ctx = ctx;
	}
	
	public long insertAnswer(SQLiteDatabase db, long question_id, String content) {		
		ContentValues values = new ContentValues();
		values.put(ID_QUESTION, question_id);
		values.put(CONTENT, content);
		
		return db.insert(TABLENAME, null, values);
	}
	
	public Cursor getAlternatives(SQLiteDatabase db, long question_id) {
		return db.query(
				TABLENAME, 
				new String[] {CONTENT}, 
				ID_QUESTION + " = " + question_id, 
				null, 
				null,
				null,
				null);
	}
}
