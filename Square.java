import java.awt.Color;
import java.awt.Graphics;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author CSC 143
 */
public class Square {
	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square

	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		boolean move = true;
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		switch (direction) {
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;

		// Set boundaries for the pieces to move. 
		case LEFT:
			if (col == 0 || grid.isSet(row, col - 1))
				move = false;
			break;		
		case RIGHT:
			if (col == Grid.WIDTH - 1 || grid.isSet(row, col + 1))
				move = false;
			break;
		}
		return move;
	}
		
	public boolean canRotate(int indexRow, int indexCol) {
		int[] offsets = computeOffsets(indexRow, indexCol);
		int rowOffset = offsets[0];
		int colOffset = offsets[1];
		
		int[] newPositions = computeNewPosition(offsets, indexRow, indexCol);
		int newRow = newPositions[0];
		int newCol = newPositions[1];
		
		
		// check if within bounds
		if (newRow >= grid.HEIGHT || newRow < 0) {
			return false;
		}
		if (newCol >= grid.WIDTH || newCol < 0) {
			return false;
		}
		
		int[] traversialPos = new int[] {row,col};
		
		if (Math.abs(rowOffset) > Math.abs(colOffset)) {
			if (!traverseHorizontal(traversialPos, newRow, newCol)|| !traverseVertical(traversialPos, newRow, newCol)) {
				return false;
			} 

		} else {
			if (!traverseVertical(traversialPos, newRow, newCol) || !traverseHorizontal(traversialPos, newRow, newCol)) {
				return false;
			} 
		}
		
		return true;

	}
	
	private boolean traverseHorizontal(int[] traversialPos, int newRow, int newCol) {
		int travRow = traversialPos[0];
		int travCol = traversialPos[1];
		boolean clear = true;
		
		int startCol, endCol;
		// must start while loop with the smaller of the two numbers....
		if (travCol < newCol) {
			startCol = travCol;
			endCol = newCol;
		} else if (travCol > newCol) {
			startCol = newCol - 1;
			endCol = travCol - 1;
		} else {
			// they're equal and there is no need to traverse
			return clear;
		}
		
		while (startCol < endCol) {
			startCol++;
			if (grid.isSet(travRow, startCol)) {
				clear = false;
				break;
			}
		}
		
		traversialPos[1] = newCol;
		return clear;
		
	}
	
	private boolean traverseVertical(int[] traversialPos, int newRow, int newCol) {
		int travRow = traversialPos[0];
		int travCol = traversialPos[1];
		boolean clear = true;
		
		int startRow, endRow;
		// must start while loop with the smaller of the two numbers....
		if (travRow < newRow) {
			startRow = travRow;
			endRow = newRow;
		} else if (travRow > newRow) {
			startRow = newRow-1;
			endRow = travRow - 1;
		} else {
			// they're equal and there is no need to traverse
			return clear;
		}
		
		while (startRow < endRow) {
			startRow++;
			if (grid.isSet(startRow, travCol)) {
				clear = false;
				break;
			}
		}
		
		traversialPos[0] = newRow;
		return clear;
	}
	
	public void rotate(int indexRow, int indexCol) {
		int[] offsets = computeOffsets(indexRow, indexCol);
		int[] newPositions = computeNewPosition(offsets, indexRow, indexCol);
		row = newPositions[0];
		col = newPositions[1];
	}
	
	
	private int[] computeOffsets(int indexRow, int indexCol) {
		return new int[] {row - indexRow, col - indexCol};
	}
	
	
	private int[] computeNewPosition(int[] offsets, int indexRow, int indexCol) {
		
		int rowOffset = offsets[0];
		int colOffset = offsets[1];
		
		int newCol = indexCol - rowOffset;
		int newRow = indexRow + colOffset;
		return new int[] {newRow, newCol};
	}
	

	
	
	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			switch (direction) {
			case DOWN:
				row = row + 1;
				break;

			// Added code to move piece left and right
			case LEFT:
				col = col - 1;
				break;
			case RIGHT:
				col = col + 1;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
}
