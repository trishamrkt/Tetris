import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Game Board
	private GameBoard board;
	
	// Side panel: displays score and next piece 
	private SideBoard side;
	
	// Hold coordinates of current game piece
	private int currentX;
	private int currentY;
	
	// Timer for game animation
	private Timer timer;
	

	public Game() {
		this.currentX = 50;
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
		board.setPreferredSize(new Dimension(250, 550));
		side.setPreferredSize(new Dimension(200, 550));
		
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
	
	/*
	 *  MUTATORS
	 */
	public void setCurrentX(int _x) {
		this.currentX = _x;
	}
	
	public void setCurrentY(int _y) {
		this.currentY = _y;
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
				board.left();
				break;
				
			case KeyEvent.VK_RIGHT:
				board.right();
				break;
				
			default: break;
				
			}
		}
	}
	
	// for timer
	class Animate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentY != 504)
				board.dropDown();
		}
	}
		
	public static void main(String[] args) {
		// Initialize game
		Game tetris = new Game();
		tetris.setVisible(true);
	}

}
