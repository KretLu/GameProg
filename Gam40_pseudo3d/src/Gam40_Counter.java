
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam40_Counter extends A_TextObject
{
  private int number = 1;
	
  public Gam40_Counter(int x, int y)
  { super(x,y, new B_Shape(0,new Color(255,255,0,210)));
  }
  
  public String toString()
  { String display = "Zombies: ";
    display += number;
    return display;
  }
  
  public void increment(){ number++; }
}
