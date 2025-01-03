
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

public class B_Panel extends JPanel implements A_GraphicSystem, A_InputSystem, MouseListener
{
  // ...ok...
  private static final long serialVersionUID = 1L;
  
  
  // UserInput variables
  //
  private boolean newInput = false;
  private int     mousePressedX, mousePressedY, 
                  mouseMovedX, mouseMovedY, mouseButton;
  private char    keyPressed;
	
  // GraphicsSystem variables
  //
  private GraphicsConfiguration graphicsConf = 
    GraphicsEnvironment.getLocalGraphicsEnvironment().
    getDefaultScreenDevice().getDefaultConfiguration();
  private BufferedImage imageBuffer;
  private Graphics      graphics;

  
	
  public B_Panel()
  { 
	this.setSize(1000,800);  
	imageBuffer = graphicsConf.createCompatibleImage(
			        this.getWidth(), this.getHeight());	 
	graphics = imageBuffer.getGraphics();
	
	// initialize Listeners
	this.addMouseListener(this);
  }
  
  public void clear()
  { graphics.setColor(Color.LIGHT_GRAY);
    graphics.fillRect(0, 0, 1000, 800);
  }
  
  public final void draw(A_GameObject dot)
  {
	int x = (int)dot.x-dot.radius;
	int y = (int)dot.y-dot.radius;
	int r = dot.radius*2;
	
	graphics.setColor(dot.color);
	graphics.fillOval(x, y, r, r);
	graphics.setColor(Color.DARK_GRAY);
	graphics.drawOval(x,y,r,r);
  }
  
  
  public void redraw()
  { this.getGraphics().drawImage(imageBuffer, 0, 0, this);
  }
  
  
  public void mousePressed(MouseEvent evt)
  {
	// an input Event occurs
	newInput = true;
	
    mousePressedX = evt.getX();
    mousePressedY = evt.getY();
    mouseButton   = evt.getButton();  
  }  
  
  
  public A_UserInput getUserInput()
  { 
    if(!newInput) return null;
    
    newInput = false;
    return new A_UserInput(mousePressedX,mousePressedY, 
	  	                   mouseMovedX,mouseMovedY,mouseButton,keyPressed);
  }
  
  
  // direct the Avatar
  public void command(A_GameObject av, A_UserInput input)
  {
    Gam03_Avatar avatar = (Gam03_Avatar)av;
    avatar.setDestination(input.mousePressedX, input.mousePressedY);    
  }

  
  public void mouseEntered(MouseEvent evt){}
  public void mouseExited(MouseEvent evt){}
  public void mouseClicked(MouseEvent evt){}
  public void mouseReleased(MouseEvent evt){}
}
