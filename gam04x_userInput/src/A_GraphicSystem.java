
public interface A_GraphicSystem 
{
  // prepare to draw a new Screen
  public void clear();
  
  // draw ONE GameObject on the graphical output
  public void draw(A_GameObject gameObj);
  
  // display the completed graphical output
  public void redraw();
}