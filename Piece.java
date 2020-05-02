  
import java.awt.Graphics;

public interface Piece {
	public void draw(Graphics g);
	public boolean canMove(Direction direction);
	public void move(Direction direction);
	public boolean canRotate();
	public void rotate();

}