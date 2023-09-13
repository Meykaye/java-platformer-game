package utilz;

import main.Game;

public class Constants {
	
	public static class EnemyConstants {
		public static final int MONSTER = 0;
		
		public static final int IDLE = 0;
		public static final int WALKING = 4;
		public static final int ATTACKING = 3;
		public static final int HITTING = 2;
		public static final int DYING = 1;
		
//		public static final int IDLE = 0;
//		public static final int ATTACKING = 1;
//		public static final int HITTING = 2;
//		public static final int DYING = 3;
		
		
		public static final int MONSTER_DEFAULT_WIDTH = 32;
		public static final int MONSTER_DEFAULT_HEIGHT = 32;
		
		public static final int MONSTER_WIDTH = (int) (MONSTER_DEFAULT_WIDTH * Game.SCALE);
		public static final int MONSTER_HEIGHT = (int) (MONSTER_DEFAULT_HEIGHT * Game.SCALE);
		
		public static final int MONSTER_DRAWOFFSET_X = (int) (1* Game.SCALE); //26
		public static final int MONSTER_DRAWOFFSET_Y = (int) (12* Game.SCALE); //9
		
		public static int GetSpriteAmt(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case MONSTER:
				switch(enemy_state) {
				case IDLE:
				case HITTING:
				case DYING:
				case WALKING:
					return 4;
				case ATTACKING:
					return 5;
				}
			}
			return 0;
		}
		
		public static int GetMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case MONSTER:
				return 5;
			default:
				return 1;
			}
		}
		
		public static int GetEnemyDmg(int enemy_type) {
			switch(enemy_type) {
			case MONSTER:
				return 15;
			default:
				return 0;
			}
		}
	}
	
	public static class Environment {
		public static final int BACKGROUNDIMG_2_DEFAULT_WIDTH = 640;
		public static final int BACKGROUNDIMG_2_DEFAULT_HEIGHT = 360;
		public static final int BACKGROUNDIMG_3_DEFAULT_WIDTH = 640;
		public static final int BACKGROUNDIMG_3_DEFAULT_HEIGHT = 360;
		public static final int DRIPSTONE_DEFAULT_WIDTH = 544;
		public static final int DRIPSTONE_DEFAULT_HEIGHT = 80;
		
		public static final int BACKGROUNDIMG_2_WIDTH = (int) (BACKGROUNDIMG_2_DEFAULT_WIDTH * Game.SCALE);
		public static final int BACKGROUNDIMG_2_HEIGHT = (int) (BACKGROUNDIMG_2_DEFAULT_HEIGHT * Game.SCALE);
		public static final int BACKGROUNDIMG_3_WIDTH = (int) (BACKGROUNDIMG_3_DEFAULT_WIDTH * Game.SCALE);
		public static final int BACKGROUNDIMG_3_HEIGHT = (int) (BACKGROUNDIMG_3_DEFAULT_HEIGHT * Game.SCALE);
		public static final int DRIPSTONE_WIDTH = (int) (DRIPSTONE_DEFAULT_WIDTH * Game.SCALE);
		public static final int DRIPSTONE_HEIGHT = (int) (DRIPSTONE_DEFAULT_HEIGHT * Game.SCALE);
		
	}
	
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
