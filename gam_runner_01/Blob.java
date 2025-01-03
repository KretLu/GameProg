
import java.awt.*;

public class Blob extends Item
{
  public Blob(int x_, int y_)
  { x = x_;  y =y_;
    color = new Color(200,60,60);
  }
  
  public void move()
  { this.x -= Const.STEP;
  }
}
