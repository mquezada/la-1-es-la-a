package cl.la1eslaa.model.beans;

import android.content.Context;

public class Question {

	private long id;
	
	private long subject;
	private String content;
	private String[] alternatives;
	private int correct;
	private int difficulty;
	

	public Question(long id, String content, String[] alternatives, int correct, int difficulty, long subject) {
		super();
		this.id = id;
		this.content = content;
		this.subject = subject;
		this.alternatives = alternatives;
		this.correct = correct;
		this.difficulty = difficulty;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Question() {
		super();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(String[] alternatives) {
		this.alternatives = alternatives;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public long getSubject() {
		return subject;
	}

	public void setSubject(long subject) {
		this.subject = subject;
	}
		
	
	
	
	
}
