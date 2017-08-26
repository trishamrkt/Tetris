import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/******* PANEL CONSTANTS *****/
	private static final int BOARD_HEIGHT = 550;
	private static final int BOARD_WIDTH = 250;
	private static final int TILE_SIZE = 25;
	private static final Color BOARD_COLOUR = new Color(47, 51, 56);
	
	// Tetris game
	private Game tetris;
	
	public GameBoard(Game _tetris) {
		this.tetris = _tetris;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGame(g);
	}
	
	/* 
	 *  Draw game board
	 */
	private void drawGame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		// Set background colour
		this.setBackground(BOARD_COLOUR);
		
		drawTile(g2, tetris.getCurrentX(), tetris.getCurrentY(), new Color(72, 207, 173));
		
		
	}
	
	private void drawTile(Graphics2D g2, int x, int y, Color col) {
		g2.setColor(col);
		g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		
		// Draw outline to make it easier for player
		g2.setColor(BOARD_COLOUR);
		g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
	}
	
	
	/*
	 *  ANIMATION METHODS
	 */
	public void dropDown() {
		tetris.setCurrentY(tetris.getCurrentY() + 24);
		this.repaint();
	}
	
	public void left() {
		tetris.setCurrentX(tetris.getCurrentX() - 24);
		this.repaint();
	}
	
	public void right() {
		tetris.setCurrentX(tetris.getCurrentX() + 24);
		this.repaint();
	}
}
