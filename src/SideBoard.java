import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class SideBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/******* PANEL CONSTANTS *****/
	private static final int BOARD_WIDTH = 200;
	private static final int BOARD_HEIGHT = 550;
	private static final Color BG_COLOUR = new Color(55, 60, 66);
	
	// Tetris game - holds info about next piece
	private Game tetris;
	
	
	// Constructor - set tetris game to current game 
	public SideBoard(Game _tetris) {
		this.tetris = _tetris;
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		System.out.println("hi");
		super.paintComponent(g);
		this.setBackground(BG_COLOUR);
		
		this.drawScore(g);
		this.drawNext(g);
	}
	
	// Draws the score
	private void drawScore(Graphics g) {
		
	}
	
	// Draws the next piece
	private void drawNext(Graphics g) {
		
	}
}
