package cl.la1eslaa.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class La1EsLaADBHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "l1ela.db";
	private final static int DATABASE_VERSION = 1;
	
	private Context ctx;
	
	public La1EsLaADBHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.ctx = ctx;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(QuestionSQLAdapter.CREATE_STATEMENT);
		db.execSQL(AnswerSQLAdapter.CREATE_STATEMENT);
		db.execSQL(AnsweredQuestionsSQLAdapter.CREATE_STATEMENT);
		db.execSQL(UserSQLAdapter.CREATE_STATEMENT);
		
		QuestionSQLAdapter q = new QuestionSQLAdapter(this.ctx);
		q.init(db);	
		
		UserSQLAdapter.init(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(La1EsLaADBHelper.class.getName(), "onUpgrade called");	
		db.execSQL("DROP TABLE IF EXISTS " + QuestionSQLAdapter.TABLENAME);
		db.execSQL("DROP TABLE IF EXISTS " + AnswerSQLAdapter.TABLENAME);
		db.execSQL("DROP TABLE IF EXISTS " + AnsweredQuestionsSQLAdapter.TABLENAME);
		
		this.onCreate(db);
	}					
	
}
