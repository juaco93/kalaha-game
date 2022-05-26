package com.joaquinarias.kalahagame.service.impl;

import org.springframework.stereotype.Service;

import com.joaquinarias.kalahagame.model.Game;
import com.joaquinarias.kalahagame.service.KalahaGameService;

@Service
public class KalahaGameServiceImpl implements KalahaGameService{
	
	private Game game;

	@Override
	public Game startGame() {
		game = new Game();
		game.startGame("Player 1", "Player 2");
		
		return game;
	}

	@Override
	public Game makeMove(Integer playerId, Integer pitIndex) throws Exception {
		return game.makeMove(playerId, pitIndex);

	}

}
