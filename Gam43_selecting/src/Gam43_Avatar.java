
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam43_Avatar extends A_GameObject
{
  
  public Gam43_Avatar(double x, double y) 
  { super(x,y,0,200,14,52,new Color(96,96,255));
    this.isMoving = false;
  }
  
  public void move(double diffSeconds)
  {
	// move Avatar one step forward
	super.move(diffSeconds);
	
    // calculate all collisions with other Objects 
	A_GameObjectList collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  
	  // if Object is a tree, move back one step
	  if(obj.type()==A_Const.TYPE_TREE) 
	  { this.moveBack(); }
	  
	}
  }
  
  
  public int type() { return A_Const.TYPE_AVATAR; }
}
