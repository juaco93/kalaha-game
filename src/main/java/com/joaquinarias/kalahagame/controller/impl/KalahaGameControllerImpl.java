package com.joaquinarias.kalahagame.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.joaquinarias.kalahagame.controller.KalahaGameController;
import com.joaquinarias.kalahagame.service.KalahaGameService;

@RestController
public class KalahaGameControllerImpl implements KalahaGameController {
	
	private KalahaGameService kalahaGameService;
	
	@Autowired
	public KalahaGameControllerImpl(KalahaGameService kalahaGameService) {
		this.kalahaGameService = kalahaGameService;
	}

	@Override
	public ResponseEntity<Object> startGame() {
		return new ResponseEntity<>(kalahaGameService.startGame(), HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<Object> makeMove(Integer playerId, Integer pitIndex){
		try {
			return new ResponseEntity<>(
					kalahaGameService.makeMove(playerId, pitIndex)
					, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
