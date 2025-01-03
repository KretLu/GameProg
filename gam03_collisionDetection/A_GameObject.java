import java.awt.Color;

public abstract class A_GameObject 
{
  // yes, public  :(
  //
  public double x,y;
  public double alfa  = 0;
  public double speed = 0;
  public Color  color = Color.ORANGE;

  // destination the object shall move to
  //
  protected double  destX;
  protected double  destY;
  protected boolean isMoving = false;
  
  protected int radius = 2;
  
  
  // GameObjects sometimes call physics methods
  protected static A_PhysicsSystem physicsSystem;
  protected static A_World         world;
  
  
  // construct GameObject
  public A_GameObject(double x_, double y_, double a_, double s_)
  { x=x_; y=y_; alfa=a_; speed=s_;
  }
  
  // move one step to direction <alfa>
  public void move(double diffSeconds)
  {  
	 x += Math.cos(alfa)*speed*diffSeconds;
	 y += Math.sin(alfa)*speed*diffSeconds;   	  
  }
  
  // test and reflect on Window Borders
  protected void reflectOnBorders()
  {
	int    rad = this.radius;
	double PI  = Math.PI;
	
    if(x<rad && (alfa>PI/2 && alfa<PI*3/2))
	{ alfa = Math.PI-alfa;
	}
    if(y<rad && alfa>PI)
	{ alfa = PI*2-alfa; 
	}
    if(x>A_Constants.WIDTH-rad)
	{ alfa = Math.PI-alfa;
	}
    if(y>A_Constants.HEIGHT-rad)
	{ alfa = PI*2-alfa;
    }

	
	if(alfa<0)    alfa += PI*2;
	if(alfa>PI*2) alfa -= PI*2;	
  }
  
  
  public void setDestination(double dx, double dy)
  {
    isMoving = true;
    destX    = dx;
    destY    = dy;
    
    alfa = Math.atan2(dy-y, dx-x);
  }  
  
  public static void setPhysicsSystem(A_PhysicsSystem ps){physicsSystem=ps;}
  public static void setWorld(A_World w)                 {world=w;}
  
}
