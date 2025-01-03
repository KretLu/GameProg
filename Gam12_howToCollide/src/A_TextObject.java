
// (c) Thorsten Hasbargen

import java.awt.*;

abstract class A_TextObject
{
  protected static A_World world;
  
  // yes, public :(
  int     x,y;
  Color   color;
  
  public A_TextObject(int x_, int y_, Color c_)
  { x=x_; y=y_; color=c_;
  }
  
  public abstract String toString();
  
  protected static void setWorld(A_World w){world=w;}
}
