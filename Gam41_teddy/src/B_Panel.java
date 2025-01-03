
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
	if(dot.type()==A_Const.TYPE_AVATAR || dot.type()==A_Const.TYPE_ZOMBIE)
	{
	  drawTeddy(xPos,yPos,dot.alfa,dot.kapa,graphics,dot.type());
      return;
	}
	
		
	final int PARTS  = 20;
	
	double HEIGHT = dot.shape.radius()*2;
	
	// trees are not THAT high, more like bushes
	if(dot.type()==A_Const.TYPE_TREE)
	{ HEIGHT = dot.shape.radius(); }
	
	// height of grenades decreases with time
	if(dot.type()==A_Const.TYPE_GRENADE)
	{ HEIGHT *= ((Gam41_Grenade)dot).life/A_Const.LIFE_GRENADE;
      HEIGHT = Math.floor(HEIGHT);
	}
	else if(dot.type()==A_Const.TYPE_ZOMBIE)
	{ HEIGHT = HEIGHT*((Gam41_ZombieKI)dot).life;
	  HEIGHT = Math.floor(HEIGHT);
	}

	
    float opacity;
    if(dot.type()==A_Const.TYPE_TREE)      opacity = 0.8f;
    else if(dot.type()==A_Const.TYPE_SHOT) opacity = 0.5f;
    else                                   opacity = 1.0f;
	
	  

    
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
  
  
  // -----------------------------------------------------------------------------------------------
  
  public void drawTeddy(double xScreen, double yScreen, double alfa, double kapa, Graphics gra, int type)
  {
    if(kapa>Math.PI*2) kapa -= Math.PI*2;
    double jota = Math.sin(kapa)*0.8;
	  
    
    double scale = 0.32;
    
    double widthHead  = 50*scale;
    double widthBody  = 60*scale;
    double heightBody = 100*scale;
    double heightLegs = 66*scale;
    double widthLegs  = 32*scale;
    double distLegs   = 22*scale;
    double distArms   = 28*scale;
    double heightArms = 56*scale;
    double alfaArms   = Math.PI/8;
    
    double bodyY     = heightLegs*0.8+heightBody/2.0;
    double headY     = bodyY+heightBody/2.0+widthHead/2.4;
    double armsY1    = bodyY+heightBody*0.35;
    double armsDistX = Math.sin(alfaArms)*heightArms;
    double armsDistY = Math.cos(alfaArms)*heightArms;
        
    
    Color colorBody,colorArm,colorHead,colorLeg;
    
    if(type==A_Const.TYPE_AVATAR)
    {
      colorBody = new Color(140,180,140);
      colorArm  = new Color(100,160,100);
      colorHead = new Color(180,180,140);
      colorLeg  = new Color(140,140,200);
    }
    else
    {
        colorBody = new Color(80,80,80);
        colorArm  = new Color(80,100,80);
        colorHead = new Color(120,80,80);
        colorLeg  = new Color(80,80,110);    
    }
        
    P3D legLeft0 = new P3D(0,-distLegs,0);
    P3D legLeft1 = new P3D(0,-distLegs,0-heightLegs);
    P3D legRigt0 = new P3D(0, distLegs,0);
    P3D legRigt1 = new P3D(0, distLegs,0-heightLegs);

    P3D armLeft0 = new P3D(0,-distArms,0-armsY1);
    P3D armLeft1 = new P3D(0,-(distArms+armsDistX),0-armsY1+armsDistY);
    P3D armRigt0 = new P3D(0, distArms,0-armsY1);
    P3D armRigt1 = new P3D(0,distArms+armsDistX,0-armsY1+armsDistY);
    
    P3D body     = new P3D(0,0,-bodyY);
    P3D head     = new P3D(0,0,-headY);
    
    armLeft1.turnXZ(armLeft0.x, armLeft0.z,  jota);
    armRigt1.turnXZ(armRigt0.x, armRigt0.z, -jota);
    
    legLeft0.turnXZ(legLeft1.x, legLeft1.z, -jota*0.8);
    legRigt0.turnXZ(legRigt1.x, legRigt1.z,  jota*0.8);

    legLeft0.turnXY(0, 0, alfa);
    legLeft1.turnXY(0, 0, alfa);
    legRigt0.turnXY(0, 0, alfa);
    legRigt1.turnXY(0, 0, alfa);
    
    armLeft0.turnXY(0, 0, alfa);
    armLeft1.turnXY(0, 0, alfa);
    armRigt0.turnXY(0, 0, alfa);
    armRigt1.turnXY(0, 0, alfa);

   
    
    // move to right x,y Position on Screen
    armLeft0.move(xScreen,0,yScreen);
    armLeft1.move(xScreen,0,yScreen);
    armRigt0.move(xScreen,0,yScreen);
    armRigt1.move(xScreen,0,yScreen);
    legLeft0.move(xScreen,0,yScreen);
    legLeft1.move(xScreen,0,yScreen);
    legRigt0.move(xScreen,0,yScreen);
    legRigt1.move(xScreen,0,yScreen);
    body.move(xScreen,0,yScreen);
    head.move(xScreen,0,yScreen);


    
    if(legLeft1.y < body.y)
    {
      drawTube(legLeft0, legLeft1, widthLegs, colorLeg, gra);  	
      drawTube(armLeft0, armLeft1, widthLegs, colorArm, gra);
    }
    else
    {
      drawTube(legRigt0, legRigt1, widthLegs, colorLeg, gra);
      drawTube(armRigt0, armRigt1, widthLegs, colorArm, gra);   	
    }
    
    drawOval(body,widthBody,heightBody,colorBody,gra);
    drawOval(head,widthHead,widthHead,colorHead,gra);   
    
    if(legLeft1.y >= body.y)
    {
      drawTube(legLeft0, legLeft1, widthLegs, colorLeg, gra);  	
      drawTube(armLeft0, armLeft1, widthLegs, colorArm, gra);
    }
    else
    {
      drawTube(legRigt0, legRigt1, widthLegs, colorLeg, gra);
      drawTube(armRigt0, armRigt1, widthLegs, colorArm, gra);   	
    }    
  }
  

  
  
  private final void drawTube(P3D p0, P3D p1, 
          double width, Color color, Graphics gra)
  {
	    double alfa    = Math.atan2(p1.z-p0.z,p1.x-p0.x);
	 
    int x0 = (int)p0.x;
    int y0 = (int)p0.z;
    int x1 = (int)p1.x;
    int y1 = (int)p1.z;
    int w2 = (int)(width/2);
    int wi = (int)width;
	    
    int dx = (int)(Math.sin(alfa)*w2);
    int dy = (int)(Math.cos(alfa)*w2);
	    
    final int[] xs = {x0-dx,x0+dx,x1+dx,x1-dx};
    final int[] ys = {y0+dy,y0-dy,y1-dy,y1+dy};
	    
    gra.setColor(color);
    gra.fillPolygon(xs,ys,4);
    if(wi>5)
    {
      gra.fillOval(x0-w2+1, y0-w2+1, wi-2, wi-2);
      gra.fillOval(x1-w2+1, y1-w2+1, wi-2, wi-2);
    }
  }

  
  private final void drawOval(
		              P3D p0,double rx,double ry,Color color,Graphics gra)
  {
	gra.setColor(color);
	gra.fillOval((int)(p0.x-rx/2), (int)(p0.z-ry/2),(int)rx,(int)ry);	  
  }
  
  
  final class P3D
  {
    double xInit,yInit,zInit;
    double x,y,z;
    
    private  P3D(double x_, double y_, double z_)
    { 
      xInit = x = x_;
      yInit = y = y_;
      zInit = z = z_;
    }
    
    public final void reset()
    { x=xInit; y=yInit; z=zInit;
    }
    
    // turn P3D z-axis around point x,y
    private final void turnXY(double xCenter, double yCenter, double alfa)
    {
      double x  = this.x-xCenter;
      double y  = this.y-yCenter;
      
      this.x = Math.cos(alfa)*x-Math.sin(alfa)*y+xCenter;
      this.y = Math.sin(alfa)*x+Math.cos(alfa)*y+yCenter;     
    }

  
    // turn P3D z-axis around point x,y
    private final void turnXZ(double xCenter, double zCenter, double alfa)
    {
      double x  = this.x-xCenter;
      double z  = this.z-zCenter;
      
      this.x = Math.cos(alfa)*x-Math.sin(alfa)*z+xCenter;
      this.z = Math.sin(alfa)*x+Math.cos(alfa)*z+zCenter;      
    }
    
    
    // turn P3D z-axis around point y,z
    private final void turnYZ(double yCenter, double zCenter, double alfa)
    {
      double y  = this.y-yCenter;
      double z  = this.z-zCenter;
    
      this.y = Math.cos(alfa)*y-Math.sin(alfa)*z+yCenter;
      this.z = Math.sin(alfa)*y+Math.cos(alfa)*z+zCenter;
    }
    
    
    private final void move(double xd,double yd,double zd)
    { x+=xd; y+=yd;z+=zd;
    }

    
  }  
}

