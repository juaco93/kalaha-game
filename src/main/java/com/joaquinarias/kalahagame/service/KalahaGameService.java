package com.joaquinarias.kalahagame.service;

import com.joaquinarias.kalahagame.model.Game;

public interface KalahaGameService {
	public Game makeMove(Integer playerId, Integer pitIndex) throws Exception;
	public Game startGame();
}
