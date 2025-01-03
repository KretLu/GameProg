import java.awt.*;
import javax.swing.*;

public class GraphicSystem extends JPanel
{
  private static final Color COLOR_DOT  = new Color(96,96,255);
  private static final int   RADIUS_DOT = 20;
	
  public GraphicSystem()
  {  this.setSize(1000,800);   
  }
  
  public void clear()
  { Graphics gra = this.getGraphics();
    gra.setColor(Color.LIGHT_GRAY);
    gra.fillRect(0, 0, 1000, 800);
  }
  
  public void draw(Dot dot)
  {
	Graphics gra = this.getGraphics();
	gra.setColor(COLOR_DOT);
	gra.fillOval((int)dot.x-RADIUS_DOT, (int)dot.y-RADIUS_DOT,
                   RADIUS_DOT*2, RADIUS_DOT*2);
  }
  
  public void redraw() {} 
}