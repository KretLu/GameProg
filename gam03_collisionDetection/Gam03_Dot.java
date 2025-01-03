
import java.awt.Color;
import java.util.ArrayList;

public class Gam03_Dot extends A_GameObject
{
  public Gam03_Dot(double x, double y, double a, double s)
  { super(x,y,a,s);
    radius = 7;
    
    int r = (int)(Math.random()*256);
    int g = (int)(Math.random()*256);
    int b = (int)(Math.random()*256);
    color = new Color(r,g,b);
  }
  
  public void move(double diffSeconds)
  {
	super.move(diffSeconds);
	reflectOnBorders();
	
	// ?? shall colliding Dot_s be removed??
	if(true) return;
	
	ArrayList<A_GameObject> collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  if(obj!=world.gameObjects.get(0))world.gameObjects.remove(obj);	  
	}
    if(collisions.size()>0 && this!=world.gameObjects.get(0)) world.gameObjects.remove(this);
    

  }
}
