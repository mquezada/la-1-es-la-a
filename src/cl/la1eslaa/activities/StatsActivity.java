package cl.la1eslaa.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import cl.la1eslaa.controller.StatsManager;
import cl.la1eslaa.model.AnsweredQuestionsSQLAdapter;
import cl.la1eslaa.model.La1EsLaADBHelper;
import cl.la1eslaa.model.beans.Stat;

public class StatsActivity extends Activity {

	private ProgressBar[] bars;
	private TextView[] names;
	private TextView[] percentages;

	private int total;
	
	private Typeface tf;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        tf = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf");
        SQLiteDatabase db = new La1EsLaADBHelper(this).getReadableDatabase();
        StatsManager statsManager = new StatsManager(db, new AnsweredQuestionsSQLAdapter(this));        
        
        Stat[] stats = statsManager.getStats();
        
        this.total = Stat.SUBJECTS_COUNT;
        initVars();
        fillBars(stats);
        db.close();
    }
    
    private void fillBars(Stat[] stats) {
    	int percentage;
    	
    	
		names[0].setText(R.string.subject1);
		names[1].setText(R.string.subject2);
		names[2].setText(R.string.subject3);
		names[3].setText(R.string.subject4);
		names[4].setText(R.string.subject5);
		names[5].setText(R.string.subject6);
		names[6].setText(R.string.subject7);

    	
		for(int i=0; i<this.total; i++){
			percentage = calcProgress(stats[i]);
			bars[i].setMax(100);
			bars[i].setProgress(percentage);
			names[i].setTypeface(tf);
			percentages[i].setText(percentage+"%");
			percentages[i].setTypeface(tf);
		}
		
	}

	private int calcProgress(Stat stat) {
		int total = stat.getTotal();
		if(total!=0){
			return (int) (1.0*stat.getCorrects()/stat.getTotal()*100);
		}
		return 0;
	}

	public void initVars() {
    	bars = new ProgressBar[total];
    	names = new TextView[total];
    	percentages = new TextView[total];
    	
    	bars[0] = (ProgressBar) this.findViewById(R.id.progressBar1);
    	bars[1] = (ProgressBar) this.findViewById(R.id.progressBar2);
    	bars[2] = (ProgressBar) this.findViewById(R.id.progressBar3);
    	bars[3] = (ProgressBar) this.findViewById(R.id.progressBar4);
    	bars[4] = (ProgressBar) this.findViewById(R.id.progressBar5);
    	bars[5] = (ProgressBar) this.findViewById(R.id.progressBar6);
    	bars[6] = (ProgressBar) this.findViewById(R.id.progressBar7);
    	
    	names[0] = (TextView) this.findViewById(R.id.textView1);
    	names[1] = (TextView) this.findViewById(R.id.textView3);
    	names[2] = (TextView) this.findViewById(R.id.textView5);
    	names[3] = (TextView) this.findViewById(R.id.textView7);
    	names[4] = (TextView) this.findViewById(R.id.textView9);
    	names[5] = (TextView) this.findViewById(R.id.textView11);
    	names[6] = (TextView) this.findViewById(R.id.textView13);
    	
    	percentages[0] = (TextView) this.findViewById(R.id.textView2);
    	percentages[1] = (TextView) this.findViewById(R.id.textView4);
    	percentages[2] = (TextView) this.findViewById(R.id.textView6);
    	percentages[3] = (TextView) this.findViewById(R.id.textView8);
    	percentages[4] = (TextView) this.findViewById(R.id.textView10);
    	percentages[5] = (TextView) this.findViewById(R.id.textView12);
    	percentages[6] = (TextView) this.findViewById(R.id.textView14);
    }
}
