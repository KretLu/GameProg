
// (c) Thorsten Hasbargen


class Gam20_World extends A_World 
{
  private double timePassed = 0;
  private double timeSinceLastShot = 0;
  
  // for grenades
  private int grenades = 1;
  private Gam20_CounterGrenades counterG;
  private Gam20_Counter         counterZ;
  private Gam20_HelpText        helpText;
  private double spawnGrenade = 0;
  
  private double lifeHelpText = 10.0;
	
  protected void init()
  {
    // add the Avatar
	avatar = new Gam20_Avatar(400,500);
	gameObjects.add(avatar);
	
	// add a little forrest
    gameObjects.add(new Gam20_Tree(300,200,80));
    gameObjects.add(new Gam20_Tree(600,370,50));
    gameObjects.add(new Gam20_Tree(200,600,50));
    gameObjects.add(new Gam20_Tree(500,600,40));
    gameObjects.add(new Gam20_Tree(800,500,60));
    gameObjects.add(new Gam20_Tree(760,160,40));
    
    // add one zombie
    gameObjects.add(new Gam20_ZombieKI(100,100));
    
    
    counterZ = new Gam20_Counter(20,40);
    counterG = new Gam20_CounterGrenades(770,40);
    helpText = new Gam20_HelpText(100,400);

    counterG.setNumber(grenades);
    textObjects.add(counterZ);
    textObjects.add(counterG);
    textObjects.add(helpText);
  }
	
  protected void processUserInput(A_UserInput userInput, double diffSeconds)
  { 
    // distinguish if Avatar shall move or shoots	  
	int button = userInput.mouseButton;
	
	//
	// Mouse events
	//
	if(userInput.isMouseEvent)
	{
	  // move
	  if(button==1)
	  { avatar.setDestination(userInput.mousePressedX, userInput.mousePressedY);
	  }
	}
	
	//
	// Mouse still pressed?
	//
	if(userInput.isMousePressed && button==3)
	{
	  // only 1 shot every ... seconds:
      timeSinceLastShot += diffSeconds;
      if(timeSinceLastShot > 0.2)
      {
    	timeSinceLastShot = 0;
    	
        Gam20_Shot shot = new Gam20_Shot(
          avatar.x,avatar.y,userInput.mouseMovedX,userInput.mouseMovedY);		
        this.gameObjects.add(shot);    	  
      }
	}
	
	//
	// Keyboard events
	//
	if(userInput.isKeyEvent)
	{
	  if(userInput.keyPressed==' ')
	  {
		throwGrenade(userInput.mouseMovedX,userInput.mouseMovedY);
	  }
	}
  }
  
   
  private void throwGrenade(double x, double y)
  {
	if(grenades<=0) return;  
	  
	// throw grenade
    for(int i=0; i<2000; i++)
    {
      double alfa = Math.random()*Math.PI*2;
      double speed = 50+Math.random()*600;
      double time  = 0.1+Math.random()*0.2;
      Gam20_Shot shot = new Gam20_Shot(x,y,alfa,speed,time);
      this.gameObjects.add(shot);
    }	  
    
    // inform counter
    grenades--;
    counterG.setNumber(grenades);
  }

  
  
  protected void createNewObjects(double diffSeconds)
  {
    createZombie(diffSeconds);
    createGrenade(diffSeconds);
    
    // delete HelpText after ... seconds
    if(helpText!=null)
    {
      lifeHelpText -= diffSeconds;
      if(lifeHelpText < 0)
      {
        textObjects.remove(helpText);
        helpText = null;
      }
    }
  }
  
  
  private void createGrenade(double diffSeconds)
  {
    final double INTERVAL = A_Const.SPAWN_GRENADE;
		  
	spawnGrenade += diffSeconds;
	if(spawnGrenade>INTERVAL)
	{
	  spawnGrenade -= INTERVAL;
	      
	  // create new Grenade
	  double x = 20+Math.random()*960;
	  double y = 20+Math.random()*760;
	      
	  // if too close to Avatar, cancel
	  double dx = x-avatar.x;
	  double dy = y-avatar.y;
	  if(dx*dx+dy*dy < 200*200) 
	  { spawnGrenade = INTERVAL;
	    return;
	  }
	       
	  
	  // if collisions occur, cancel
	  Gam20_Grenade grenade = new Gam20_Grenade(x,y);
	  A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(grenade);
	  if(list.size()!=0)
	  { spawnGrenade = INTERVAL;
	    return;
	  }	  
	  
	  // else add zombie to world
	  this.gameObjects.add(grenade);
      counterG.setNumber(grenades);      
    }
	  
  }
  
  
  
  private void createZombie(double diffSeconds)
  {
    final double INTERVAL = A_Const.SPAWN_INTERVAL;
		  
	timePassed += diffSeconds;
	if(timePassed>INTERVAL)
	{
	  timePassed -= INTERVAL;
	      
	  // create new Zombie
	  double x = 20+Math.random()*960;
	  double y = 20+Math.random()*760;
	      
	  // if too close to Avatar, cancel
	  double dx = x-avatar.x;
	  double dy = y-avatar.y;
	  if(dx*dx+dy*dy < 200*200) 
	  { timePassed = INTERVAL;
	    return;
	  }
	      
	  // if collisions occur, cancel
	  Gam20_ZombieKI zombie = new Gam20_ZombieKI(x,y);
	  A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(zombie);
	  if(list.size()!=0)
	  { timePassed = INTERVAL;
	    return;
	  }
	      
	  // else add zombie to world
	  this.gameObjects.add(zombie);
	  zombie.setDestination(avatar);
	  Gam20_Counter counter = (Gam20_Counter)textObjects.get(0);
      counter.increment();      
    }
	  
  }
 
  
  public void addGrenade()
  {
    if(grenades<3) { grenades++; }
    counterG.setNumber(grenades);
  }
  
  
  public void gameOver()
  {
	while(true);
  }  
}
