package cl.la1eslaa.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.la1eslaa.model.beans.User;

public class RankingController {

	private UserManager uManager;
	
	static private final String BASE_URL = "http://www.disign.cl/cursor/la1eslaa/";
	static private final String REGISTER_URL = BASE_URL + "register.php?";
	static private final String LEVEL_URL = BASE_URL + "setLevel.php?";
	static private final String RANKING_URL = BASE_URL + "getRanking.php";
	static private final String POINTS_URL = BASE_URL + "setPoints.php?";
	static private final String CHANGE_NAME = BASE_URL + "changeUsername.php?";
	
	public RankingController(SQLiteDatabase db) {
		uManager = new UserManager(db);		
	}
	
	public ArrayList<String[]> init() throws IOException {
		User u = uManager.getUser();
				
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		// registrar si no existe
		if(u.getServerId() == 0) {
			registerUser();
		}
		
		String id_user = "id_user=" + uManager.getUser().getServerId();
		String username = "username=" + uManager.getUser().getName();
		
		// actualizar nombre
		String name_url = CHANGE_NAME + "&" + id_user + "&"  + username;
		getStreamFromURL(name_url);
		
		// post user points		
		String s_points = "points=" + uManager.getUser().getPoints();
		String game_type = "game_type=1";
		
		String post_url = POINTS_URL + id_user + "&" + s_points + "&" + game_type;		
		getStreamFromURL(post_url);
		
		// post user level
		String s_level = "level=" + uManager.getUser().getLevel();
		
		post_url = LEVEL_URL + s_level + "&" + id_user;
		getStreamFromURL(post_url);
		
		// get ranking table
		Scanner s = null;
		s = new Scanner(getStreamFromURL(RANKING_URL));
		
		while(s.hasNext()) {
			String name = s.next();
			int level = s.nextInt();
			int points = s.nextInt();
			
			String[] row = new String[] {
				name, Integer.toString(level), Integer.toString(points)
			};
			result.add(row);
		}
		
		s.close();
		
		return result;		
	}
	
	private InputStream getStreamFromURL(String URL) throws IOException {
		HttpURLConnection conn = null;
		
		URL url = new URL(URL);
		conn = (HttpURLConnection) url.openConnection();			
		return conn.getInputStream();
	}
	
	public boolean registerUser() {
		User u = uManager.getUser();
		String name = u.getName();
		Scanner s;
		HttpURLConnection conn = null;  
		
		try {
			
			URL url = new URL(REGISTER_URL + "username=" + name);
			conn = (HttpURLConnection) url.openConnection();
			s = new Scanner(conn.getInputStream());			
			int id = s.nextInt();
			
			if(id < 0) {
				Log.d("RankingController", "Error del servidor: usuario ya existe?");
				return false;
			} else {
				Log.d("RankingController", "Usuario registrado");
				name = name + id;
				uManager.updateUsername(name);
				uManager.updateServerId(id);			
			}
			return true;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			conn.disconnect();
		}
		
		
	}
	
}