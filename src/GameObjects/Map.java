package GameObjects;

import Graphic.TTextures;

public class Map {
	public static GameObject[] GenerateMap(){
		int i = 0;
		
		GameObject[] result = new GameObject[65];
		
		// ground
		result[i++] = new Box(0,-1,-30,	49.5f,2,49.5f,	TTextures.Wall_Stone);
		
		result[i++] = new Box(0,-1, 30,	49.5f,2,49.5f,	TTextures.Wall_Stone);
		
		// edge walls
		result[i++] = new Box(-15f,10,-10, 		 20,20,10, 		TTextures.Wall_Stone); //left
		result[i++] = new Box(15f,10,-10, 		 20,20,10, 		TTextures.Wall_Stone); //right

		result[i++] = new Box(-15f,10,10, 		 20,20,10, 		TTextures.Wall_Stone); //left
		result[i++] = new Box(15f,10,10, 			 20,20,10, 		TTextures.Wall_Stone); //right
		
		// side edge walls
		result[i++] = new Box(-20f,5,-35, 		 10,10,40, 		TTextures.Wall_Stone); //left
		result[i++] = new Box(20f,5,-35, 			 10,10,40, 		TTextures.Wall_Stone); //right
		
		result[i++] = new Box(-20f,5,35, 			 10,10,40, 		TTextures.Wall_Stone); //left
		result[i++] = new Box(20f,5,35,			 10,10,40, 		TTextures.Wall_Stone); //right
		
		// ramp, ground to side wall
		result[i++] = new Box(-12.5f,2.5f,-20, 	 5,5,10, 		TTextures.Wall_Wood); //left
		result[i++] = new Box( 12.5f,2.5f,-20, 	 5,5,10, 		TTextures.Wall_Wood); //right
		
		for (int j=1; j<9; j++){
			float x = j*1.25f;
			result[i++] = new Stairs(-4.375f-x,x/2,20,	1.25f,x,10,	TTextures.Wall_Wood); //left
			result[i++] = new Stairs( 4.375f+x,x/2,20,	1.25f,x,10,	TTextures.Wall_Wood); //right
			
			result[i++] = new Stairs(-4.375f-x,x/2,-20,	1.25f,x,10,	TTextures.Wall_Wood); //left
			result[i++] = new Stairs( 4.375f+x,x/2,-20,	1.25f,x,10,	TTextures.Wall_Wood); //right
		}
		result[i++] = new Box(-10f,5,26.25f,	10,10,2.5f,	TTextures.Wall_Stone); //left
		result[i++] = new Box( 10f,5,26.25f,	10,10,2.5f,	TTextures.Wall_Stone); //left
		
		result[i++] = new Box(-10f,5,-26.25f,	10,10,2.5f,	TTextures.Wall_Stone); //left
		result[i++] = new Box( 10f,5,-26.25f,	10,10,2.5f,	TTextures.Wall_Stone); //left
		
		// ramp, side wall to edge wall
		result[i++] = new Box(-20f,12.5f,-17.5f,	5,5,5, 		TTextures.Wall_Wood); //left
		result[i++] = new Box( 20f,12.5f,-17.5f,	5,5,5, 		TTextures.Wall_Wood); //right
		
		result[i++] = new Box(-20f,12.5f,17.5f,	5,5,5, 		TTextures.Wall_Wood); //left
		result[i++] = new Box( 20f,12.5f,17.5f,	5,5,5, 		TTextures.Wall_Wood); //right
		
		// ramp, side wall to flag wall
		result[i++] = new Box( -7.5f,13.75f,-50,	5,2.5f,10,	TTextures.Wall_Wood); //left
		result[i++] = new Box(-12.5f,11.25f,-50,	5,2.5f,10,	TTextures.Wall_Wood); //left
		result[i++] = new Box(  7.5f,13.75f,-50,	5,2.5f,10,	TTextures.Wall_Wood); //right
		result[i++] = new Box( 12.5f,11.25f,-50,	5,2.5f,10,	TTextures.Wall_Wood); //right
		
		result[i++] = new Box( -7.5f,13.75f,50,	5,2.5f,10,	TTextures.Wall_Wood); //left
		result[i++] = new Box(-12.5f,11.25f,50,	5,2.5f,10,	TTextures.Wall_Wood); //left
		result[i++] = new Box(  7.5f,13.75f,50,	5,2.5f,10,	TTextures.Wall_Wood); //right
		result[i++] = new Box( 12.5f,11.25f,50,	5,2.5f,10,	TTextures.Wall_Wood); //right
		
		// Flag wall
		result[i++] = new Box(0,8.75f,-50,	10,17.5f,10,		TTextures.Wall_Stone);
		result[i++] = new Box(0,8.75f, 50,	10,17.5f,10,		TTextures.Wall_Stone);
		
		//ScullFloor
		result[i++] = new Box(0,-1,0,	10,2,10,	TTextures.Wall_Wood);
		
		//Flags
		result[i++] = new Flag(0,16.5f,-47.5f,	0,0,0,		0); //Flag 1
		result[i++] = new Flag(0,16.5f,47.5f,	180,0,1,	0); //Flag 2
		
		return result;
	}
}
