
// (c) Thorsten Hasbargen


final class A_UserInput 
{
  // everything a user can press on keyboard or mouse	
  int mousePressedX, mousePressedY, 
      mouseMovedX,   mouseMovedY, mouseButton;
  
  char keyPressed;
  
  // only internally used
  private final char[] zz_charsPressed = new char[20];
  
  // if Mouse was clicked, Key was pressed or Mouse is still hold down
  boolean isMouseEvent, isKeyEvent, isMousePressed; 
  
  // clear all Input events on construction
  A_UserInput()
  { 
	this.clear();
    for(int i=0; i<zz_charsPressed.length; i++)
    { zz_charsPressed[i] = 0;
    }  
  }
  
  final void clear()
  { 
	isMouseEvent=false; isKeyEvent=false;

  }
  
  // if the keys which are pressed contain some char
  final boolean isKeyPressed(char c)
  {
	for(int i=0; i<zz_charsPressed.length; i++)
	{ if(zz_charsPressed[i]==c) return true;
	}
	return false;
  }

  
  
  // only for internal use:
  // 
  
  // register some char was pressed
  final void zz_registerChar(char c)
  {
	if(isKeyPressed(c)) return;
	for(int i=0; i<zz_charsPressed.length; i++)
	{
      if(zz_charsPressed[i]==0)
      {
        zz_charsPressed[i]=c;
        return;
      }
	}
  }
  
  // un-register some char which is NOT pressed any more
  final void zz_unregisterChar(char c)
  {
	for(int i=0; i<zz_charsPressed.length; i++)
	{
      if(zz_charsPressed[i]==c)
      { zz_charsPressed[i]=0;
      }
	}
  }
  
  
}
