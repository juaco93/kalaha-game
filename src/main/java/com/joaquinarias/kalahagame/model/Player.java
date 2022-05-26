package com.joaquinarias.kalahagame.model;

public class Player {
	private Integer id;
	private String name;
	private Integer score;

	public Player(String name, Integer id) {
		super();
		this.id = id;
		this.name = name;
		this.score = 0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
