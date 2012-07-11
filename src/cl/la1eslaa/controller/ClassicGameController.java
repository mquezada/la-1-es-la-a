package cl.la1eslaa.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cl.la1eslaa.util.GameInfo;
import cl.la1eslaa.util.GameInfo;

public class ClassicGameController extends GameController {

//	static private final int TIME_LIMIT = 0;
	static private final int TOTAL_QUESTIONS = 10;
	static private final boolean CAN_SKIP = false;
	
	public ClassicGameController(SQLiteDatabase db, Context ctx) {
		super(db, ctx, TOTAL_QUESTIONS);		
	}
	
	@Override
	public boolean isGameOver() {
		if(this.answeredQuestions >= TOTAL_QUESTIONS) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canSkip() {
		return CAN_SKIP;
	}

}
