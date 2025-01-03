
public class A_Main 
{
  private A_World world = null;
  
  public A_Main()
  { 
	B_Frame frame = new B_Frame();
    frame.setVisible(true);
    
    world = new B_World();
    
    world.setGraphicSystem(frame.getPanel());
    world.setInputSystem(frame.getPanel());
    
    A_GameObject.setPhysicsSystem(world.getPhysicsSystem());
    A_GameObject.setWorld(world);
    
    world.init();
    world.run();
  }
	
  public static void main(String[] args)
  { new A_Main();
  }
}
