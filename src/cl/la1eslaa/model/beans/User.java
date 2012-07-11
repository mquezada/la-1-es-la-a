package cl.la1eslaa.model.beans;

public class User {

	private String name;
	private int serverId;
	private int points;
	private int level;
	private String photoUri;
	private int record;
	
	static public final String DEFAULT_NAME = "Player";
	
	public User() {
		name = DEFAULT_NAME;
		serverId = 0;
		points = 0;
		level = 0;
		photoUri = "";
		record = 0;
	}
	
	public User(String name, int serverId, int points, int level, String photoUri, int record) {
		super();
		this.name = name;
		this.serverId = serverId;
		this.points = points;
		this.level = level;
		this.photoUri = photoUri;
		this.record = record;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}
	
	public String toString() {
		return "[name="+name+",server_id="+serverId+",points="+points+",level="+level+",photo="+photoUri+",record="+record+"]";
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}
	
	
	
}
