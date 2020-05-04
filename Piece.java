  
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Piece {
	public void draw(Graphics g);
	public boolean canMove(Direction direction);
	public void move(Direction direction);
	public boolean canRotate();
	public void rotate();
	public Point[] getLocations();
	public Color getColor();
}