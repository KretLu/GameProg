
// (c) Thorsten Hasbargen


import java.awt.Color;

public class Gam11_Avatar extends A_GameObject
{
  
  public Gam11_Avatar(double x, double y) 
  { super(x,y,0,0,25,new Color(96,96,255));
  }
  
  public void move(double diffSeconds)
  {  
	// move Avatar one step
	super.move(diffSeconds);
		
	// get collision list
	//
	A_GameObjectList collide = world.getPhysicsSystem().getCollisions(this);
	
	

	for(int i=0; i<collide.size(); i++)
	{ 
	  // delete colliding obstacles
	  //		
	  // TO RESTRICT to obstacles ONLY!!!!!!!!!!!!
	  //
	  collide.get(i).isLiving=false;
	
	  // bounce back on colliding bricks
	  // TO DO !!!!!!!!!!!!!!!!!!!!
	}

  }
  
  
  public int type() { return A_Const.TYPE_AVATAR; }
}
