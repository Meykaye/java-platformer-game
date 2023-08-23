package utilz;

public class Constants {
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class  PlayerConstants {
		public static final int IDLE = 0;
		public static final int WALKING = 2;
		public static final int RUNNING = 3; 
		public static final int CROUCHING = 4;
		public static final int JUMPING = 5;
		public static final int VANISHING = 6;
		public static final int DYING = 7;
		public static final int ATTACKING = 8; 
		
		public static int GetSpriteAmt(int player_action) {
			
			switch(player_action) {
				case RUNNING:          
				case JUMPING:
				case DYING:
				case ATTACKING:
					return 8;
				
				case WALKING:
					return 4;
					
				case CROUCHING:
					return 6;
					
				case VANISHING:
					return 3;
					
				default:
					return 2;
			}
			
		}
	}
	
}
