import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/******** GAME CONSTANTS ***********/
	private static final int HEIGHT = 550;
	private static final int BOARD_WIDTH = 240;
	private static final int  SIDE_WIDTH = 210;
	
	// Game Board
	private GameBoard board;
	
	// Side panel: displays score and next piece 
	private SideBoard side;
	
	// Hold coordinates of current game piece
	private int currentX;
	private int currentY;
	
	// Timer for game animation
	private Timer timer;
	
	/*
	 *  Info about game piece
	 */
	// Used to get random next piece
	private Random rand = new Random();
	
	// Holds current piece shape
	private GamePiece currentShape;
	
	// Holds current rotation of piece
	private int rotation = 0;
	
	// Height and width - used for checking valid moves
	private int pieceHeight;
	private int pieceWidth;
	
	// If thie piece has been dropped
	private boolean dropped = false;
	
	// Coordinates of piece
	public int[][] coords;
	
	private int currentRow = 0;
	private int currentCol = 4;
	
	

	public Game() {
		this.currentX = 96;
		this.currentY = 0;
		
		// Initial display of game 
		initUI();
		
		// Start game 
		start();
	}
	
	/*
	 * INITIALIZE GAME AND SIDE BOARD APPEARANCE 
	 */
	private void initUI() {
		this.setLayout(new BorderLayout());
		this.setTitle("/trishamrkt - Tetris");
		this.setSize(450, 550);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setFocusable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Creates instance of board and side panels
		board = new GameBoard(this);
		side = new SideBoard(this);
		
		// Set panel sizes 
		board.setPreferredSize(new Dimension(BOARD_WIDTH, HEIGHT));
		side.setPreferredSize(new Dimension(SIDE_WIDTH, HEIGHT));
		
		// Add to game 
		this.add(board, BorderLayout.CENTER);
		this.add(side, BorderLayout.EAST);
		
		// Start timer
		timer = new Timer(500, new Animate());
		timer.start();
		
		// Add keylistener
		this.addKeyListener(new GameInput());
		
	}
	
	/*
	 *  START GAME
	 */
	private void start() {
		newPiece();	
	}

	/*
	 *  MANIPULATE GAME PIECE
	 */
	public void newPiece() {
		this.currentShape = GamePiece.getRandom(rand.nextInt(7));
		this.rotation = 0;
		setPieceWidth();
		setPieceHeight();
		this.currentX = 96;
		this.currentY = 0;
		this.currentRow = 0;
		this.currentCol = 4;
		this.coords = this.currentShape.getCoords()[this.rotation];
		
	}
	
	private void rotate() {
		if (currentShape != GamePiece.OBlock) {
			if (rotation != 3)
				this.rotation++;
			else rotation = 0;
			setPieceWidth();
			setPieceHeight();
			this.coords = this.currentShape.getCoords()[rotation];
		}
	}
	

	/*
	 *  ACCESSORS
	 */
	public int getCurrentX() { return this.currentX; }
	
	public int getCurrentY() { return this.currentY; }
	
	public GamePiece getCurrentPiece() { return this.currentShape; }
	
	public int getRotation() { return this.rotation; }
	
	public int getPieceHeight() { return this.pieceHeight; }
	
	public int getPieceWidth() { return this.pieceWidth; }
	
	public boolean isDropped() { return this.dropped; }
	
	public int getCurrentRow() { return this.currentRow; }
	
	public int getCurrentCol() { return this.currentCol; }
	
	/*
	 *  MUTATORS
	 */
	public void setCurrentX(int _x) { this.currentX = _x; }
	
	public void setCurrentY(int _y) { this.currentY = _y; }
	
	public void setDropped(boolean _dropped) { this.dropped = _dropped; }
	
	public void setCurrentR(int _r) { this.currentRow = _r; }
	
	public void setCurrentC(int _c) { this.currentCol = _c; }
	
	private void setPieceHeight() {
		if (currentShape != GamePiece.OBlock)
			this.pieceHeight = currentShape.getCoords()[rotation].length;
		else this.pieceHeight = 2;
	}
	
	private void setPieceWidth() {
		if (currentShape!= GamePiece.OBlock) 
			this.pieceWidth = currentShape.getCoords()[rotation][0].length;
		else this.pieceWidth = 2;
	}
	

	/*
	 *  KEY AND ACTION LISTENERS
	 */
	// KEYBOARD INPUTS: left, right, down, pause, start
	class GameInput extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				board.horizontalMove(0);
				break;
				
			case KeyEvent.VK_RIGHT:
				board.horizontalMove(1);
				break;
				
			case KeyEvent.VK_UP:
				rotate();
				board.repaint();
				break;
				
			case KeyEvent.VK_DOWN:
				timer.setDelay(50);
				break;
				
			default: break;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_DOWN:
				timer.setDelay(400);
				break;
			default:
				break;
			}
		}
	}
	
	// for timer
	class Animate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!dropped){
				timer.stop();
				board.dropDown();
				timer.start();
			}
			else {
				// create new piece and refresh screen to show animation
				newPiece();
				board.repaint();
				dropped = false;
				timer.restart();
			}
		}
	}
		
	public static void main(String[] args) {
		// Initialize game
		Game tetris = new Game();
		tetris.setVisible(true);
	}

}
