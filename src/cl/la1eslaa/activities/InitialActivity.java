package cl.la1eslaa.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

// Actividad Inicial

public class InitialActivity extends Activity implements OnClickListener {
	MediaPlayer back;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);
//        
//        back = MediaPlayer.create(this, R.raw.guile);
//        back.setVolume(0.2f, 0.2f);
//        back.start();
//        
        addListeners();
    }
    
    private void addListeners() {
    	View playButton = findViewById(R.id.play_button);
    	View profileButton = findViewById(R.id.profile_button);
    	View configButton = findViewById(R.id.config_button);
    	playButton.setOnClickListener(this);
    	profileButton.setOnClickListener(this);
    	configButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.play_button:
			startActivity(new Intent(this, ModeSelectActivity.class));
			break;
		case R.id.profile_button:
			startActivity(new Intent(this, ProfileActivity.class));
			break;
		case R.id.config_button:
			startActivity(new Intent(this, RankingActivity.class));
			break;
		}	
		
		finalize();
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		if(back != null) {
//			back.release();
//		}
//		back = MediaPlayer.create(this, R.raw.guile);
//        back.setVolume(0.2f, 0.2f);
//        back.start();
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finalize();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		finalize();
	}
	
	public void finalize() {
//		try {
//			if(back != null) {
//				back.stop();
//				back.release();
//			}
//		} catch(RuntimeException t) {
//			
//		}
	}
}