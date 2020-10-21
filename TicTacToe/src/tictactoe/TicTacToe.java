package tictactoe;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import tictactoe.controller.Controller;
import tictactoe.model.Coordinate;
import tictactoe.model.Game;
import tictactoe.model.Game.CELL;
import tictactoe.model.Game.SIDE;
import tictactoe.view.Console;

public class TicTacToe implements Controller {
	
	private SIDE playerSide;
	
	private Console console;
	private Game currentGame;
	
	public TicTacToe() {
		
		console = new Console(this);
		console.setVisible(true);
		console.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
			
		});
		
		console.setGameOn(false);
		
	}
	
	public static void main(String[] args) {
		new TicTacToe();
	}

	@Override
	public void boardClick(int x, int y) {
		System.out.println("Board clicked at " + x + ", " + y);
		
		if(currentGame.getCell(x, y) == CELL.EMPTY) {
			if(playerSide == SIDE.CROSS) {
				currentGame.setCell(x, y, CELL.CROSS);
			} else if(playerSide == SIDE.NAUGHT) {
				currentGame.setCell(x, y, CELL.NAUGHT);
			} else {
				System.err.println("Error playerside is not naught or cross");
			}
			console.refresh(currentGame);
			currentGame.switchCurrentTurn();
			nextTurn();
		}
		
	}
	
	private void nextTurn() {

		if(currentGame.getCurrentTurn() == playerSide) {
			if(!checkForFinish()) {
				console.setStatus("Your move");
			}			
		} else {
			if(!checkForFinish()) {
				console.setStatus("My move");
				computerMove();
				console.refresh(currentGame);
			}
		}

	}

	private boolean checkForFinish() {
		
		SIDE winningSide = currentGame.getWinningSide();
		if(winningSide == playerSide) {
			console.setStatus("You won");
			console.setGameOn(false);
			return true;
		} else if(winningSide != null) {
			console.setStatus("I win");
			console.setGameOn(false);
			return true;
		}
		
		// If board is full, its a draw.
		if(currentGame.isBoardFull()) {
			console.setStatus("Draw");
			console.setGameOn(false);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void startButtonPressed(SIDE side) {
		System.out.println("Start button clicked for side " + side);
		playerSide = side;
		console.setGameOn(true);
		
		currentGame = new Game();
		console.refresh(currentGame);
		nextTurn();
	}
	
	private void computerMove() {
		
		SIDE computerSide = null;
		CELL computerCell = null;
		if(playerSide == SIDE.CROSS) {
			computerSide = SIDE.NAUGHT;
			computerCell = CELL.NAUGHT;
		} else {
			computerSide = SIDE.CROSS;
			computerCell = CELL.CROSS;
		}
		
		// If computer can win - do so.
		Coordinate winningCell = currentGame.getWinningMove(computerSide);
		if(winningCell != null) {
			currentGame.setCell(winningCell.x, winningCell.y, computerCell);
		} else {

			// If player can win in next move, block.
			Coordinate losingCell = currentGame.getWinningMove(playerSide);
			if(losingCell != null) {
				currentGame.setCell(losingCell.x, losingCell.y, computerCell);	
			} else {
				computerPickCell(computerCell);
			}
			
		}
		

		currentGame.switchCurrentTurn();
		nextTurn();
	}
	
	private void computerPickCell(CELL computerCell) {
		// Pick a corner.
		if(computerConsiderCell(0,0, computerCell)) return;
		if(computerConsiderCell(0,2, computerCell)) return;
		if(computerConsiderCell(2,0, computerCell)) return;
		if(computerConsiderCell(2,2, computerCell)) return;
		
		// Pick an edge.
		if(computerConsiderCell(0,1, computerCell)) return;
		if(computerConsiderCell(1,0, computerCell)) return;
		if(computerConsiderCell(2,1, computerCell)) return;
		if(computerConsiderCell(1,2, computerCell)) return;
		
		// Go in middle.
		if(computerConsiderCell(1,1, computerCell)) return;
		System.err.println("Error for computer picking cell.");
	}
	
	private boolean computerConsiderCell(int x, int y, CELL computerCell) {
		if(currentGame.getCell(x, y) == CELL.EMPTY) {
			currentGame.setCell(x, y, computerCell);
			return true;
		}
		return false;
	}

}
