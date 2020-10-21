package tictactoe.controller;

import tictactoe.model.Game.SIDE;

public interface Controller {

	public void boardClick(int x, int y);
	
	public void startButtonPressed(SIDE side);
}
