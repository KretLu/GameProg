
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam43_HelpText extends A_TextObject
{
  public Gam43_HelpText(int x, int y)
  { super(x,y,new Color(0,120,255,60));
  }
  
  public String toString()
  { String display = "MOVE:Mouse left      SHOOT:Mouse right      "+
                     "Grenade:Space bar     END: Escape";
    return display;
  }
  
}
