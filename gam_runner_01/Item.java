
import java.awt.*;

public class Item 
{
  int x,y;
  Color color;
	
  
  public void draw(Graphics gra)
  {
    gra.setColor(color);
    gra.fillOval(x-Const.CIRCLE/2, y-Const.CIRCLE/2, Const.CIRCLE, Const.CIRCLE);  
    gra.setColor(Color.black);
    gra.drawOval(x-Const.CIRCLE/2, y-Const.CIRCLE/2, Const.CIRCLE, Const.CIRCLE);   
  }
  
  public void move() {}
  
}
