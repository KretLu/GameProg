
import java.awt.Color;

public class Gam43_AttackFlash extends A_GameObject
{
  // attack knows source and target
  A_GameObject source,target;
	
	
  
  public Gam43_AttackFlash(A_GameObject source_, A_GameObject target_)
  {
	super(source_.x, source_.y,0,0,10,10,Color.blue);
	source=source_; target=target_;
  }
  
  
  public void move(double diffSeconds)
  { 
	// flash lasts for 1 second
	health -= diffSeconds;
    if(health<0)
    { this.isLiving=false;
    }
    
    // flash drains life from target
    target.health -= diffSeconds*0.55;
    
    // kill target
    if(target.health<0 && target.type()==A_Const.TYPE_ZOMBIE)
    {
      target.isLiving = false;
      this.isLiving   = false;
      world.selectedGameObject = null;
    }
  }
  
  
  public int type() { return A_Const.TYPE_ATTACKFLASH; }
}
