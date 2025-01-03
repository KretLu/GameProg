
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam40_HelpText extends A_TextObject
{
  public Gam40_HelpText(int x, int y)
  { super(x,y, new B_Shape(0,new Color(0,120,255,60)));
  }
  
  public String toString()
  { String display = "MOVE:Mouse left      SHOOT:Mouse right      "+
                     "Grenade:Space bar     END: Escape";
    return display;
  }
  
}
