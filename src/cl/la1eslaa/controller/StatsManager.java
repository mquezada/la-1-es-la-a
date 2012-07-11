package cl.la1eslaa.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cl.la1eslaa.model.AnsweredQuestionsSQLAdapter;
import cl.la1eslaa.model.beans.Stat;
import cl.la1eslaa.util.GameInfo;

public class StatsManager {

	private AnsweredQuestionsSQLAdapter adapter;
	private SQLiteDatabase db;
	
	public StatsManager(SQLiteDatabase db, AnsweredQuestionsSQLAdapter adapter) {
		super();
		this.adapter = adapter;
		this.db = db;
	}
	
	public void saveStat(GameInfo c) {
		long id_question = c.getId_question();
		long id_answer = c.getAnswer_given();
		long time = c.getTime();
		
		adapter.insertStat(db, id_question, id_answer, time);
	}
	
	public Stat[] getStats() {
		Cursor[] cursors = adapter.getStats(db);
		Stat[] stats = new Stat[Stat.SUBJECTS_COUNT];
	
//		stat: id_sub corrects total
//			    1      10 	   20
		
		for(int j = 0; j < Stat.SUBJECTS_COUNT; j++) 
			stats[j] = new Stat(j);
		
//		settea los totales recorriendo el cursor
		while(cursors[1].moveToNext()) {
			int sub_id = (int) cursors[1].getLong(0);
			for(int i = 0; i < Stat.SUBJECTS_COUNT; i++) {
				if(sub_id == stats[i].getId_subject()) {
					stats[i].setTotal(cursors[1].getInt(1));
				}
			}
		}
		
//		settea las correctas
		while(cursors[0].moveToNext()) {
			int sub_id = (int) cursors[0].getLong(0);
			for(int i = 0; i < Stat.SUBJECTS_COUNT; i++) {
				if(sub_id == stats[i].getId_subject()) {
					stats[i].setCorrects(cursors[0].getInt(1));
				}
			}
		}
				
		cursors[1].close();
		cursors[0].close();
		
		return stats;
	}
	
}
