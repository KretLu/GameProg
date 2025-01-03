
import java.awt.*;

public class Gam11_Obstacle extends A_GameObject
{
	
  public Gam11_Obstacle(int x, int y)
  {
	super(x,y,0,0,20,Color.orange);
	this.isMoving = false;
  }
  
  public int type() { return A_Const.TYPE_OBSTACLE; }
}
