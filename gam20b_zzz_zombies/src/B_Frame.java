
// (c) Thorsten Hasbargen


import javax.swing.*;

class B_Frame extends JFrame implements A_Frame
{
  // ...ok...
  private static final long serialVersionUID = 2L;

  private B_Panel panel = null;	

  public B_Frame()
  { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(A_Const.WIDTH+20,A_Const.HEIGHT+40);
	this.setResizable(false);
	
	panel = new B_Panel();
	
	// needed for Keyboard input !!!
	panel.setFocusable(true);
	panel.requestFocusInWindow();
	
	this.setContentPane(panel);
  }
  
  public void displayOnScreen() { validate(); setVisible(true); }
  
  public A_GraphicSystem getGraphicSystem() { return panel; }
  public A_InputSystem   getInputSystem()   { return panel.getInputSystem(); }
}
