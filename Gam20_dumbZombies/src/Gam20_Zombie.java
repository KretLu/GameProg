
// (c) Thorsten Hasbargen


import java.awt.Color;
import java.util.ArrayList;

class Gam20_Zombie extends A_GameObject
{
  private static final Color COLOR = new Color(160,80,40);	

  public Gam20_Zombie(double x, double y)
  {
    super(x,y,0,70, new B_Shape(15,COLOR));
    this.isMoving = false;
  }
  
  
  public void move(double diffSeconds)
  {
    this.setDestination(world.avatar); 
    
    super.move(diffSeconds);
    
    // handle collisions of the zombie
	ArrayList<A_GameObject> collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  
	  int type = obj.type();
	  
	  // if object is avatar, game over
	  if(type==A_Const.TYPE_AVATAR) { world.gameOver(); }
	  
	  // if object is zombie, step back
	  if(type==A_Const.TYPE_ZOMBIE) { moveBack(); }
	  
	  // if Object is a tree, move back one step
	  if(obj.type()==A_Const.TYPE_TREE) { moveBack(); }
	}    
  }
  
  public int type() { return A_Const.TYPE_ZOMBIE; }
}
