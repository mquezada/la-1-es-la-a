package cl.la1eslaa.controller;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.la1eslaa.model.AnswerSQLAdapter;
import cl.la1eslaa.model.AnsweredQuestionsSQLAdapter;
import cl.la1eslaa.model.QuestionSQLAdapter;
import cl.la1eslaa.model.beans.Question;
import cl.la1eslaa.model.beans.Stat;
import cl.la1eslaa.util.GameInfo;

public abstract class GameController {

	protected int totalIncorrects;
	protected int skippedQuestions;
	protected int answeredQuestions;
	protected int totalQuestions;	
	protected int score;
	protected boolean lastQuestionCorrect;
	protected boolean levelUp;
	
	protected QuestionManager questionManager;
	protected StatsManager statsManager;
	protected UserManager uManager;
	protected int lastPoints;
	
	protected ArrayList<Question> questions;
	protected int currentIndex;
	
	public GameController(SQLiteDatabase db, Context ctx, int totalQuestions) {
		super();
		
		this.lastQuestionCorrect = true;
		this.totalIncorrects = 0;
		this.totalQuestions = totalQuestions;
		this.answeredQuestions = 0;
		this.score = 0;
		this.skippedQuestions = 0;
		this.levelUp = false;
		
		this.questionManager = new QuestionManager(db, new QuestionSQLAdapter(ctx), new AnswerSQLAdapter(ctx)) ;
		this.statsManager = new StatsManager(db, new AnsweredQuestionsSQLAdapter(ctx));
		
		uManager = new UserManager(db);
		lastPoints = uManager.getUser().getPoints();
	}
	
	public boolean wasLastQuestionCorrect() {
		return lastQuestionCorrect;
	}
	
	public Question nextQuestion(GameInfo c) {
		if (c == null) 
			return null;
		if(!c.isSkipped()) {
			refreshScore(c.getId_question(), questionManager.isCorrect(c));
		} else {
			answeredQuestions++;
			skippedQuestions++;
		}
		
		statsManager.saveStat(c);
		Stat[] stats = statsManager.getStats();
		for(Stat s : stats) 
			System.out.println(s.toString());
		
		if(isGameOver()) {
			return null;
		}
		return nextQuestion();
	}
	
	public int getLastRecord() {
		return uManager.getUser().getRecord();
	}
	
	public void setNewRecord(int rec) {
		uManager.updateRecord(rec);
	}
	
	private void refreshScore(long id_question, boolean correct) {
		answeredQuestions++;
		if(correct){
			lastQuestionCorrect = true;
			Log.d(this.getClass().getSimpleName(), "CORRECT");
			
			score = score + 10;
			uManager.addPoints(10);
		}
		else {
			lastQuestionCorrect = false;
			Log.d(this.getClass().getSimpleName(), "INCORRECT");
			totalIncorrects++;
			
			if(totalIncorrects%4 == 0) {
				uManager.addPoints(-10);
				score = score - 10;
			}
		}
		
		int points = uManager.getUser().getPoints();
		if(points != lastPoints && points % 100 == 0) {
			uManager.levelUp();
			levelUp = true;
			lastPoints = points;
		}
	}

	public boolean levelUp() {
		boolean tmp = levelUp;
		levelUp = false;
		return tmp;
	}
	
	public int getUserLevel() {
		return uManager.getUser().getLevel();
	}
	
	public Question nextQuestion(){
		Question q;
		if(questions == null || questions.size() < 1){
			questions = questionManager.getQuestions(0, 0, 50);
			currentIndex = 0;
		}
		q = questions.get(0);
		questions.remove(0);
		
		return q;
	}
	
	abstract public boolean canSkip();
	abstract public boolean isGameOver();
	
	public int getTotalIncorrects() {
		return totalIncorrects;
	}
	public void setTotalIncorrects(int totalIncorrects) {
		this.totalIncorrects = totalIncorrects;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSkippedQuestions() {
		return skippedQuestions;
	}
	public void setSkippedQuestions(int skippedQuestions) {
		this.skippedQuestions = skippedQuestions;
	}
	
	public int getAnsweredQuestions() {
		return answeredQuestions;
	}
}
