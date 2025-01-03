
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam41_Avatar extends A_GameObject
{
  private static final B_Shape SHAPE = new B_Shape(12,new Color(96,96,255)); 
  
  public Gam41_Avatar(double x, double y) 
  { super(x,y,0,200,SHAPE);
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
	  
	  // pick up Grenades
	  else if(obj.type()==A_Const.TYPE_GRENADE)
	  { ((Gam41_World)world).addGrenade();
	    obj.isLiving=false;
	  }
	}
  }
  
  
  public int type() { return A_Const.TYPE_AVATAR; }
}
