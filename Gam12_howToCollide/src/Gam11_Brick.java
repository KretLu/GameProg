
import java.awt.Color;

public class Gam11_Brick extends A_GameObject
{
  public int x2,y2;
	
  public Gam11_Brick(int x1_, int y1_, int x2_, int y2_)
  { super(x1_,y1_,0,0,0,Color.RED);
    x2=x2_; y2=y2_;
    this.isMoving=false;
  }
  
  public int type() { return A_Const.TYPE_BRICK; }
}
