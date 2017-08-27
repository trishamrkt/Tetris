import java.awt.Color;

public enum GamePiece {
	/* 
	 * I-PIECE
	 */
	IBlock (
			new Color(66, 182, 242),
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
			new Color(73, 84, 184),
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
			new Color(52, 212, 124),
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
			new Color(178, 82, 139),
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
			new Color(239, 108, 109),
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
			new Color(255, 239, 85),
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
			new Color(244, 160, 71),
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
	
	/*
	 *  FOR GAMEPLAY
	 */
	// Get the next game piece randomly
	public static GamePiece getRandom(int id) {
		// Get random index
		return (pieces[id]);
	}
}
