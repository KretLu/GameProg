
// (c) Thorsten Hasbargen

class A_Main 
{
  private A_World world = null;
  
  public A_Main()
  { 
	A_Frame frame = new B_Frame();
    frame.displayOnScreen();
    
    // depends on the ACTUAL GAM-world class
    //
    world = new Gam11_World();
  
    world.setGraphicSystem(frame.getGraphicSystem());
    world.setInputSystem(frame.getInputSystem());
    A_GameObject.setWorld(world);
   
    world.init();
    world.run();
  }
	
  public static void main(String[] args)
  { new A_Main();
  }
}
