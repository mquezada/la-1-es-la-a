package cl.la1eslaa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ModeSelectActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_select);
		
		addListeners();
	}
	
	private void addListeners() {
		View classic =  findViewById(R.id.select_classic);
		View hilo = findViewById(R.id.select_hilo);
		View time = findViewById(R.id.select_time);
		
		classic.setOnClickListener(this);
		hilo.setOnClickListener(this);
		time.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.select_classic:
			startActivity(new Intent(this, ClassicGameActivity.class));
			break;
		case R.id.select_hilo:
			startActivity(new Intent(this, CuantasAlHiloGameActivity.class));
			break;
		case R.id.select_time:
			startActivity(new Intent(this, CountDownGameActivity.class));
			break;
		}
		
		this.finish();
	}
	

}
