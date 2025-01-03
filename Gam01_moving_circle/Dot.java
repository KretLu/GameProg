public class Dot 
{
  // yes, public  :(
  //
  public double x,y;
  
  public Dot(double x_, double y_) 
  {x=x_;  y=y_;
  }
  
  public void move()
  { x += 0.01;
  }
}