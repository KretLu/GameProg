
// (c) Thorsten Hasbargen


import java.awt.Color;

class Gam43_CounterZombies extends A_TextObject
{
  private int number = 1;
	
  public Gam43_CounterZombies(int x, int y)
  { super(x,y, new Color(255,255,0,210));
  }
  
  public String toString()
  { String display = "Zombies: ";
    display += number;
    return display;
  }
  
  public void increment(){ number++; }
}
