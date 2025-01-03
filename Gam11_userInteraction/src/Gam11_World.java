
// (c) Thorsten Hasbargen


public class Gam11_World extends A_World 
{
  protected void init()
  {
    // add the Avatar
	avatar = new Gam11_Avatar(400,500);
	gameObjects.add(avatar);
	
	gameObjects.add(new Gam11_Brick(50,50,250,150));
	gameObjects.add(new Gam11_Brick(150,300,350,450));
	gameObjects.add(new Gam11_Brick(350,500,650,550));
  }
	
  
  protected void processUserInput(A_UserInput userInput, double diffSeconds)
  { 
	//
	// Keyboard events
	//
	if(userInput.isKeyEvent)
	{
	  if(userInput.keyPressed=='w')
	  { avatar.speed += 20;
	  }
	  else if(userInput.keyPressed=='s')
	  { avatar.speed -= 20;
	  }
	  else if(userInput.keyPressed=='a')
	  { avatar.alfa -= 0.1;
	  }
	  else if(userInput.keyPressed=='d')
	  { avatar.alfa += 0.1;
	  }	}
  }
  
  
  protected void createNewObjects(double diffSeconds) {}
  
  public void gameOver()
  { while(true);
  }  
}
