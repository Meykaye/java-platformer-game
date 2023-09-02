package utilz;

import main.Game;

public class Constants {
	
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class URMButtons {
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class VolumeButtons {
			public static final int VOLUME_WIDTH_DEFAULT = 28;
			public static final int VOLUME_HEIGHT_DEFAULT = 44;
			public static final int SLIDER_WIDTH_DEFAULT = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * Game.SCALE);
		}
	}
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class  PlayerConstants {
		
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
