
import javax.swing.*;

public class B_Frame extends JFrame
{
  // ...ok...
  private static final long serialVersionUID = 2L;

  private B_Panel panel = null;	

  public B_Frame()
  { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(1020,845);
	
	panel = new B_Panel();
	this.setContentPane(panel);
  }
  
  public B_Panel getPanel() {return panel;}
}
