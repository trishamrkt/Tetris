import java.awt.Color;
import java.util.Random;

public enum GamePiece {
	/* 
	 * I-PIECE
	 */
	IBlock (
			new Color(160, 212, 107),
			4, 
			new int[][][] {
				{
					{ 1, 1, 1, 1}
				},
				{
					{ 1},
					{ 1},
					{ 1},
					{ 1}
				},
				{
					{ 1, 1, 1, 1}
				},
				{
					{ 1},
					{ 1},
					{ 1},
					{ 1}
				}
				
			}),
	
	/*
	 *  	J-SHAPE
	 */
	JBlock (
			new Color(72, 207, 73),
			3, 
			new int[][][] {
				{
					{ 1, 0, 0},
					{ 1, 1, 1}
				},
				{
					{ 1, 1},
					{ 1, 0},
					{ 1, 0}
				},
				{
					{ 1, 1, 1},
					{ 0, 0, 1}
				},
				{
					{ 0, 1},
					{ 0, 1},
					{ 1, 1}
				}
			}),
	
	/*
	 *  L-SHAPE
	 */
	LBlock (
			new Color(79, 193, 233),
			3, 
			new int[][][] {
				{
					{ 0, 0, 1},
					{ 1, 1, 1}
				},
				{
					{ 1, 0},
					{ 1, 0},
					{ 1, 1}
				},
				{ 
					{ 1, 1, 1},
					{ 1, 0, 0}
				},
				{ 
					{ 1, 1},
					{ 0, 1},
					{ 0, 1}
				}
			}),
	
	/*
	 *  O-SHAPE
	 */
	OBlock (
			new Color(178, 156, 233),
			2, 
			new int[][][] {
				{
					{ 1, 1},
					{ 1, 1}
				}
			}),
	
	/*
	 *  S-SHAPE
	 */
	SBlock (
			new Color(252, 110, 81),
			3, 
			new int[][][] {
				{
					{ 0, 1, 1},
					{ 1, 1, 0}
				},
				{
					{ 1, 0},
					{ 1, 1},
					{ 0, 1}
				},
				{
					{ 0, 1, 1},
					{ 1, 1, 0}
				},
				{
					{ 1, 0},
					{ 1, 1},
					{ 0, 1}
				}
			}),
	
	/*
	 *  T-SHAPE
	 */
	TBlock (
			new Color(255, 206, 84),
			3, 
			new int[][][] {
				{
					{ 0, 1, 0},
					{ 1, 1, 1}
				},
				{
					{ 1, 0},
					{ 1, 1},
					{ 1, 0}
				},
				{
					{ 1, 1, 1},
					{ 0, 1, 0}
				},
				{
					{ 0, 1},
					{ 1, 1},
					{ 0, 1}
				}
			}),
	
	/*
	 *  Z-SHAPE
	 */
	ZBlock (
			new Color(255, 142, 202),
			3, 
			new int[][][] {
				{
					{ 1, 1, 0},
					{ 0, 1, 1}
				},
				{
					{ 0, 1},
					{ 1, 1},
					{ 1, 0}
				},
				{
					{ 1, 1, 0},
					{ 0, 1, 1}
				},
				{
					{ 0, 1},
					{ 1, 1},
					{ 1, 0}
				}
			});

	
	private Color colour;
	private int size;
	private final int[][][] coords;
	
	GamePiece(Color _colour, int _size, int[][][] _coords) {
		colour = _colour;
		size = _size;
		coords = _coords;
	}
	
	/*
	 *  GETTER METHODS
	 */
	Color getColor() { return this.colour; }
	int getSize() { return this.size; }
	int[][][] getCoords() { return this.coords; }
	
	private static GamePiece[] pieces = values();
	private static Random rand = new Random();
	
	/*
	 *  FOR GAMEPLAY
	 */
	// Get the next game piece randomly
	public static GamePiece getRandom() {
		// Get random index
		int id = rand.nextInt(7);
		return (pieces[id]);
	}
}
