package com.joaquinarias.kalahagame.model;

public class Pit {
	private PitType type;
	private Integer stones;
	private Integer playerId;
	
	public Pit(PitType type,Integer playerId) {
		super();
		this.type = type;
		this.playerId = playerId;
		this.stones = 6;
		if(type.equals(PitType.BIG)) {
			this.stones = 0;
		};
	}

	public PitType getType() {
		return type;
	}

	public void setType(PitType type) {
		this.type = type;
	}

	public Integer getStones() {
		return stones;
	}

	public void setStones(Integer stones) {
		this.stones = stones;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	

}
