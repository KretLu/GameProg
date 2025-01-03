
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam20_HelpText extends A_TextObject
{
  public Gam20_HelpText(int x, int y)
  { super(x,y, new B_Shape(0,new Color(0,120,255,60)));
  }
  
  public String toString()
  { String display = "MOVE:Mouse left        SHOOT:Mouse right        Grenade:Space bar";
    return display;
  }
  
}
