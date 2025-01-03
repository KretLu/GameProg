
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class RunnerPanel extends JPanel implements KeyListener
{
  private static final long serialVersionUID = 1L;
  
  // needed objects
  //
  private Vector<Item> objects = new Vector<Item>();
  private Player player;
  private RunnerFrame frame;
  
  
  public RunnerPanel(RunnerFrame _frame)
  { 
	frame = _frame;
    this.addKeyListener(this);
    this.setSize(Const.WIDTH, Const.HEIGHT);
    
    // create player and insert into object list
    //
    player = new Player();
    objects.add(player);
    
  }
  
  // screen is painted: clear it and draw all objects
  //
  public void paint(Graphics gra)
  {
	gra.setColor(new Color(200,200,255));
	gra.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
	
	// draw all objects
	for(Item item : objects)
	{ item.draw(gra);
	}
  }
  
  
  public void keyPressed(KeyEvent evt)
  {
    char c = evt.getKeyChar();
    changeTheWorld(c);
  }
  
  
  // this method changes the world, according to
  // - the players action
  // - the action of the other game objects
  //
  public void changeTheWorld(char c)
  {
    // change the players position
	//
    switch(c)
    {
      case 'd' : player.down();
      break;
      case 'e' : player.up();
      break;
      case 'f' : player.right();
      break;
    }
    
    
    // check if the player collides with any blob
    checkCollisions();
    
    // change the position of the blobs
    //
    for(Item item : objects)
    {
      if(item!=player)
      { 
    	// let the object move itself
    	item.move();
      }
    }
    
    
    // check if the player collides with any blob
    checkCollisions();

    
    // maybe create new objects
    //
    if(false)for(int i=0; i<Const.MAX_NEW_OBJECTS; i++)
    {
      if(Math.random()<Const.CHANCE_NEW_OBJECT)
      {
    	int yPos = ((int)(Math.random()*Const.HEIGHT));
    	yPos -= yPos%Const.CIRCLE;
    	
        Blob blob = new Blob(Const.WIDTH, yPos);
        objects.add(blob);
      }
    }
    
    frame.repaint();
  }
  

  
  public void checkCollisions()
  {
	for(Item item : objects)
	{
	  if(item!=player)
	  {
	    // do blob and player collide?
		// their distance must be Const.CIRCLE or more...
        int xd = player.x-item.x;
        int yd = player.y-item.y;
		double dist = Math.sqrt(xd*xd+yd*yd);
		if(dist<Const.CIRCLE)
		{ gameOver();
		}
	  }
	}
  }
  
  
  public void gameOver()
  { System.exit(0);
  }
  
 
  public void keyTyped(KeyEvent evt){}
  public void keyReleased(KeyEvent evt){}

}
