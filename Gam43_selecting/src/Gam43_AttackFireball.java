
import java.awt.Color;

public class Gam43_AttackFireball extends A_GameObject
{
  // attack knows source and target
  A_GameObject source,target;
	
	
  
  public Gam43_AttackFireball(A_GameObject source_, A_GameObject target_)
  {
	super(source_.x, source_.y,0,400,10,10,Color.blue);
	source=source_; target=target_;
  }
  
  
  public void move(double diffSeconds)
  { 
	// if target is hit, subtract health
	A_GameObjectList collisions = physicsSystem.getCollisions(this);
	for(int i=0; i<collisions.size(); i++)
	{
	  A_GameObject obj = collisions.get(i);
	  int type = obj.type();
	  
	  if(type==A_Const.TYPE_TREE)
	  { obj.health -= 0.8;
		this.isLiving = false;
	  }
	  else if(type==A_Const.TYPE_ZOMBIE)
	  { obj.health -= 0.8;
	    this.isLiving = false;
	  }
	}
	
    // kill target
    if(target.health<0 && target.type()==A_Const.TYPE_ZOMBIE)
    { target.isLiving = false;
      world.selectedGameObject = null;
    }
	  
    // fireball moves to target
    alfa = Math.atan2(target.y-this.y,target.x-this.x);
    super.move(diffSeconds);
  }
  
  
  public int type() { return A_Const.TYPE_ATTACKFIREBALL; }
}
