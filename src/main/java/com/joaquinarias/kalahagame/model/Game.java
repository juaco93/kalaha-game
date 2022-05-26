package com.joaquinarias.kalahagame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	// Board will always represent the current state of the board
	private Board board;
	private List<Player> players;
	private Integer nextPlayerIdTurn;
	private boolean isGameOver;

	public Game() {
		super();
		this.board = new Board();
		this.players = new ArrayList<Player>();
		this.nextPlayerIdTurn = 0;
		this.isGameOver = false;
	}

	public Game startGame(String player1Name, String player2Name) {
		// First we add the players to the current game list
		Player player1 = new Player(player1Name,1);
		Player player2 = new Player(player2Name,2);
		this.players.add(player1);
		this.players.add(player2);

		// Second we generate the board to play
		generateBoard(players);

		// Third we randomize who plays first
		Random rand = new Random();
		this.nextPlayerIdTurn = players.get(rand.nextInt(players.size())).getId();

		return this;
	}

	public void generateBoard(List<Player> players) {
		for (Player player : players) {
			generatePitsForPlayer(board, player);
			}
		}

	private void generatePitsForPlayer(Board board, Player player) {
		// For this particular game each player has 6 regular pits...
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit(PitType.REGULAR, player.getId());
			this.board.addPit(pit);
		}

		// ...and a BIG(or little?) pit
		this.board.addPit(new Pit(PitType.BIG, player.getId()));

	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public Game makeMove(Integer playerId, Integer selectedPit) throws Exception {
		
		Player player = players.stream().filter(p -> p.getId().equals(playerId)).findFirst().orElse(null);
		
		// 1st the player selects the pit (we could scan user input)
		if (!playerId.equals(nextPlayerIdTurn))
			throw new Exception("It's not the turn of the selected player");

		// Check if the move is valid
		checkIfselectedMoveIsValid(player, selectedPit);

		// Move the stones
		Boolean currentPlayerRepeatsTurn = moveStones(player, selectedPit);
		
		// Update the scores
		updatePlayerScores();

		// Set next turn to the other player
		if(!currentPlayerRepeatsTurn) {
			this.nextPlayerIdTurn = players.stream().filter(p -> !p.getId().equals(player.getId())).findFirst().orElse(null)
									.getId();
		}
		
		// Check if somebody won
		Integer winner = checkIfPlayerWon();
		if(!winner.equals(0)) {
			this.isGameOver = true;
			this.nextPlayerIdTurn = 99;
		}
		
		return this;
	}

	private void checkIfselectedMoveIsValid(Player player, Integer selectedPitIndex) throws Exception {
		Pit selectedPit = board.getPits().get(selectedPitIndex);

		if (!player.getId().equals(selectedPit.getPlayerId()))
			throw new Exception("The player does not own the selected pit");
		if (selectedPit.getStones() < 1)
			throw new Exception("The selected pit doesn't have stones");
		if (selectedPit.getType().equals(PitType.BIG))
			throw new Exception("The player cannot select a BIG pit");

	}

	private boolean moveStones(Player player, Integer selectedPitIndex) {
		Integer pitCursor = selectedPitIndex;
		Pit selectedPit = board.getPits().get(pitCursor);
		Boolean playerRepeatsTurn = false;
		
		
		//First we empty the selected pit (but before we grab the stones)
		Integer stonesInPit = selectedPit.getStones();
		board.getPits().get(selectedPitIndex).setStones(0);
		
		//Then we sow stones to the right
		pitCursor = selectedPitIndex + 1;
		
		for(int i=0;i<stonesInPit;i++) {
			if(!(pitCursor < board.getPits().size())) {
				// If we looped trough all the board we restart the cursor at the beggining
				pitCursor -= 14;
			}
			
			// We store data from the pit we're in for checks
			Pit currentPit = board.getPits().get(pitCursor);
			

			// We check if we're not on the opponents big pit
			if(!currentPit.getPlayerId().equals(player.getId()) && currentPit.getType().equals(PitType.BIG)) {
				pitCursor++;
				break;
			}

			// For the last stone we check where it landed
			if( i ==(stonesInPit-1) ) {
				if(currentPit.getStones()==0 && currentPit.getType().equals(PitType.REGULAR) && currentPit.getPlayerId().equals(player.getId())) {
					captureOpponentStones(player, pitCursor);
				}
				// If last stone lands in BIG pit then player gets another turn
				if(currentPit.getType().equals(PitType.BIG)) {
					playerRepeatsTurn = true;
				}
			}
			
			Integer previousStones = board.getPits().get(pitCursor).getStones();
			board.getPits().get(pitCursor).setStones(previousStones + 1);
			pitCursor++;
		}
		
		return playerRepeatsTurn;
		
	}
	
	private void captureOpponentStones(Player player,Integer pitIndex) {
		Pit ownPit = board.getPits().get(pitIndex);
		Pit oppositePit = board.getPits().get(oppositePit(pitIndex));
		Integer ownStones = ownPit.getStones();
		Integer oppositeStones = oppositePit.getStones();
		
		// Player is south
		if(pitIndex < 6) {
			Integer prev = board.getPits().get(getIndexKalahaSouth()).getStones();
			board.getPits().get(getIndexKalahaSouth()).setStones(prev+ownStones+oppositeStones);
		}else {
			Integer prev = board.getPits().get(getIndexKalahaNorth()).getStones();
			board.getPits().get(getIndexKalahaNorth()).setStones(prev+ownStones+oppositeStones);
		}
		
		// Finally we clear the pits
		//Own pit
		board.getPits().get(pitIndex).setStones(0);

		//Opposite pit
		board.getPits().get(oppositePit(pitIndex)).setStones(0);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}


	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	public Integer getIndexKalahaSouth() {
        return board.getPits().size() / 2 - 1;
    }

    public Integer getIndexKalahaNorth() {
        return board.getPits().size() - 1;
    }
    
    private int oppositePit(final int index) {
        return 2 * getIndexKalahaSouth() - index;
    }

	public Integer getNextPlayerIdTurn() {
		return nextPlayerIdTurn;
	}

	public void setNextPlayerIdTurn(Integer nextPlayerIdTurn) {
		this.nextPlayerIdTurn = nextPlayerIdTurn;
	}
    
	private void updatePlayerScores() {
		this.getPlayers().get(0).setScore(board.getPits().get(getIndexKalahaSouth()).getStones());
		this.getPlayers().get(1).setScore(board.getPits().get(getIndexKalahaNorth()).getStones());
	}
	
	private Integer checkIfPlayerWon() {
		List<Pit> pits = board.getPits();
		Integer stonesPlayer1 = 0;
		Integer stonesPlayer2 = 0;
		
		for(int i=0;i<pits.size();i++) {
			//Player 1
			if(i<6) {
				stonesPlayer1 += pits.get(i).getStones();
			}
			
			//Player 2
			if(i>6 && i<pits.size()) {
				stonesPlayer2 += pits.get(i).getStones();
			}
		}
		
		if(stonesPlayer1==0)return 1;
		if(stonesPlayer2==0)return 2;
		
		return 0;
	}
    

}
