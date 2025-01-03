
public class Gam03_World extends A_World 
{
  protected void init()
  {
	gameObjects.add(new Gam03_Avatar(400,500));
	
	for(int i=0; i<20; i++)
	{ 
	  double x = 1000*Math.random();
	  double y = 800*Math.random();
	  double a = Math.PI*2*Math.random();
	  double s = 100+100*Math.random();
		
	  Gam03_Dot dot = new Gam03_Dot(x,y,a,s);
	  gameObjects.add(dot);
	}
  }
	
  protected void processUserInput(A_UserInput userInput)
  {
    Gam03_Avatar avatar = (Gam03_Avatar)this.gameObjects.get(0);
    avatar.setDestination(userInput.mousePressedX, userInput.mousePressedY);
  }
}
