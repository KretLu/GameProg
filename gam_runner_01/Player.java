
import java.awt.*;

public class Player extends Item
{
  public Player()
  {
    x = 50;
    y = Const.HEIGHT/2;
    color = new Color(60,180,60);
  }
  
  public void down()  { this.y += Const.STEP; }
  public void up()    { this.y -= Const.STEP; }
  public void right() { this.x += Const.STEP; }
}
