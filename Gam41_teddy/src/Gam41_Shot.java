
import java.awt.Color;

class Gam41_Shot extends A_GameObject
{
  private static final B_Shape SHAPE = new B_Shape(12,new Color(255,128,0));
  
  private double lifeTime = 1.2;

  public Gam41_Shot(double x, double y, double xDest, double yDest)
  {
    super(x,y,Math.atan2(yDest-y, xDest-x),500,SHAPE);
    this.isMoving = true;
  }
  
  public Gam41_Shot(double x, double y, double a, double s, double time)
  { super(x,y,a,s,SHAPE);
    lifeTime = time;
    this.isMoving = true;
  }
  
  
  public void move(double diffSeconds)
  { 
	lifeTime -= diffSeconds;
	if(lifeTime<=0)
	{ this.isLiving=false;
	  return;
	}
	
	
    // handle collisions of the zombie
	A_GameObjectList collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  
	  int type = obj.type();
	  
	  // tree: shot is deleted
	  if(type==A_Const.TYPE_TREE)
	  { this.isLiving=false;
	  }
	  // Zombie: inform Zombie it is hit
	  else if(type==A_Const.TYPE_ZOMBIE && obj.isLiving)
	  { 
	    Gam41_ZombieKI zombie = (Gam41_ZombieKI)obj;
	    zombie.hasBeenShot();
        this.isLiving=false;
	  }
	}  
	
	super.move(diffSeconds);
  }
    
  public final int type() { return A_Const.TYPE_SHOT;}
}
