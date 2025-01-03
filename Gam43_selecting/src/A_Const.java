
// (c) Thorsten Hasbargen


final class A_Const 
{
  // size of the world
  static final int WORLD_WIDTH      = 5000;
  static final int WORLD_HEIGHT     = 4000;
  
  // size of the screen
  static final int SCREEN_WIDTH     = 1920;
  static final int SCREEN_HEIGHT    = 1080;
  
  // for pseudo-3D
  static final double PERSPECTIVE   = 0.7;
  
  // size of the displayed part of the world
  static final int WORLDVIEW_WIDTH  = SCREEN_WIDTH;
  static final int WORLDVIEW_HEIGHT = (int)(SCREEN_HEIGHT/PERSPECTIVE);
  
  // border: when to scroll
  static final int SCROLL_BOUNDS     =  500;
  
  // --------
  
  // Graphic constants
  static final int WIDTH_HEALTH_BAR = 20;
  
  // --------
  
  // for garbage collection
  static final int GARBAGE_INTERVAL  = 10;
	  
  // --------
  
  static final double SPAWN_ZOMBIE   = 2.0;
  static final double SPAWN_GRENADE  = 10.0;
  static final double LIFE_GRENADE   = 15.0;
  
  static final int TYPE_AVATAR         = 1;
  static final int TYPE_TEXT           = 2;
  static final int TYPE_TREE           = 3;
  static final int TYPE_ZOMBIE         = 4;
  static final int TYPE_SHOT           = 5;
  static final int TYPE_GRENADE        = 6;
  static final int TYPE_ATTACKFLASH    = 7;
  static final int TYPE_ATTACKFIREBALL = 8;
}
