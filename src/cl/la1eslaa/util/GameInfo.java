package cl.la1eslaa.util;

public class GameInfo {

	private long id_question;
	private int answer_given;
	private boolean skipped;
	private long time;
	
	public GameInfo(long id_question, int answer_given, boolean skipped, long time) {
		super();
		this.id_question = id_question;
		this.answer_given = answer_given;
		this.skipped = skipped;		
		this.time = time;
	}
	
	public long getId_question() {
		return id_question;
	}
	public void setId_question(int id_question) {
		this.id_question = id_question;
	}
	public int getAnswer_given() {
		return answer_given;
	}
	public void setAnswer_given(int answer_given) {
		this.answer_given = answer_given;
	}
	public boolean isSkipped() {
		return skipped;
	}
	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
	}
	
	public void setTime(long init, long end) {
		time = end - init;
	}
	public long getTime() {
		return time;
	}
	
	
}
