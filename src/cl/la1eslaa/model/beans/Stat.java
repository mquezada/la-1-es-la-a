package cl.la1eslaa.model.beans;

import cl.la1eslaa.activities.R;


public class Stat {
	
	
	public static final int SUBJECTS_COUNT = 7;

	private long id_subject;
	private int corrects;
	private int total;
	
	public Stat(long id_subject) { 
		this.id_subject = id_subject + 1;
		this.corrects = 0;
		this.total = 0;
	}
	
	public Stat(long id_subject, int corrects, int total) {
		super();
		this.id_subject = id_subject;
		this.corrects = corrects;
		this.total = total;
	}
	public long getId_subject() {
		return id_subject;
	}
	public void setId_subject(long id_subject) {
		this.id_subject = id_subject;
	}
	public int getCorrects() {
		return corrects;
	}
	public void setCorrects(int corrects) {
		this.corrects = corrects;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String toString() {
		return "Subject: "  + "; Corrects: " + getCorrects() + "; Total: " + getTotal();
	}
}
