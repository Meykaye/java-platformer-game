package utilz;

public class Constants {
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class  PlayerConstants {
//		public static final int IDLE = 0;
//		public static final int WALKING = 2;
//		public static final int RUNNING = 3; 
//		public static final int CROUCHING = 4;
//		public static final int JUMPING = 5;
//		public static final int VANISHING = 6;
//		public static final int DYING = 7;
//		public static final int ATTACKING = 8; 
//		public static final int FALLING = 9;
		
		public static final int IDLE = 0;
		public static final int WALK = 2;
		public static final int RUN = 3;
		public static final int CROUCH = 4;
		public static final int JUMP = 5;
		public static final int VANISH = 6;
		public static final int DYING = 7;
		public static final int ATTACK = 8;
		public static final int FALLING = 9;
		public static int GetSpriteAmt(int player_action) {
			
			switch(player_action) {
				case RUN:          
				case DYING:
				case ATTACK:
					return 8;
				
				case WALK:
					return 4;
					
				case CROUCH:
					return 6;
					
				case VANISH:
				case JUMP:
					return 3;
					
				case FALLING:
					return 1;
				default:
					return 2;
			}
			
		}
	}
	
}
