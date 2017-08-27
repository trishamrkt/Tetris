import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/******* PANEL CONSTANTS *****/
	private static final int TILE_SIZE = 24;
	private static final int TILE_MOVE = 24;
	private static final Color BOARD_COLOUR = new Color(47, 51, 56);
	private static final int MAX_Y = 504;
	private static final int MAX_X = 240;
	
	// Tetris game
	private Game tetris;
	
	// Graphics container
	Graphics2D g2;
	
	// Game board tiles
	private Color[][] tiles = new Color[22][10];
	
	public GameBoard(Game _tetris) {
		this.tetris = _tetris;
		
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] = null;
			}
		}
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
		g2 = (Graphics2D) g;
		
		// Set background colour
		this.setBackground(BOARD_COLOUR);
		
		drawExisting();
		drawCurrentPiece();
		
	}
	
	/*
	 * Draw game pieces
	 */
	private void drawExisting() {
		for (int row = 0; row < 22; row++) {
			for (int col = 0; col < 10; col++) {
				if (tiles[row][col] != null) {
					drawTile(g2, col * TILE_MOVE, row * TILE_MOVE,tiles[row][col]);
				}
			}
		}
	}
	
	private void drawCurrentPiece() {
		GamePiece current = tetris.getCurrentPiece();
		int rotation = tetris.getRotation();
		
		if (current == GamePiece.OBlock) rotation = 0;
		
		int[][] coords = current.getCoords()[rotation];
		int numRows = coords.length;
		int numCols = coords[0].length;
		
		// loop through the array 
		for (int row = 0; row < numRows; row++) {
			int y = tetris.getCurrentY() + (24 * row);
			
			for (int col = 0; col < numCols; col++) {
				if (coords[row][col] == 1){
					int x = tetris.getCurrentX() + (24 * col);
					drawTile(g2, x, y, current.getColor());
				}
			}
		}
		
	}
	
	private void drawTile(Graphics2D g2, int x, int y, Color col) {
		g2.setColor(col);
		g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
		
		// Draw outline to make it easier for player
		g2.setColor(BOARD_COLOUR);
		g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
	}
	
	/*
	 *  CHECKING METHODS
	 */
	// Check if moving left/right is valid
	// dir = 0: left
	// dir = 1: right
	private boolean validMove(int projected, int dir) {
		boolean valid = true;
		if (projected >= MAX_X || projected < 0)
			valid = false;
		
		else {
			int col = tetris.getCurrentCol();
			int row = tetris.getCurrentRow();
			int width = tetris.getPieceWidth();
			if (dir == 0 && tiles[row][col - 1] != null)
				valid = false;
			else if (dir == 1 && tiles[row][col + width] != null)
				valid = false;
			
		}
		
		return (valid);
	}
	
	// Check if can drop any further
	private boolean canDrop(int projected) {
		boolean valid = true;
		
		if (projected >= MAX_Y) { 
			valid = false;
			tetris.setDropped(true);
			placeOnBoard(tetris.getCurrentPiece().getColor());
		}
		else {
			int height = tetris.getPieceHeight();
			int width = tetris.getPieceWidth();
			int[][] coords = tetris.getCurrentPiece().getCoords()[tetris.getRotation()];
			for (int row = height - 1; row >= 0 && valid; row--) {
				int nextRow = tetris.getCurrentRow() + row + 1;
				
				for (int col = 0; col < width && valid; col++) {
					int currCol = col + tetris.getCurrentCol();
					if (tiles[nextRow][currCol] != null && coords[row][col] == 1) {
						valid = false;
						tetris.setDropped(true);
						placeOnBoard(tetris.getCurrentPiece().getColor());
					}
				}
			}
		}
		
		return valid;
	}
	
	/*
	 *  GAME LOGIC
	 */
	// place piece in array 
	private void placeOnBoard(Color bg) {
		int topR = tetris.getCurrentRow();
		int topC = tetris.getCurrentCol();
		int rotation;
		if (tetris.getCurrentPiece() == GamePiece.OBlock)
			rotation = 0;
		else rotation = tetris.getRotation();
		int[][] coords = tetris.getCurrentPiece().getCoords()[rotation];
		
		for (int row = 0; row < tetris.getPieceHeight(); row++) {
			for (int col = 0; col < tetris.getPieceWidth(); col++) {
				if (coords[row][col] == 1) {
					tiles[topR + row][topC + col] = bg;
				}
			}
		}
	}
	
	
	/*
	 *  ANIMATION METHODS
	 */
	// Move piece down tile by tile 
	public void dropDown() {
		int projected = tetris.getCurrentY() + TILE_MOVE * (tetris.getPieceHeight()-1);
		boolean drop = canDrop(projected);
		
		if (drop) {
			tetris.setCurrentY(tetris.getCurrentY() + TILE_MOVE);
			tetris.setCurrentR(tetris.getCurrentRow() + 1);
			this.repaint();
		}
	}
	
	// Move piece left or right
	// dir = 0 : left
	// dir = 1 : right
	public void horizontalMove(int dir) {
		int projected;
		boolean valid = false;
		int col = tetris.getCurrentCol();
		
		// Set projected x value
		if (dir == 0) projected = tetris.getCurrentX() - TILE_MOVE;
		else projected = tetris.getCurrentX() + TILE_MOVE + TILE_MOVE * (tetris.getPieceWidth() - 1);
		
		// Check if moving is a valid move
		valid = validMove(projected, dir);
		
		//Perform move
		if (valid) {
			if (dir == 1)  {
				tetris.setCurrentX(tetris.getCurrentX() + TILE_MOVE);
				tetris.setCurrentC(col + 1);
			}
			else {
				tetris.setCurrentX(projected);
				tetris.setCurrentC(col - 1);
			}
			this.repaint();
		}
	}
}
