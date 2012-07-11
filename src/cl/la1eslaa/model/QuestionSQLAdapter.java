package cl.la1eslaa.model;

import java.util.Scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cl.la1eslaa.activities.R;

public class QuestionSQLAdapter {

	public static final String TABLENAME = "question";
	
	public static final String ROW_ID = "_id";
	public static final String ID_SUBJECT = "id_subject";
	public static final String ID_CORRECT_ANS = "id_correct_ans";
	public static final String CONTENT = "content";
	public static final String DIFFICULTY = "difficulty";
		
	public final static String CREATE_STATEMENT = 
			"create table " + TABLENAME + " " +
			"("+ROW_ID+" integer primary key autoincrement, " +
			ID_SUBJECT +" integer not null, " +
			ID_CORRECT_ANS + " integer, " +
			CONTENT + " text not null," +
			DIFFICULTY + " integer not null);";
	
	private Context ctx;
	
	public QuestionSQLAdapter(Context ctx) {		
		this.ctx = ctx;
	}
	
	public Cursor getQuestion(SQLiteDatabase db, long question_id) {
		return db.query(
				TABLENAME, 
				new String[] {ROW_ID, ID_SUBJECT, ID_CORRECT_ANS, CONTENT, DIFFICULTY}, 
				ROW_ID + " = " + question_id, 
				null, 
				null, 
				null, 
				null);
	}
	
	// <= 0 para cualquiera
	public Cursor getQuestions(SQLiteDatabase db, int subject, int difficulty, int quantity) {
			
		String Q_ID_SUBJECT = ID_SUBJECT + " = " + subject;
		String Q_DIFFICULTY = DIFFICULTY + " = " + difficulty;
		String Q_QUANTITY = ROW_ID + " ASC LIMIT " + quantity;
		
		if(subject <= 0)
			Q_ID_SUBJECT = "";
		if(difficulty <= 0) 
			Q_DIFFICULTY = "";
		if(quantity <= 0)
			Q_QUANTITY = null;
		
		String AND = " AND ";
		if(Q_DIFFICULTY.equals("") || Q_ID_SUBJECT.equals(""))
			AND = "";
		
		return db.query(
				TABLENAME, 
				new String[] {ROW_ID, ID_SUBJECT, ID_CORRECT_ANS, CONTENT, DIFFICULTY}, 
				Q_DIFFICULTY + AND + Q_ID_SUBJECT, 
				null, 
				null, 
				null, 
				Q_QUANTITY);
	}
	
	public void init(SQLiteDatabase db) {		
		Scanner s = new Scanner(ctx.getResources().openRawResource(R.raw.preguntas));
		AnswerSQLAdapter answerAdapter = new AnswerSQLAdapter(ctx);
		
		
		while(s.hasNext()) {
			String content = s.nextLine();
			String[] alternativas = new String[] {
					s.nextLine(),s.nextLine(),s.nextLine(),s.nextLine(),s.nextLine()
			};
			
			String a = s.nextLine();
			int correct = Integer.parseInt(a);
			
			String sub = s.nextLine();
			int subject = Integer.parseInt(sub);
			
			String dif = s.nextLine();
			int difficulty = Integer.parseInt(dif);
			
			// question
			ContentValues question = new ContentValues();
			question.put(ID_SUBJECT, subject);
			question.put(CONTENT, content);
			question.put(DIFFICULTY, difficulty);
			
			long qid = db.insert(TABLENAME, null, question);
			
			// alternativas
			long idcorrect = -1;
			for(int i = 0; i < 5; i++) {
				long aid = answerAdapter.insertAnswer(db, qid, alternativas[i]);
				if(aid%5+1 == correct)
					idcorrect = aid%5+1;
			}
			
			ContentValues question_with_correct = new ContentValues();
//			question_with_correct.put(ROW_ID, qid);
			question_with_correct.put(ID_CORRECT_ANS, idcorrect);
			db.update(TABLENAME, question_with_correct, ROW_ID + " = " + qid, null);			
		}	
		
	}
		
}
