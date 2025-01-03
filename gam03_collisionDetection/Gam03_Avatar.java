import java.awt.Color;
import java.util.ArrayList;

public class Gam03_Avatar extends A_GameObject
{
  private static final Color COLOR_AVATAR  = new Color(96,96,255);
  
  
  public Gam03_Avatar(double x, double y) 
  {super(x,y,0,200);
   color  = COLOR_AVATAR;
   radius = 30;
  }
  
  public void move(double diffSeconds)
  {
	 
    // remove objects which collide with the Avatar 
	ArrayList<A_GameObject> collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  if(obj!=world.gameObjects.get(0)) world.gameObjects.remove(obj);	  
	}
		
	// if no moving shall occur
	if(!isMoving) return;
	 
	// stop if destination is reached
	double diffX = Math.abs(x-destX);
	double diffY = Math.abs(y-destY);
	if(diffX<2 && diffY<2)
	{ isMoving = false;
	  return;
	}

	// else move Avatar one step
	super.move(diffSeconds);
	


  }
  
}
