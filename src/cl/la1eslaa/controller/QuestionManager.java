package cl.la1eslaa.controller;

import java.util.ArrayList;
import java.util.Collections;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cl.la1eslaa.model.AnswerSQLAdapter;
import cl.la1eslaa.model.QuestionSQLAdapter;
import cl.la1eslaa.model.beans.Question;
import cl.la1eslaa.util.GameInfo;

public class QuestionManager {

	private SQLiteDatabase db;
	private QuestionSQLAdapter qAdapter;
	private AnswerSQLAdapter aAdapter;
	
	public QuestionManager(SQLiteDatabase db, QuestionSQLAdapter qAdapter,
			AnswerSQLAdapter aAdapter) {
		super();
		this.db = db;
		this.qAdapter = qAdapter;
		this.aAdapter = aAdapter;
	}
	
	public boolean isCorrect(GameInfo c) {
		long question_id = c.getId_question();
		Question q = getQuestion(question_id);
		
		System.out.println("q="+q.getCorrect() + " c=" + c.getAnswer_given());
		if(q.getCorrect() == c.getAnswer_given())
			return true;
		return false;
	}
	
	public Question getQuestion(long question_id) {
		Cursor cursor = qAdapter.getQuestion(db, question_id);		
		cursor.moveToNext();
		
		Question q = new Question();
		q.setId(cursor.getLong(0));
		q.setSubject(cursor.getLong(1));
		q.setCorrect(cursor.getInt(2));
		q.setContent(cursor.getString(3));
		q.setDifficulty(cursor.getInt(4));
		
		cursor.close();
		
		cursor = aAdapter.getAlternatives(db, question_id);
		String[] alts = new String[5];
		
		int i = 0;
		while(cursor.moveToNext()) {
			alts[i++] = cursor.getString(0);				
		}
		
		q.setAlternatives(alts);
		cursor.close();
		
		return q;
	}
	
	public ArrayList<Question> getQuestions(int subject, int difficulty, int quantity) {
		Cursor cursor = qAdapter.getQuestions(db, subject, difficulty, quantity);		
		ArrayList<Question> ans = new ArrayList<Question>();
		int i = 0;
		
		while(cursor.moveToNext()) {
			
			Question q = new Question();
			
			q.setId(cursor.getLong(0));
			q.setSubject(cursor.getLong(1));
			q.setCorrect(cursor.getInt(2));
			q.setContent(cursor.getString(3));
			q.setDifficulty(cursor.getInt(4));
			
			ans.add(q);
		}
		
		cursor.close();
		
		for(Question q : ans) {
			cursor = aAdapter.getAlternatives(db, q.getId());
			String[] alts = new String[5];
			i = 0;
			while(cursor.moveToNext()) {
				alts[i++] = cursor.getString(0);				
			}
			
			q.setAlternatives(alts);
			cursor.close();
		}
		
		Collections.shuffle(ans);
		return ans;
	}
	
}
