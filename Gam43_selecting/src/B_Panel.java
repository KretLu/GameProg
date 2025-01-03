
// (c) Thorsten Hasbargen


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


class B_Panel extends JPanel implements A_GraphicSystem
{
  // constants
  private static final long  serialVersionUID = 1L;
  private static final Font  font = new Font("Arial",Font.PLAIN,24);
  private static final Color SELECTED   = new Color(255,100,0);
  private static final Color HEALTHBAR  = new Color(240,60,0);
  private static final Color BACKGROUND = new Color(120,160,120);
  private static final Color GREY       = new Color(160,160,160);
  private static final Color FLASH      = new Color(255,255,255);
  private static final Color FLASHB     = new Color(220,220,255);
  private static final Color FLASHY     = new Color(255,220,255);

  
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
  { graphics.setColor(GREY);
    graphics.fillRect(
               0, 0,A_Const.WORLDVIEW_WIDTH,A_Const.WORLDVIEW_HEIGHT);
  }
  
  
 
  public final void draw(A_GameObject obj)
  {
    // position and geometry
    //
    double PERSP   = A_Const.PERSPECTIVE;
    int    rad     = (int)(obj.radius());
    int    radP    = (int)(obj.radius()*A_Const.PERSPECTIVE);
	int    xScreen = (int)(obj.x - world.worldViewX);
	int    yScreen = (int)(PERSP*(obj.y-world.worldViewY));	
	
	// if outside WorldView: return
	//
	if(xScreen<0-rad)                       return;
	if(xScreen>A_Const.SCREEN_WIDTH+rad)    return;
	if(yScreen<0-rad*3)                     return;
	if(yScreen>A_Const.SCREEN_HEIGHT+rad*3) return;
	
	
	// if selected, draw oval around base
	//
	if(obj==world.selectedGameObject)
	{
      graphics.setColor(SELECTED);
      graphics.drawOval(xScreen-rad-3, yScreen-radP-3,rad*2+4, radP*2+4);
      graphics.drawOval(xScreen-rad-4, yScreen-radP-4,rad*2+6, radP*2+6);
	}
	
	// if selected or Avatar, draw health bar
	//
	if(obj==world.selectedGameObject || obj.type()==A_Const.TYPE_AVATAR)
	{
      int widthBar = obj.radius()*2;
		
      int x = xScreen-widthBar/2;
      int y = yScreen-(obj.height()+radP+12);
      
      graphics.setColor(BACKGROUND);
      graphics.fillRect(x, y, widthBar, 4);
      graphics.setColor(HEALTHBAR);
      graphics.fillRect(x, y, (int)(widthBar*obj.health), 4);
	}
	
	
	// Avatar and Zombies are drawn as teddies
	//
	if(obj.type()==A_Const.TYPE_AVATAR || obj.type()==A_Const.TYPE_ZOMBIE)
	{
	  drawTeddy(xScreen,yScreen,obj.alfa,obj.kapa,graphics,obj.type());
      return;
	}
	
	
	// Attacks are also drawn differently
	//
	else if(obj.type()==A_Const.TYPE_ATTACKFLASH)
	{
      Gam43_AttackFlash attack = (Gam43_AttackFlash)obj;
  	  int    xScr0 = (int)(attack.source.x - world.worldViewX);
  	  int    yScr0 = (int)(PERSP*(attack.source.y-world.worldViewY));	     
  	  int    xScr1 = (int)(attack.target.x - world.worldViewX);
  	  int    yScr1 = (int)(PERSP*(attack.target.y-world.worldViewY));	     

  	  yScr0 -= attack.source.height()*3/4;
  	  yScr1 -= attack.target.height()*3/4;
		
	  drawAttackFlash(xScr0,yScr0,xScr1,yScr1);	
	  return;
	}
	else if(obj.type()==A_Const.TYPE_ATTACKFIREBALL)
	{
	  Gam43_AttackFireball attack = (Gam43_AttackFireball)obj;
  	  int    xScr = (int)(attack.x - world.worldViewX);
  	  int    yScr = (int)(PERSP*(attack.y-world.worldViewY));	     
  	  
  	  yScr -= attack.source.height()*3/4;
  	  
	  drawAttackFireball(xScr,yScr);
	  return;
	}

	
		
	final int PARTS  = 20;
	
	double HEIGHT = obj.height();
	
    float opacity;
    if(obj.type()==A_Const.TYPE_TREE)      opacity = 0.8f;
    else if(obj.type()==A_Const.TYPE_SHOT) opacity = 0.5f;
    else                                   opacity = 1.0f;
	
	  

    
	// front part of cylinder
    
    int xR   = (int)(xScreen+rad);
    int yR   = (int)(yScreen);

    for(int part=1; part<PARTS; part++)
	{
      double alfa  = Math.PI*part/PARTS;
            
      int xL   = (int)(xScreen+  rad*(Math.cos(alfa)));
      int yL   = (int)(yScreen+ radP*(Math.sin(alfa)));
      
      int[] xs = {xR,xL,xL,xR};
      int[] ys = {yR,yL,yL-(int)HEIGHT,yR-(int)HEIGHT};
      
      float factor = (0.3f+0.7f*part/PARTS)/255.0f;
      float red = obj.color.getRed()*factor;
      float gre = obj.color.getGreen()*factor;
      float blu = obj.color.getBlue()*factor;

            
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
            
      xs[part] = (int)(xScreen+     rad*Math.cos(alfa));
      ys[part] = (int)(yScreen+PERSP*rad*Math.sin(alfa)-HEIGHT);     
	}
    
    float factor = 0.8f/255.0f;
    float red = obj.color.getRed()*factor;
    float gre = obj.color.getGreen()*factor;
    float blu = obj.color.getBlue()*factor;
             
    graphics.setColor(new Color(red,gre,blu,opacity));
    graphics.fillPolygon(xs,ys,PARTS*2-1); 	
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
  public final void setWorld(A_World world_)  {this.world = world_;}
  
  
  // -----------------------------------------------------------------------------------------------
  P3D legLeft0=new P3D(0,0,0); P3D legLeft1=new P3D(0,0,0);
  P3D legRigt0=new P3D(0,0,0); P3D legRigt1=new P3D(0,0,0);
  P3D armLeft0=new P3D(0,0,0); P3D armLeft1=new P3D(0,0,0);
  P3D armRigt0=new P3D(0,0,0); P3D armRigt1=new P3D(0,0,0);
  P3D body    =new P3D(0,0,0); P3D head    =new P3D(0,0,0);

  
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
        
    legLeft0.set(0,-distLegs,0);
    legLeft1.set(0,-distLegs,0-heightLegs);
    legRigt0.set(0, distLegs,0);
    legRigt1.set(0, distLegs,0-heightLegs);

    armLeft0.set(0,-distArms,0-armsY1);
    armLeft1.set(0,-(distArms+armsDistX),0-armsY1+armsDistY);
    armRigt0.set(0, distArms,0-armsY1);
    armRigt1.set(0,distArms+armsDistX,0-armsY1+armsDistY);
    
    body.set(0,0,-bodyY);
    head.set(0,0,-headY);
    
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
  

  
  
  // draw flash attack
  //
  private final void drawAttackFlash(int x0, int y0, int x1, int y1)
  {
	  
	  
    for(int i=0; i<4; i++)
    {    
        if((i%3)==0) graphics.setColor(FLASH);
        if((i%3)==1) graphics.setColor(FLASHB);
        if((i%3)==2) graphics.setColor(FLASHY);
 	
    	
      int xa = x0;
      int ya  =y0;
    
      for(int part=1; part<=10; part++)
      { int xb = (x0*(10-part)+x1*part)/10;
        int yb = (y0*(10-part)+y1*part)/10;
      
        double a = Math.random()*2*Math.PI;
        double r = Math.random()*16;
        xb += (int)(Math.sin(a)*r);
        yb += (int)(Math.cos(a)*r);
      
        graphics.drawLine(xa, ya, xb, yb);
        graphics.drawLine(xa+2, ya-2, xb+2, yb-2);
        graphics.drawLine(xa-2, ya+2, xb-2, yb+2);
        xa=xb; ya=yb;
      }
    }
  }
  
  
  // draw fireball attack
  //
  private final void drawAttackFireball(int x, int y)
  {
	int g=60;
	int b=0;
    for(int p=16; p>=4; p-=4)
    {
      Color c = new Color(255,g,b);
      graphics.setColor(c);
      graphics.fillOval(x-p, y-p, p+p, p+p);
      g += 15+(int)(Math.random()*30);
      b += (int)(Math.random()*30);
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
  
  
  
  // class for 3-dimensional point in space
  //
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
    
    
    private final void move(double xd,double yd,double zd)
    { x+=xd; y+=yd;z+=zd;
    }
    
    private final void set(double x_, double y_, double z_)
    { xInit=x=x_;  yInit=y=y_;  zInit=z=z_;
    }

    
  }  
}

