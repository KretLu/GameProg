
public class Main 
{
  private RunnerFrame frame;
  

  public void init()
  {
	frame = new RunnerFrame();
	frame.setVisible(true);
  }  

  
  
  public static void main(String[] args)
  {
	Main world = new Main();
	world.init();
  }  
}
