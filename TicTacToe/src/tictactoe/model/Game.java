package tictactoe.model;

public class Game {

	public enum CELL { EMPTY, NAUGHT, CROSS };
	
	public enum SIDE { NAUGHT, CROSS };
	
	private CELL[][] board;
	
	private SIDE currentTurn = SIDE.CROSS;
	
	public Game() {
		board = new CELL[3][3];
		for(int x=0; x<3; x++) {
			for(int y=0; y<3; y++) {
				board[x][y] = CELL.EMPTY;
			}
		}
	}
	
	public CELL getCell(int x, int y) {
		return board[x][y];
	}
	
	public boolean setCell(int x, int y, CELL cell) {
		if(board[x][y] == CELL.EMPTY) {
			board[x][y] = cell;
			return true;
		} else {
			return false;
		}
	}

	public SIDE getCurrentTurn() {
		return currentTurn;
	}

	public void switchCurrentTurn() {
		if(currentTurn == SIDE.CROSS) {
			currentTurn = SIDE.NAUGHT;
		} else {
			currentTurn = SIDE.CROSS;
		}
	}
	
	public Coordinate getWinningMove(SIDE side) {
		
		CELL cellValue = null;
		if(side == SIDE.NAUGHT) {
			cellValue = CELL.NAUGHT;
		} else {
			cellValue = CELL.CROSS;
		}
		
		for(int x=0; x<3; x++) {
			for(int y=0; y<3; y++) {
				if(board[x][y] == CELL.EMPTY) {
					boolean win = false;
					board[x][y] = cellValue;
					if(getWinningSide() == side) {
						win = true;
					}
					board[x][y] = CELL.EMPTY;
					if(win) {
						return new Coordinate(x, y);
					}
				}
			}
		}
		return null;
	}
	
	public SIDE getWinningSide() {
		
		if(getWinningRun(0,0, 0,1, 0,2) != null) {
			return getWinningRun(0,0, 0,1, 0,2);
		}
		if(getWinningRun(1,0, 1,1, 1,2) != null) {
			return getWinningRun(1,0, 1,1, 1,2);
		}
		if(getWinningRun(2,0, 2,1, 2,2) != null) {
			return getWinningRun(2,0, 2,1, 2,2);
		}
		if(getWinningRun(0,0, 1,0, 2,0) != null) {
			return getWinningRun(0,0, 1,0, 2,0);
		}
		if(getWinningRun(0,1, 1,1, 2,1) != null) {
			return getWinningRun(0,1, 1,1, 2,1);
		}
		if(getWinningRun(0,2, 1,2, 2,2) != null) {
			return getWinningRun(0,2, 1,2, 2,2);
		}
		if(getWinningRun(0,0, 1,1, 2,2) != null) {
			return getWinningRun(0,0, 1,1, 2,2);
		}
		if(getWinningRun(0,2, 1,1, 2,0) != null) {
			return getWinningRun(0,2, 1,1, 2,0);
		}
		return null;
		
	}
	
	private SIDE getWinningRun(int cellX1, int cellY1, int cellX2, int cellY2, int cellX3, int cellY3) {
		if(getCell(cellX1,cellY1) != CELL.EMPTY) {
			if(getCell(cellX1,cellY1) == getCell(cellX2,cellY2)) {
				if(getCell(cellX1,cellY1) == getCell(cellX3,cellY3)) {
					if(getCell(cellX1,cellY1) == CELL.NAUGHT) {
						return SIDE.NAUGHT;
					}
					if(getCell(cellX1,cellY1) == CELL.CROSS) {
						return SIDE.CROSS;
					}
				}
			}
		}
		return null;
	}
	
	public boolean isBoardFull() {
		for(int x=0; x<3; x++) {
			for(int y=0; y<3; y++) {
				if(board[x][y] == CELL.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
}
