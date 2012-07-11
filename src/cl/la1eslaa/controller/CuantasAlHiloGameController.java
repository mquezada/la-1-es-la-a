package cl.la1eslaa.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CuantasAlHiloGameController extends GameController {

	static private final boolean CAN_SKIP = false;
	static private final int TOTAL_QUESTIONS = 0;
	
	public CuantasAlHiloGameController(SQLiteDatabase db, Context ctx) {
		super(db, ctx, TOTAL_QUESTIONS);
	}
	
	@Override
	public boolean isGameOver() {
		return !this.wasLastQuestionCorrect();
	}
	
	@Override
	public boolean canSkip() {
		return CAN_SKIP;
	}
	
}
