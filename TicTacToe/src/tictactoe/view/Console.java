package tictactoe.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tictactoe.controller.Controller;
import tictactoe.model.Game;
import tictactoe.model.Game.CELL;
import tictactoe.model.Game.SIDE;

public class Console extends JFrame implements ActionListener {

	private String CLICK_START_MESSAGE = "Click a start button";
	
	private JButton[][] buttons;
	
	private JButton startXButton = new JButton("Start X");
	private JButton startOButton = new JButton("Start O");
	
	private JTextArea status = new JTextArea();
	
	private Controller controller;
	
	public Console(Controller controller) {
		
		this.controller = controller;
		
		buttons = new JButton[3][3];
		
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(3,3));
		
		for(int y=0; y<3; y++) {
			for(int x=0; x<3; x++) {
				buttons[x][y] = new JButton();;
				gamePanel.add(buttons[x][y]);
				buttons[x][y].setActionCommand(x + "," + y);
				buttons[x][y].addActionListener(this);
			}
		}
		
		setSize(200,300);
		setLocation(1500, 50);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(startXButton);
		controlPanel.add(startOButton);
		
		startXButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.startButtonPressed(SIDE.CROSS);
			}
		});

		startOButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.startButtonPressed(SIDE.NAUGHT);
			}
		});

		this.setLayout(new BorderLayout());
		
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(status, BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		String[] xy = actionCommand.split(",");
		int x = Integer.valueOf(xy[0]);
		int y = Integer.valueOf(xy[1]);
		controller.boardClick(x, y);
	}
	
	public void setGameOn(boolean gameOn) {

		for(int y=0; y<3; y++) {
			for(int x=0; x<3; x++) {
				buttons[x][y].setEnabled(gameOn);
			}
		}
		startXButton.setEnabled(!gameOn);
		startOButton.setEnabled(!gameOn);
		
//		if(!gameOn) {
//			status.setText(CLICK_START_MESSAGE);
//		}
		
	}
	
	public void setStatus(String statusMessage) {
		status.setText(statusMessage);
	}
	
	public void refresh(Game game) {
		for(int y=0; y<3; y++) {
			for(int x=0; x<3; x++) {
				switch(game.getCell(x, y)) {
				case CROSS:
					buttons[x][y].setText("X");
					break;
				case NAUGHT:
					buttons[x][y].setText("O");
					break;
				case EMPTY:
				default:
					buttons[x][y].setText("");
					break;
				
				}
			}
		}
	}
	
}
