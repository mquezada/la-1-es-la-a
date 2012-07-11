package cl.la1eslaa.activities;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cl.la1eslaa.controller.UserManager;
import cl.la1eslaa.model.La1EsLaADBHelper;
import cl.la1eslaa.model.beans.User;

public class ProfileActivity extends Activity {
	
	UserManager um;
	SQLiteDatabase db;
	
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		db = new La1EsLaADBHelper(this).getWritableDatabase();
		um = new UserManager(db);
		
		setProfilePhoto();		

		if(um.getUser().getName().equals(User.DEFAULT_NAME))
			Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_LONG).show();
		
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		nameEdit.setText(um.getUser().getName());
		
		nameEdit.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	um.updateUsername(nameEdit.getText().toString());
		          Toast.makeText(ProfileActivity.this, "Nombre modificado", Toast.LENGTH_SHORT).show();
		          return true;
		        }
		        return false;
		    }
		});
		
		((TextView) findViewById(R.id.levelText)).setText("Nivel "+um.getUser().getLevel());
		((TextView) findViewById(R.id.scoreText)).setText(um.getUser().getPoints() + " puntos");
		((ImageView) findViewById(R.id.statsButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileActivity.this, StatsActivity.class));
			}
		});
	}
	
	private ImageView profilePhoto;
	public void setProfilePhoto() {
		profilePhoto = (ImageView) this.findViewById(R.id.imageView1);
		
		String uri = um.getUser().getPhotoUri();
		System.out.println("obteniendo uri:"+uri);
		try {
			if(uri!=null){
				profilePhoto.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.parse(uri)));
			}
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "Presiona la imagen de la camara para tomar una foto de perfil", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		profilePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);				
				startActivityForResult(cameraIntent, 4242);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_CANCELED) 
			return;
				
		if(requestCode == 4242 && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			String uri = MediaStore.Images.Media.insertImage(getContentResolver(), photo,
					"Foto perfil de la1eslaa", "Foto del usuario");
			um.setProfilePhotoURI(uri);
			profilePhoto.setImageBitmap(photo);
			System.out.println("guardando uri:"+uri);			
		}
	}
	
	@Override
	public void onBackPressed() {
		finalize();
	}
	
	public void finalize() {
		db.close();
		finish();
	}
}
