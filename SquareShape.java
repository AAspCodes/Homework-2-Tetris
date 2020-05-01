import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * An L-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 * Sq <br>
 * Sq <br>
 * Sq Sq <br>
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid
 * 
 * @author CSC 143
 */
public class SquareShape extends AbstractPiece implements Piece {


	/**
	 * Creates an L-Shape piece. See class description for actual location of r
	 * and c
	 * 
	 * @param row
	 *            row location for this piece
	 * @param col
	 *            column location for this piece
	 * @param grid
	 *            the grid for this game piece
	 * 
	 */
	public SquareShape(int row, int col, Grid grid) {
		super(row, col, grid);
		
		
		// Create the squares
		square[0] = new Square(grid, row - 1, col, Color.yellow, true);
		square[1] = new Square(grid, row - 1, col + 1, Color.yellow, true);
		square[2] = new Square(grid, row, col, Color.yellow, true);
		square[3] = new Square(grid, row, col + 1, Color.yellow, true);
	}

}