
import javax.swing.*;

public class RunnerFrame extends JFrame 
{
  private static final long serialVersionUID = 1L;

  public RunnerFrame()
  {
	this.setSize(Const.WIDTH+8,Const.HEIGHT+20); 
	this.setLocation(200,200);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	RunnerPanel panel = new RunnerPanel(this);
    panel.setFocusable(true);
    panel.requestFocusInWindow();
	
	this.setContentPane(panel);
  }
}
