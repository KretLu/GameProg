
import java.awt.Color;

public class Gam11_Counter extends A_TextObject
{
  private long start;
	
  public Gam11_Counter()
  {
    super(50,40,Color.ORANGE);
    start = System.currentTimeMillis();
  }
  
  public String toString()
  {
    long diff = System.currentTimeMillis()-start;
    String secs = ""+(diff/1000)+"."+((diff/100)%10);
    return secs;
  }
}
