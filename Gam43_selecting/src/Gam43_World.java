
// (c) Thorsten Hasbargen


class Gam43_World extends A_World 
{
  private double timePassed = 0;
  
  // for grenades
  private Gam43_CounterZombies  counterZ;
  private Gam43_HelpText        helpText;
  
  private double lifeHelpText = 10.0;
  

  protected void init()
  {
    // add the Avatar
	avatar = new Gam43_Avatar(2500,2000);
	gameObjects.add(avatar);
	
	// set WorldPart position
	worldViewX = 1500;
	worldViewY = 1500;
	
	// add a little forrest
	
	for(int x=0; x<5000; x+=1000)
	{

	  for(int y=0; y<5000; y+=800)
	  {
        gameObjects.add(new Gam43_Tree(x+300,y+200,80));
        gameObjects.add(new Gam43_Tree(x+600,y+370,50));
        gameObjects.add(new Gam43_Tree(x+200,y+600,50));
        gameObjects.add(new Gam43_Tree(x+500,y+800,40));
        gameObjects.add(new Gam43_Tree(x+900,y+500,100));
        gameObjects.add(new Gam43_Tree(x+760,y+160,40));
	  }
	}
    


    // add one zombie
    gameObjects.add(new Gam43_ZombieKI(100,100));
    
    
    counterZ = new Gam43_CounterZombies(20,40);
    helpText = new Gam43_HelpText(100,400);

    textObjects.add(counterZ);
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
	  { avatar.setDestination(
			  userInput.mousePressedX+worldViewX, 
              userInput.mousePressedY/A_Const.PERSPECTIVE+worldViewY);
	  }
	  
	  // select GameObject
	  if(button==3)
	  { selectGameObject(userInput.mousePressedX,userInput.mousePressedY);
	  }
	}
	

	
	//
	// Keyboard events
	//
	if(userInput.isKeyEvent)
	{
	  // flash attack
	  //
	  if(userInput.keyPressed=='1')
	  { 
		if(selectedGameObject!=null)
		{
	      Gam43_AttackFlash attack = new Gam43_AttackFlash(avatar,selectedGameObject);
	      gameObjects.add(attack);
		}
	  }	  

      // fireball attack
      //
      else if(userInput.keyPressed=='2')
      { 
        if(selectedGameObject!=null)
        {
            Gam43_AttackFireball attack = new Gam43_AttackFireball(avatar,selectedGameObject);
            gameObjects.add(attack);
        }	  
      }
	
	  // exit game
      else if(userInput.keyPressed==(char)27)
      { System.exit(0);
      }
	}
  }
  
   
  
  protected void createNewObjects(double diffSeconds)
  {	  	  
    createZombie(diffSeconds);
    
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
  
  
  
  
  
  private void createZombie(double diffSeconds)
  {
    final double INTERVAL = A_Const.SPAWN_ZOMBIE;
		  
	timePassed += diffSeconds;
	if(timePassed>INTERVAL)
	{
	  timePassed -= INTERVAL;
	      
	  // create new Zombie; preference to current screen
	  double x,y;
	  if(Math.random() < 0.7)
	  { x = Math.random()*A_Const.WORLD_WIDTH;
	    y = Math.random()*A_Const.WORLD_HEIGHT;
	  }
	  else
	  { x = worldViewX+Math.random()*A_Const.WORLDVIEW_WIDTH;
	    y = worldViewY+Math.random()*A_Const.WORLDVIEW_HEIGHT;
	  }
	  
	      
	  // if too close to Avatar, cancel
	  double dx = x-avatar.x;
	  double dy = y-avatar.y;
	  if(dx*dx+dy*dy < 400*400) 
	  { timePassed = INTERVAL;
	    return;
	  }
	      
	  // if collisions occur, cancel
	  Gam43_ZombieKI zombie = new Gam43_ZombieKI(x,y);
	  A_GameObjectList list = A_GameObject.physicsSystem.getCollisions(zombie);
	  if(list.size()!=0)
	  { timePassed = INTERVAL;
	    return;
	  }
	      
	  // else add zombie to world
	  this.gameObjects.add(zombie);
	  zombie.setDestination(avatar);
	  Gam43_CounterZombies counter = (Gam43_CounterZombies)textObjects.get(0);
      counter.increment();      
    }
	  
  }
 


}
