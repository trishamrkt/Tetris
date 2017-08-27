import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GameBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/******* PANEL CONSTANTS *****/
	private static final int TILE_SIZE = 24;
	private static final int TILE_MOVE = 24;
	private static final Color BOARD_COLOUR = new Color(47, 51, 56);
	private static final int MAX_Y = 504;
	private static final int MAX_X = 240;
	private static final int NUM_ROWS = 22;
	private static final int NUM_COLS = 10;
	
	// Tetris game
	private Game tetris;
	
	// Graphics container
	Graphics2D g2;
	
	// Game board tiles
	private List<ArrayList<Color>> gameTiles = new ArrayList<>();
	private List<Integer> tilesInRow = new ArrayList<>();
	private ArrayList<Color> emptyRow = new ArrayList<>();
	
	public GameBoard(Game _tetris) {
		this.tetris = _tetris;
		
		for (int row = 0; row < NUM_ROWS; row++) {
			ArrayList<Color> rowTiles = new ArrayList<>();
			
			for (int col = 0; col < NUM_COLS; col++) {
				rowTiles.add(null);
				emptyRow.add(null);
			}
			gameTiles.add(rowTiles);
			tilesInRow.add(0);
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
		boolean go = true;
		for (int row = NUM_ROWS - 1; row >= 0 && go; row--) {
			int numNull = 0;
			for (int col = 0; col < NUM_COLS; col++) {
				Color tileColour = gameTiles.get(row).get(col);
				if (tileColour != null) {
					drawTile(g2, col * TILE_MOVE, row * TILE_MOVE, tileColour);
				}
				else numNull++;
			}
			if (numNull == NUM_COLS) go = false;
		}
	}
	
	private void drawCurrentPiece() {
		GamePiece current = tetris.getCurrentPiece();
		
		// loop through the array 
		for (int row = 0; row < tetris.getPieceHeight(); row++) {
			int y = tetris.getCurrentY() + (TILE_SIZE * row);
			
			for (int col = 0; col < tetris.getPieceWidth(); col++) {
				if (tetris.coords[row][col] == 1){
					int x = tetris.getCurrentX() + (TILE_SIZE * col);
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
			int currCol = tetris.getCurrentCol();
			int currRow = tetris.getCurrentRow();
			int width = tetris.getPieceWidth();
			int height = tetris.getPieceHeight();
			
			for (int i = height - 1; i >= 0 && valid; i--) {
				int row = currRow + i;
				
				if (dir == 0 && gameTiles.get(row).get(currCol - 1) != null && tetris.coords[i][0] == 1)
					valid = false;
				else if (dir == 1 && gameTiles.get(row).get(currCol + width) != null && tetris.coords[i][width - 1] == 1)
					valid = false;	
			}
		}
		
		return (valid);
	}
	
	// Check if can drop any further
	private boolean canDrop(int projected) {
		boolean valid = true;
		
		if (projected >= MAX_Y) { 
			valid = false;
		}
		else {
			int height = tetris.getPieceHeight();
			int width = tetris.getPieceWidth();
			for (int row = height - 1; row >= 0 && valid; row--) {
				int nextRow = tetris.getCurrentRow() + row + 1;
				
				for (int col = 0; col < width && valid; col++) {
					int currCol = col + tetris.getCurrentCol();
					
					Color check = gameTiles.get(nextRow).get(currCol);
					if (check != null && tetris.coords[row][col] == 1) {
						valid = false;
					}
				}
			}
		}
		
		if (!valid && !tetris.isDropped()) {
			tetris.setDropped(true);
			placeOnBoard(tetris.getCurrentPiece().getColor());
			checkLines();
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
		
		for (int row = 0; row < tetris.getPieceHeight(); row++) {
			for (int col = 0; col < tetris.getPieceWidth(); col++) {
				
				if (tetris.coords[row][col] == 1) {
					int old = tilesInRow.get(topR + row);
					gameTiles.get(topR + row).set(topC + col, bg);
					tilesInRow.set(topR + row, old + 1);
				}
			}
		}
	}
	
	private void checkLines() {
		for (int row = 0; row < NUM_ROWS; row++) {
			if (tilesInRow.get(row) == NUM_COLS) {
				gameTiles.remove(row);
				tilesInRow.remove(row);
				
				gameTiles.add(0, emptyRow);
				tilesInRow.add(0, 0);
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
