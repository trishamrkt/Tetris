import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SideBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/******* PANEL CONSTANTS *****/
	private static final Color BG_COLOUR = new Color(55, 60, 66);
	private static final int TILE_SIZE = 30;
	private static final int TILE_X_COORD = 60;
	private static final int TILE_Y_COORD = 140;
	
	// Tetris game - holds info about next piece
	private Game tetris;
	private Graphics2D g2;
	
	
	// Constructor - set tetris game to current game 
	public SideBoard(Game _tetris) {
		this.tetris = _tetris;
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		
		super.paintComponent(g);
		this.setBackground(BG_COLOUR);
		
		
		this.drawScore();
		this.drawNext();
		
		// instructions
		g2.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		g2.setColor(Color.WHITE);
		g2.drawString("press 'p' to pause", 40, 325);
	}
	
	// Draws the score
	private void drawScore() {
		int currentScore = tetris.getScore();
		
		g2.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
		g2.setColor(Color.WHITE);
		g2.drawString("score: ", 40, 275);
		
		g2.setFont(new Font("MonoSpaced", Font.BOLD, 35));
		g2.drawString(Integer.toString(currentScore), 95, 280);
		
	}
	
	// Draws the next piece
	private void drawNext() {
		GamePiece next = tetris.getNextPiece();
		int[][] coords = next.coords[0];
		Color col = next.getColor();
		
		for (int i = 0; i < coords.length; i++) {
			for (int j = 0; j < coords[0].length; j++) {
				if (coords[i][j] == 1)
					drawTile(TILE_X_COORD + j*TILE_SIZE, TILE_Y_COORD + i * TILE_SIZE, col);
			}
		}
		
	}
	
	private void drawTile(int x, int y, Color col) {
		g2.setColor(col);
		g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		
		// Draw outline to make it easier for player
		g2.setColor(BG_COLOUR);
		g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
	}
}
