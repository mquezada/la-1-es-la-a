package cl.la1eslaa.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cl.la1eslaa.controller.CuantasAlHiloGameController;
import cl.la1eslaa.controller.GameController;
import cl.la1eslaa.model.La1EsLaADBHelper;
import cl.la1eslaa.model.beans.Question;
import cl.la1eslaa.util.GameInfo;

public class CuantasAlHiloGameActivity extends Activity implements OnClickListener {

	private TextView alt1, alt2, alt3, alt4, alt5;
	private TextView skip;
	private View question;
	
	private TextView questionNumber;
	private TextView pointsText;
	private TextView clockText;
	
	private SQLiteDatabase db;
	private GameController gc;
	private MediaPlayer mp;
	private Vibrator vib;	 
	
	private Timer counter;
	private long startTime;
	private boolean finished;
	
	private int currentIndex;
	private Question currentQuestion;
	// para medir cuanto tiempo se demora en contestar
	private long initTime;
	
	protected PowerManager.WakeLock mWakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

		setContentView(R.layout.game);
		
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		finished = false;
		getViews();
		addListeners();		

		this.db = new La1EsLaADBHelper(this).getWritableDatabase();
		
		gc = new CuantasAlHiloGameController(db, this);		
		currentQuestion = gc.nextQuestion();
		currentIndex = 1;
		
		startTime = System.currentTimeMillis();
		counter = new Timer();
		counter.schedule(new TimerTask() {
			
			@Override
			public void run() {
				CuantasAlHiloGameActivity.this.updateUI();				
			}
		}, 0, 1000);
		
		play();
	}
	
	private void updateUI() {
		this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
		
		@Override
		public void run() {
			long timeElapsed = System.currentTimeMillis() - startTime;
			String time = (String) DateFormat.format("mm:ss", timeElapsed);
			
			CuantasAlHiloGameActivity.this.clockText.setText(time);			
		}
	};
	
	private void play(){
		initTime = System.currentTimeMillis();
		fillWithQuestion(currentQuestion);
	}
	
	private void getViews() {
		alt1 = (TextView) this.findViewById(R.id.alt1Button);
		alt2 = (TextView) this.findViewById(R.id.alt2Button);
		alt3 = (TextView) this.findViewById(R.id.alt3Button);
		alt4 = (TextView) this.findViewById(R.id.alt4Button);
		alt5 = (TextView) this.findViewById(R.id.alt5Button);
		
		questionNumber = (TextView) this.findViewById(R.id.numberQuestion);
		clockText = (TextView) this.findViewById(R.id.clockText);
		pointsText = (TextView) this.findViewById(R.id.numberText);
		
		skip = (TextView) this.findViewById(R.id.skipButton);		
		skip.setVisibility(View.INVISIBLE);
		
		question = this.findViewById(R.id.questionView);
	}
	
	private void addListeners() {
		alt1.setOnClickListener(this);
		alt2.setOnClickListener(this);
		alt3.setOnClickListener(this);
		alt4.setOnClickListener(this);
		alt5.setOnClickListener(this);
		
	}
	
	private void fillWithQuestion(Question q) {
        ((TextView) question).setText(q.getContent());
        
        String[] alts = q.getAlternatives();
        alt1.setText(alts[0]);
        alt2.setText(alts[1]);
        alt3.setText(alts[2]);
        alt4.setText(alts[3]);
        alt5.setText(alts[4]);

	}

	@Override
	public synchronized void onClick(View v) {
		GameInfo info = null;
		long dtime = System.currentTimeMillis() - initTime;
		
		if(currentQuestion != null) {		
			switch(v.getId()) {
				case R.id.alt1Button:
					info = new GameInfo(currentQuestion.getId(),1,false, dtime);
					break;
				case R.id.alt2Button:
					info = new GameInfo(currentQuestion.getId(),2,false, dtime);
					break;
				case R.id.alt3Button:
					info = new GameInfo(currentQuestion.getId(),3,false, dtime);
					break;
				case R.id.alt4Button:
					info = new GameInfo(currentQuestion.getId(),4,false, dtime);
					break;
				case R.id.alt5Button:
					info = new GameInfo(currentQuestion.getId(),5,false, dtime);
					break;
			}
		}
		
		currentQuestion = gc.nextQuestion(info);		
		playStatusSound(gc.wasLastQuestionCorrect());		
		pointsText.setText(""+gc.getScore() + " pts");
		
		if(currentQuestion == null) {
			finalize();
		} else {
			questionNumber.setText("Pregunta " + (++currentIndex));
			play();
		}
		if(gc.levelUp()) {
			Toast.makeText(this, "Nivel obtenido!", Toast.LENGTH_LONG).show();
		}
	}
	
	public void playSound(boolean correct, int resource) {
		if (mp != null) { 
			mp.release();
		}
		if(resource == -1) {
			mp = MediaPlayer.create(this, R.raw.correct);
			mp.start();
		} else {
			mp = MediaPlayer.create(this, resource);
			mp.start();
		}
	}
	
	public void playEndSound() {
		playSound(false, R.raw.buzzer);
	}
	
	public void playStatusSound(boolean correct) {
		playSound(correct, -1);
	}
	
	public void finalize() {			
		if(finished) 
			return;
			
		this.db.close();
		Intent intent = new Intent(this, ResumeActivity.class);
		intent.putExtra("score",""+gc.getScore());
		intent.putExtra("correct",""+(gc.getAnsweredQuestions()-gc.getTotalIncorrects()-gc.getSkippedQuestions()));
		intent.putExtra("incorrect",""+gc.getTotalIncorrects());
		intent.putExtra("skipped",""+gc.getSkippedQuestions());
		intent.putExtra("typeGame",2);
		
		vib.vibrate(400);
		playEndSound();
		startActivity(intent);
		finished = true;
		this.finish();		
	}
	
	@Override
	public void onBackPressed(){
		finalize();
	}
	
	@Override
    public void onDestroy() {
            this.mWakeLock.release();
            super.onDestroy();
    }
	
}
