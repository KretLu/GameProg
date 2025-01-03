
// (c) Thorsten Hasbargen


abstract class A_GameObject 
{
  // Constants
  private static final double PI2 = Math.PI*2;
	
  // yes, public  :(
  //
  protected double  x,y;
  protected double  alfa  = 0;
  protected double  kapa  = 0;
  protected double  speed = 0;
  protected A_Shape shape;
  
  // if the object is existing, moving etc
  protected boolean isLiving = true;
  protected boolean isMoving = true;

  // destination the object shall move to,
  // old position etc
  private double  destX, destY;
  private boolean hasDestination = false;
  private double  xOld,  yOld;
  
      
  // GameObjects sometimes call physics methods
  protected static A_PhysicsSystem physicsSystem;
  protected static A_World         world;
  
  
  // construct GameObject
  public A_GameObject(double x_, double y_, 
		              double a_, double s_, 
		              A_Shape shape_)
  { 
	x=x_;    y=y_; 
    xOld=x;  yOld=y;
    alfa=a_; speed=s_;
    shape=shape_;
  }
  
  
  // move one step to direction <alfa>
  public void move(double diffSeconds)
  {  
    if(!isMoving) 
    {
      kapa = 0;
      return;	  
    }
	  
    // move if object has a destination
	if(hasDestination)
	{
	  // stop if destination is reached	
	  double diffX = Math.abs(x-destX);
	  double diffY = Math.abs(y-destY);
	  if(diffX<3 && diffY<3)
	  { isMoving = false;
	    return;
	  }
	}    
    
    // remember old position
	xOld=x; yOld=y; 
	  
	// move one step
    x += Math.cos(alfa)*speed*diffSeconds;
    y += Math.sin(alfa)*speed*diffSeconds; 
    
    // add moved time
    kapa += diffSeconds*speed/20;
    if(kapa>PI2) kapa -= PI2;
  }
  
  
  // test and reflect on Window Borders
  protected void reflectOnBorders()
  {
	double rad = this.shape.radius();
	double PI  = Math.PI;
	
    if(x<rad && (alfa>PI/2 && alfa<PI*3/2))
	{ alfa = Math.PI-alfa;
	}
    if(y<rad && alfa>PI)
	{ alfa = PI2-alfa; 
	}
    if(x>A_Const.WORLD_WIDTH-rad)
	{ alfa = Math.PI-alfa;
	}
    if(y>A_Const.WORLD_HEIGHT-rad)
	{ alfa = PI2-alfa;
    }

	
	if(alfa<0)   alfa += PI2;
	if(alfa>PI2) alfa -= PI2;	
  }
  
  
  // set a point in the world as destination
  public final void setDestination(double dx, double dy)
  {
    isMoving       = true;
    hasDestination = true;
    destX          = dx;
    destY          = dy;
    
    alfa = Math.atan2(dy-y, dx-x);
  }  
  
  
  // set the LOCATION of an object as destination
  public void setDestination(A_GameObject obj)
  { setDestination(obj.x, obj.y);	  
  }
  
  
  // move back to the position BEFORE the move Method was called
  protected void moveBack() { x=xOld; y=yOld; }
  
  
  abstract int type();
  static void setPhysicsSystem(A_PhysicsSystem ps){physicsSystem=ps;}
  static void setWorld(A_World w)                 {world=w;}
  
}
