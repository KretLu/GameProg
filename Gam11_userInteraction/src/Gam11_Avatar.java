
// (c) Thorsten Hasbargen


import java.awt.Color;

public class Gam11_Avatar extends A_GameObject
{
  private static final B_Shape SHAPE = new B_Shape(25,new Color(96,96,255)); 
  
  public Gam11_Avatar(double x, double y) 
  { super(x,y,0,0,SHAPE);
  }
  
  public void move(double diffSeconds)
  {
	// else move Avatar one step
	super.move(diffSeconds);
  }
  
  
  public int type() { return A_Const.TYPE_AVATAR; }
}
