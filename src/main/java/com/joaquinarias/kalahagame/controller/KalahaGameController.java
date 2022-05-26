package com.joaquinarias.kalahagame.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface KalahaGameController {
	
	@GetMapping("/start")
	public ResponseEntity<Object> startGame();
	
	@PostMapping("/player/{playerId}/move/{pitIndex}")
	public ResponseEntity<Object> makeMove(@PathVariable Integer playerId, @PathVariable Integer pitIndex) throws Exception;
	
	

}
