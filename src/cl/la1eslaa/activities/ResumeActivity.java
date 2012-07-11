package cl.la1eslaa.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ResumeActivity extends Activity implements OnClickListener {
	
	TextView scoreText;
	TextView correctText;
	TextView incorrectText;
	TextView skippedText;
	
	MediaPlayer record;
	ImageView backButton;
	ImageView playAgainButton;
	Typeface tf;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resume);
		tf = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Regular.otf");
		getViews();
		setViews();
		
	}
	private void getViews() {
		scoreText = (TextView) this.findViewById(R.id.scoreView);
		scoreText.setTypeface(tf);
		
	//	((TextView) this.findViewById(R.id.asdfText)).setTypeface(tf);
		
		correctText = (TextView) this.findViewById(R.id.correctView);
		incorrectText = (TextView) this.findViewById(R.id.incorrectView);
		skippedText = (TextView) this.findViewById(R.id.skippedView);
		
		backButton = (ImageView) this.findViewById(R.id.backButton);
		playAgainButton = (ImageView) this.findViewById(R.id.playAgainButton);
	}
	
	private void setViews(){
		Bundle extras = getIntent().getExtras();
		scoreText.setText(extras.getString("score")+" Pts");
		correctText.setText(extras.getString("correct"));
		incorrectText.setText(extras.getString("incorrect"));
		skippedText.setText(extras.getString("skipped"));
		backButton.setOnClickListener(this);
		playAgainButton.setOnClickListener(this);
		
		if(extras.getBoolean("isNewRecord")) {
			((ImageView) findViewById(R.id.summary_title)).setImageDrawable(getResources().getDrawable(R.drawable.headerrecord));
			record = MediaPlayer.create(this, R.raw.bananaphone);
			record.start();
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.backButton:
			break;
		case R.id.playAgainButton:
			Bundle extras = getIntent().getExtras();
			int typeGame = extras.getInt("typeGame");
			switch(typeGame){
			case 1: 
				startActivity(new Intent(this, ClassicGameActivity.class));
				break;
			case 2: 
				startActivity(new Intent(this, CuantasAlHiloGameActivity.class));
				break;
			case 3: 
				startActivity(new Intent(this, CountDownGameActivity.class));
				break;
			}
				
		}
		finalize();
	}
	
	@Override
	public void onBackPressed(){
		finalize();
	}
	
	public void finalize() {
		if(record != null) {
			record.stop();
			record.release();
		}
		this.finish();
	}
}
