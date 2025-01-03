public class CircleWorld 
{
  private GraphicSystem graphicSystem;
  
  private Dot dot;
  
  public void init()
  {
	dot = new Dot(100,400);
  }
  
  public void run()
  {
	while(true)
	{
	  dot.move();
	  
	  graphicSystem.clear();
	  graphicSystem.draw(dot);
	  graphicSystem.redraw();
	}
  }
  
  
  public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }
}
