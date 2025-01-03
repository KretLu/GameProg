
// (c) Thorsten Hasbargen


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

class B_Panel extends JPanel 
       implements A_GraphicSystem
{
  // constants
  private static final long serialVersionUID = 1L;
  private static final Font font = new Font("Arial",Font.PLAIN,30);

  
  // InputSystem is an external instance
  private B_InputSystem inputSystem = new B_InputSystem();

	
  // GraphicsSystem variables
  //
  private GraphicsConfiguration graphicsConf = 
    GraphicsEnvironment.getLocalGraphicsEnvironment().
    getDefaultScreenDevice().getDefaultConfiguration();
  private BufferedImage imageBuffer;
  private Graphics      graphics;

  
	
  public B_Panel()
  { 
	this.setSize(A_Const.WIDTH,A_Const.HEIGHT);  
	imageBuffer = graphicsConf.createCompatibleImage(
			        this.getWidth(), this.getHeight());	 
	graphics = imageBuffer.getGraphics();
	
	// initialize Listeners
	this.addMouseListener(inputSystem);
	this.addMouseMotionListener(inputSystem);
	this.addKeyListener(inputSystem);
  }
  
  public void clear()
  { graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(0, 0, A_Const.WIDTH, A_Const.HEIGHT);
  }
  
  
  public final void draw(A_GameObject object)
  {	  
	if(object.type()==A_Const.TYPE_AVATAR)
	{		  
		int x = (int)(object.x-object.radius);
		int y = (int)(object.y-object.radius);
		int d = (int)(object.radius*2);
		
		graphics.setColor(object.color);
		graphics.fillOval(x, y, d, d);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawOval(x,y,d,d);
		
		int mx = (int)object.x;
		int my = (int)object.y;
		int ex = mx + (int)(object.radius*Math.cos(object.alfa));
		int ey = my + (int)(object.radius*Math.sin(object.alfa));
		
		graphics.setColor(Color.BLACK);
		graphics.drawLine(mx, my, ex, ey);
	}
	else if (object.type()==A_Const.TYPE_BRICK)
	{
	  Gam11_Brick brick = (Gam11_Brick) object; 
	  graphics.setColor(object.color);
	  int x      = (int)object.x;
	  int y      = (int)object.y;
	  int width  = (int)(brick.x2-x);
	  int height = (int)(brick.y2-y);
	  graphics.fillRect(x, y, width, height);
	}
	else if(object.type()==A_Const.TYPE_OBSTACLE)
	{		  
		int x = (int)(object.x-object.radius);
		int y = (int)(object.y-object.radius);
		int d = (int)(object.radius*2);
		
		graphics.setColor(object.color);
		graphics.fillOval(x, y, d, d);
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawOval(x,y,d,d);
	}	
  }
  
  public final void draw(A_TextObject text)
  {	  
    graphics.setFont(font);
    graphics.setColor(Color.DARK_GRAY);
    graphics.drawString(text.toString(), (int)text.x+1, (int)text.y+1);    
    graphics.setColor(text.color);
    graphics.drawString(text.toString(), (int)text.x, (int)text.y);
  }
  
  
  public void redraw()
  { this.getGraphics().drawImage(imageBuffer, 0, 0, this);
  }
  
  public final A_InputSystem getInputSystem() { return inputSystem; }
}

