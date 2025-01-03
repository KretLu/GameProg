
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam40_Tree extends A_GameObject
{
  protected static final Color COLOR = new Color(64,160,64);
  public Gam40_Tree(double x, double y, int r)
  {
    super(x,y,0,0,new B_Shape(r,COLOR));
    this.isMoving = false;
  }
  
  public int type() { return A_Const.TYPE_TREE; }
}
