import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/******** GAME CONSTANTS ***********/
	private static final int HEIGHT = 550;
	private static final int BOARD_WIDTH = 240;
	private static final int  SIDE_WIDTH = 209;
	
	// Game Board
	private GameBoard board;
	
	// Side panel: displays score and next piece 
	private SideBoard side;
	
	// Hold coordinates of current game piece
	private int currentX;
	private int currentY;
	
	// Timer for game animation
	private Timer timer;
	
	// Info about game pieces
	private GamePiece currentShape;
	private GamePiece nextShape;
	private int rotation = 0;
	private int pieceHeight;
	private int pieceWidth;
	private boolean dropped = false;
	

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
		this.currentShape = GamePiece.getRandom();
		setPieceWidth();
		setPieceHeight();
		this.currentX = 96;
		this.currentY = 0;
		
	}
	
	private void rotate() {
		if (currentShape != GamePiece.OBlock) {
			if (rotation != 3)
				this.rotation++;
			else rotation = 0;
			setPieceWidth();
			setPieceHeight();
		}
	}
	

	/*
	 *  ACCESSORS
	 */
	public int getCurrentX() {
		return this.currentX;
	}
	
	public int getCurrentY() {
		return this.currentY;
	}
	
	public GamePiece getCurrentPiece() {
		return this.currentShape;
	}
	
	public int getRotation() {
		return this.rotation;
	}
	
	public int getPieceHeight() {
		return this.pieceHeight;
	}
	
	public int getPieceWidth() {
		return this.pieceWidth;
	}
	
	public boolean isDropped() {
		return this.dropped;
	}
	
	/*
	 *  MUTATORS
	 */
	public void setCurrentX(int _x) {
		this.currentX = _x;
	}
	
	public void setCurrentY(int _y) {
		this.currentY = _y;
	}
	
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
	
	public void setDropped(boolean _dropped) {
		this.dropped = _dropped;
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
				
			default: break;
				
			}
		}
	}
	
	// for timer
	class Animate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!dropped){
				board.dropDown();
			}
			else {
				newPiece();
				board.repaint();
				dropped = false;
				timer.stop();
				timer.start();
			}
		}
	}
		
	public static void main(String[] args) {
		// Initialize game
		Game tetris = new Game();
		tetris.setVisible(true);
	}

}
