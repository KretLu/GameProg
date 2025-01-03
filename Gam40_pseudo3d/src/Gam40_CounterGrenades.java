
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam40_CounterGrenades extends A_TextObject
{
  private int number = 1;
	
  public Gam40_CounterGrenades(int x, int y)
  { super(x,y, new B_Shape(0,new Color(255,255,0,210)));
  }
  
  public String toString()
  { String display = "Grenades: ";
    display += number;
    return display;
  }
  
  public void setNumber(int n){number=n;}
}
