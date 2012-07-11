package cl.la1eslaa.activities;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import cl.la1eslaa.controller.RankingController;
import cl.la1eslaa.model.La1EsLaADBHelper;

 
public class RankingActivity extends Activity {

	Handler filler;
	ViewSwitcher switcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService( RankingActivity.CONNECTIVITY_SERVICE );
        filler = new Handler();
        switcher = (ViewSwitcher) findViewById(R.id.rankingSwitcher);
        
        if( cm.getActiveNetworkInfo() == null) {
        	AlertDialog msg = new AlertDialog.Builder(this).create();
        	msg.setTitle(R.string.no_conexion_title);
        	msg.setMessage("Necesita estar conectado a Internet para ver el ranking.");
        	msg.setButton("Volver", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					RankingActivity.this.finish();
				}
			});
        	msg.show();
        } else {
        	fetchData();        
        }	        
	}
	
	public void fetchData() {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				SQLiteDatabase db = new La1EsLaADBHelper(RankingActivity.this).getWritableDatabase();
		        RankingController r = new RankingController(db);
		        ArrayList<String[]> data;
		        
				try {
					data = r.init();
				} catch (IOException e) {
					e.printStackTrace();
					filler.post(new Runnable() {
						@Override
						public void run() {
							AlertDialog msg = new AlertDialog.Builder(RankingActivity.this).create();
				        	msg.setTitle(R.string.error_ranking);
				        	msg.setMessage("Hubo un error al traer el ranking desde el servidor.");
				        	msg.setButton("Volver", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									RankingActivity.this.finish();
								}
							});
				        	msg.show();							
						}						
					});
					return;
				}
		        
		        int i = 1;
		        for(String[] row : data) {
		        	final int index = i++;
		        	final String name = row[0];
		        	final String level = row[1];
		        	final String points = row[2];
		        	
		        	filler.post(new Runnable() {
		        		@Override
		        		public void run() { addRow(index, name, level, points); }
		        	});
		        	
		        }
		        
		        filler.post(new Runnable() {
		        	@Override
		        	public void run() { switcher.showNext(); }
		        });
		        db.close();				
			}
			
		};
		
		new Thread(task).start();
	}
	
	private void addRow(int number, String username, String level, String score){
        TableLayout table  = (TableLayout) findViewById(R.id.rankingTable);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        TextView userView = new TextView(this);
        TextView numberView = new TextView(this);
        TextView levelView = new TextView(this);
        TextView scoreView = new TextView(this);
        userView.setText(username);
        numberView.setText(""+number);
        levelView.setText(level);
        scoreView.setText(score);
        userView.setGravity(Gravity.CENTER);
        numberView.setGravity(Gravity.CENTER);
        levelView.setGravity(Gravity.CENTER);
        scoreView.setGravity(Gravity.CENTER);
        tr.addView(numberView);
        tr.addView(userView);
        tr.addView(levelView);
        tr.addView(scoreView);
        table.addView(tr,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}

	
	@Override
	public void onBackPressed() {
		this.finish();
	}
	
	
}
