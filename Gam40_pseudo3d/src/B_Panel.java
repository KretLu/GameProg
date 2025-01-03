
// (c) Thorsten Hasbargen


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

class B_Panel extends JPanel implements A_GraphicSystem
{
  // constants
  private static final long serialVersionUID = 1L;
  private static final Font font = new Font("Arial",Font.PLAIN,24);

  
  // InputSystem is an external instance
  private B_InputSystem inputSystem = new B_InputSystem();
  private A_World       world       = null;

	
  // GraphicsSystem variables
  //
  private GraphicsConfiguration graphicsConf = 
    GraphicsEnvironment.getLocalGraphicsEnvironment().
    getDefaultScreenDevice().getDefaultConfiguration();
  private BufferedImage imageBuffer;
  private Graphics      graphics;

  
	
  public B_Panel()
  { 
	this.setSize(A_Const.SCREEN_WIDTH,A_Const.SCREEN_HEIGHT);  
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
    graphics.fillRect(
               0, 0,A_Const.WORLDVIEW_WIDTH,A_Const.WORLDVIEW_HEIGHT);
  }
  
  
  public final void draw_OLD(A_GameObject dot)
  {
    B_Shape shape = (B_Shape)dot.shape;
	  
	int x  = (int)(dot.x-shape.radius() - world.worldViewX);
	int y  = (int)(    A_Const.PERSPECTIVE 
			        * (dot.y-shape.radius() - world.worldViewY ) );
	int dx = (int)(shape.radius()*2);
	int dy = (int)(shape.radius()*2*A_Const.PERSPECTIVE);
	
	graphics.setColor(shape.color);
	graphics.fillOval(x, y, dx, dy);
	graphics.setColor(Color.DARK_GRAY);
	graphics.drawOval(x,y,dx,dy);
  }
  
  
  
  public final void draw(A_GameObject dot)
  {
    // position and geometry
    double pers   = A_Const.PERSPECTIVE;
    B_Shape shape = (B_Shape)dot.shape;    
    double rad  = (int)(shape.radius());
	double xPos = dot.x - world.worldViewX;
	double yPos = pers*(dot.y-world.worldViewY);	
	
	// if not inside WorldView: return
	
		
	final int PARTS  = 20;
	
	double HEIGHT = dot.shape.radius()*2;
	
	// trees are not THAT high, more like bushes
	if(dot.type()==A_Const.TYPE_TREE)
	{ HEIGHT = dot.shape.radius(); }
	
	// height of grenades decreases with time
	if(dot.type()==A_Const.TYPE_GRENADE)
	{ HEIGHT *= ((Gam40_Grenade)dot).life/A_Const.LIFE_GRENADE;
      HEIGHT = Math.floor(HEIGHT);
	}
	else if(dot.type()==A_Const.TYPE_ZOMBIE)
	{ HEIGHT = HEIGHT*((Gam40_ZombieKI)dot).life;
	  HEIGHT = Math.floor(HEIGHT);
	}
	
    float opacity;
    if(dot.type()==A_Const.TYPE_TREE) opacity = 0.8f;
    else                              opacity = 1.0f;
	
	  

    
	// front part of cylinder
    
    int xR   = (int)(xPos+rad);
    int yR   = (int)(yPos);

    for(int part=1; part<PARTS; part++)
	{
      double alfa  = Math.PI*part/PARTS;
            
      int xL   = (int)(xPos+      rad*(Math.cos(alfa)));
      int yL   = (int)(yPos+ pers*rad*(Math.sin(alfa)));
      
      int[] xs = {xR,xL,xL,xR};
      int[] ys = {yR,yL,yL-(int)HEIGHT,yR-(int)HEIGHT};
      
      float factor = (0.3f+0.7f*part/PARTS)/255.0f;
      float red = shape.color.getRed()*factor;
      float gre = shape.color.getGreen()*factor;
      float blu = shape.color.getBlue()*factor;

            
      graphics.setColor(new Color(red,gre,blu,opacity));
      graphics.fillPolygon(xs,ys,4);      
      
      xR = xL;
      yR = yL;
	}
	
	
	// top part of cylinder
    int[] xs = new int[PARTS*2];
    int[] ys = new int[PARTS*2];
	for(int part=0; part<PARTS*2; part++)
	{
      double alfa  = Math.PI*part/PARTS;
            
      xs[part] = (int)(xPos+     rad*Math.cos(alfa));
      ys[part] = (int)(yPos+pers*rad*Math.sin(alfa)-HEIGHT);     
	}
    
    float factor = 0.8f/255.0f;
    float red = shape.color.getRed()*factor;
    float gre = shape.color.getGreen()*factor;
    float blu = shape.color.getBlue()*factor;
             
    graphics.setColor(new Color(red,gre,blu,opacity));
    graphics.fillPolygon(xs,ys,PARTS*2-1); 	
  }

  
  
  public final void draw(A_TextObject text)
  {
    B_Shape shape = (B_Shape)text.shape;
	  
    graphics.setFont(font);
    graphics.setColor(Color.DARK_GRAY);
    graphics.drawString(text.toString(), (int)text.x+1, (int)text.y+1);    
    graphics.setColor(shape.color);
    graphics.drawString(text.toString(), (int)text.x, (int)text.y);
  }
  
  
  public void redraw()
  { this.getGraphics().drawImage(imageBuffer, 0, 0, this);
  }
  
  public final A_InputSystem getInputSystem() { return inputSystem; }
  public final void setWorld(A_World world_)  {this.world = world_;}
}

