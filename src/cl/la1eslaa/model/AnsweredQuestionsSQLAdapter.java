package cl.la1eslaa.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AnsweredQuestionsSQLAdapter {

	public static final String TABLENAME = "answered_question";
	
	public static final String ROW_ID = "_id";
	public static final String ID_QUESTION = "id_question";
	public static final String ID_ANSWER = "id_answer";
	public static final String ANSWER_TIME = "answer_time";
	
	public final static String CREATE_STATEMENT = 
			"create table " + TABLENAME + " " +
			"("+ROW_ID+" integer primary key autoincrement, " +
			ID_QUESTION + " integer not null, " +
			ID_ANSWER + " integer not null, " +
			ANSWER_TIME + " float not null);";
	
//	private Context ctx;
	
	public AnsweredQuestionsSQLAdapter(Context ctx) {
//		this.ctx = ctx;
	}
	
	public long insertStat(SQLiteDatabase db, long id_question, long id_answer, long time) {
		ContentValues row = new ContentValues();
		row.put(ID_QUESTION, id_question);
		row.put(ID_ANSWER, id_answer);
		row.put(ANSWER_TIME, time);
		
		return db.insert(TABLENAME, null, row);
	}
	
	/* retorna un par de cursores con las buenas y las totales
	 * agrupadas y ordenadas por subject
	 */
	public Cursor[] getStats(SQLiteDatabase db) {
		Cursor[] ans = new Cursor[2];
		String sql = 
			"SELECT " + QuestionSQLAdapter.TABLENAME + "." + QuestionSQLAdapter.ID_SUBJECT + ", COUNT(" + AnsweredQuestionsSQLAdapter.ID_ANSWER + ") " +
			"FROM " +  QuestionSQLAdapter.TABLENAME + ", " +  AnsweredQuestionsSQLAdapter.TABLENAME +  " " + 
			"WHERE " + AnsweredQuestionsSQLAdapter.ID_QUESTION + " = " + QuestionSQLAdapter.TABLENAME + "." +QuestionSQLAdapter.ROW_ID + " AND " + AnsweredQuestionsSQLAdapter.ID_ANSWER + " = " + QuestionSQLAdapter.ID_CORRECT_ANS + " " +			
			"GROUP BY " + QuestionSQLAdapter.ID_SUBJECT + " " +
			"ORDER BY " + QuestionSQLAdapter.ID_SUBJECT +";";
		
		System.out.println("SQL1 : " + sql);
		
		ans[0] = db.rawQuery(sql, null);
		
		sql = 
			"SELECT " + QuestionSQLAdapter.TABLENAME + "." + QuestionSQLAdapter.ID_SUBJECT + ", COUNT(" + AnsweredQuestionsSQLAdapter.ID_ANSWER + ") " +
			"FROM " + QuestionSQLAdapter.TABLENAME + ", " + AnsweredQuestionsSQLAdapter.TABLENAME + " " + 
			"WHERE " + AnsweredQuestionsSQLAdapter.ID_QUESTION + " = " + QuestionSQLAdapter.TABLENAME + "." +QuestionSQLAdapter.ROW_ID + " " +
 			"GROUP BY " + QuestionSQLAdapter.ID_SUBJECT + " " +
			"ORDER BY " + QuestionSQLAdapter.ID_SUBJECT +";";
		
		System.out.println("SQL2 : " + sql);
		ans[1] = db.rawQuery(sql, null);
		
		return ans;
	}
	
	
}
