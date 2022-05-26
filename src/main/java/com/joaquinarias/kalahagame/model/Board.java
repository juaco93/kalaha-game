package com.joaquinarias.kalahagame.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private List<Pit> pits;
	
	

	public Board() {
		super();
		this.pits = new ArrayList<Pit>();	}

	public List<Pit> getPits() {
		return pits;
	}

	public void addPits(List<Pit> pits) {
		for(Pit pit : pits) {
			this.pits.add(pit);
		}
	}
	
	public void addPit(Pit pit) {
		this.pits.add(pit);
	}

	
}
