
// (c) Thorsten Hasbargen


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

abstract class A_World
{
  private  A_GraphicSystem graphicSystem;
  private  A_PhysicsSystem physicsSystem;
  private  A_InputSystem   inputSystem;  
  private  A_UserInput     userInput;
  
  // top left corner of the displayed pane of the world
  double worldViewX = 0;
  double worldViewY = 0;
  
  // defines maximum frame rate
  private static final int FRAME_MINIMUM_MILLIS = 10;
  private static final boolean SHOW_FRAME       = false;
  
  // for garbage collection
  private int garbageCount = 0;
  
  // if game is over
  boolean gameOver = false;
  

  
  
  // all objects in the game, including the Avatar
  // and the GameObject which was selected by mouse click
  //
  A_GameObjectList        gameObjects = new A_GameObjectList();
  ArrayList<A_TextObject> textObjects = new ArrayList<A_TextObject>();
  A_GameObject            avatar;          
  A_GameObject            selectedGameObject = null;
  
  
  // for order-dependend drawing of all game objects
  private A_GameObject[] gameObjectsArray = null;

    
  
  A_World()
  { physicsSystem = new Gam43_PhysicsSystem(this);
  }
  
  
  //
  // the main GAME LOOP
  //
  final void run()
  {
	long lastTick =  System.currentTimeMillis();
	
	while(true)
	{
	  // calculate elapsed time
	  //
	  long currentTick = System.currentTimeMillis();
	  long millisDiff  = currentTick-lastTick;
	  
	  if(SHOW_FRAME) System.out.println("## frame time: "+millisDiff);
	  
	  // don´t run faster then MINIMUM_DIFF_SECONDS per frame
	  //
	  if(millisDiff<FRAME_MINIMUM_MILLIS)
	  {
	    try{ Thread.sleep(FRAME_MINIMUM_MILLIS-millisDiff);} catch(Exception ex){}
		currentTick = System.currentTimeMillis();
		millisDiff  = currentTick-lastTick;
	  }
	  
	  lastTick = currentTick;

	  
	  // process User Input
	  userInput = inputSystem.getUserInput();
	  processUserInput(userInput,millisDiff/1000.0);
	  userInput.clear();

	  // no actions if game is over
	  if(gameOver) { continue;}
	  
	  // move all Objects, maybe collide them etc...
	  int gameSize = gameObjects.size();
	  for(int i=0; i<gameSize; i++)
	  { 
        A_GameObject obj = gameObjects.get(i);
        if(obj.isLiving)  obj.move(millisDiff/1000.0);
	  }
	  
	  
      // delete all Objects which are not living anymore
      int num=0;
      while(num<gameSize)
      {
        if(gameObjects.get(num).isLiving==false)
        { gameObjects.remove(num);
          gameSize--;
        }
        else
        { num++;
        }
      }	  
	  
      
      // adjust displayed pane of the world
      this.adjustWorldView();
      
      
	  // draw all Objects
      //
	  graphicSystem.clear();
	  sortGameObjects();
	  for(int i=0; i<gameObjectsArray.length; i++) 
	  { graphicSystem.draw(gameObjectsArray[i]);  }

	  
      // draw all TextObjects
	  for(int i=0; i<textObjects.size(); i++)
	  { graphicSystem.draw(textObjects.get(i));
	  }
	  
	  // redraw everything
	  graphicSystem.redraw();
	  	  
	  // create new objects if needed
	  createNewObjects(millisDiff/1000.0);
	  
	  // do garbage collection if interval is reached
	  garbageCount++;
	  if(garbageCount>=A_Const.GARBAGE_INTERVAL)
	  { garbageCount=0;
	    System.gc();
	  }
	}
  }
  
  
  // adjust the displayed pane of the world according to Avatar and Bounds
  //
  private final void adjustWorldView()
  {
    final int RIGHT_END  = A_Const.WORLD_WIDTH-A_Const.WORLDVIEW_WIDTH;
    final int BOTTOM_END = A_Const.WORLD_HEIGHT-A_Const.WORLDVIEW_HEIGHT;
	  	  
    
	// if avatar is too much right in display ...
    if(avatar.x > worldViewX+A_Const.WORLDVIEW_WIDTH-A_Const.SCROLL_BOUNDS)
    {
      // ... adjust display
      worldViewX = avatar.x+A_Const.SCROLL_BOUNDS-A_Const.WORLDVIEW_WIDTH;
      if(worldViewX >= RIGHT_END)
      { worldViewX = RIGHT_END;
      }
    }
    
    // same left
    else if(avatar.x < worldViewX+A_Const.SCROLL_BOUNDS)
    {
      worldViewX = avatar.x-A_Const.SCROLL_BOUNDS;	
      if(worldViewX <=0)
      { worldViewX = 0;
      }
    }
    
    // same bottom
    if(avatar.y > worldViewY+A_Const.WORLDVIEW_HEIGHT-A_Const.SCROLL_BOUNDS)
    {
        worldViewY = avatar.y+A_Const.SCROLL_BOUNDS-A_Const.WORLDVIEW_HEIGHT;
        if(worldViewY >= BOTTOM_END)
        { worldViewY = BOTTOM_END;
        }   	
    }
    
    // same top
    else if(avatar.y < worldViewY+A_Const.SCROLL_BOUNDS)
    {
      worldViewY = avatar.y-A_Const.SCROLL_BOUNDS;
      if(worldViewY <=0)
      { worldViewY = 0;
      }
    }      
  }
  
  
  
  //
  // select GameObject by the coordinates clicked with the mouse
  //
  protected final void selectGameObject(int xMouse, int yMouse)
  {
    // calculate world coordinates
    double xWorld = userInput.mousePressedX+worldViewX;
    double yWorld = userInput.mousePressedY/A_Const.PERSPECTIVE+worldViewY;
		
    
    // find GameObject for this coordinate
    //
    selectedGameObject = null;
    
    for(int i=0; i<gameObjects.size(); i++)
    {
      A_GameObject obj = gameObjects.get(i);
        
      // mouse click in objects bounds?
      if(xWorld>=obj.x-obj.radius() && xWorld<=obj.x+obj.radius())
      {
        if(yWorld<=obj.y && yWorld>=obj.y-obj.height()/A_Const.PERSPECTIVE)
        {          
          // object is closer to foreground than current selected?
          if(selectedGameObject==null || selectedGameObject.y<obj.y)
          { selectedGameObject=obj;
          }
        }
      }              
    }    
  }
  
  
  // sort GameObjectsArray, the most Y-front ones are last in array
  //
  private final void sortGameObjects()
  {
	gameObjectsArray = 
	      gameObjects.toArray(new A_GameObject[gameObjects.size()]);
	  
	Arrays.sort(gameObjectsArray, new Comparator<A_GameObject>() 
	{  public int compare(A_GameObject first, A_GameObject second) 
	   { return (int)(Math.signum(first.y-second.y)); }
	} );	  
  }
  
    
  protected void setGraphicSystem(A_GraphicSystem p) { graphicSystem = p; }
  protected void setInputSystem(A_InputSystem p)     { inputSystem   = p; }
  
  protected A_PhysicsSystem getPhysicsSystem()       { return physicsSystem; }
  
  
  protected abstract void init();
  protected abstract void processUserInput(A_UserInput input, double diffSec);
  protected abstract void createNewObjects(double diffSeconds);
  
}
