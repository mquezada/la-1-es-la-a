package cl.la1eslaa.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CountDownGameController extends GameController {

	static private final int TOTAL_QUESTIONS = 0;
	static public final int TIME_LIMIT = 90000;
	
	public CountDownGameController(SQLiteDatabase db, Context ctx) {
		super(db, ctx, TOTAL_QUESTIONS);		
	}

	@Override
	public boolean canSkip() {
		return false;
	}

	@Override
	public boolean isGameOver() {
		return false;
	}

}
