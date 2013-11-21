package GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import GameObjects.GameObject;
import Graphic.Camera;
import Graphic.Point3D;
import Graphic.Vector3D;

public class TPlayer {
	public byte FTeam;
	public Camera FCam;
	
	// status
	private boolean FDead;
	private float FEta;
	
	// gravity
	private float FVelocity;
	private boolean FGrounded;
	
	//
	private Point3D FSpawnPoint;
	
	public TPlayer(byte ATeam, Point3D ASpawnPoint){
		FTeam = ATeam;
		FSpawnPoint = ASpawnPoint;
		FCam = new Camera(
				new Point3D(FSpawnPoint.x, FSpawnPoint.y, FSpawnPoint.z),
				new Point3D(-3.0f, 5.0f, 6.0f),
				new Vector3D(0.0f, 1.0f, 0.0f)
		);
	}
	
	public void KillPlayer(){
		FCam.eye.set(FSpawnPoint.x, FSpawnPoint.y, FSpawnPoint.z);
		
		FVelocity = 0;
		FEta = 10;
		FDead = true;
	}
	
	private void ApplyGravity(float ADeltaTime){
		if(FGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			FVelocity = 9.8f;
		}
		FGrounded = false;
		
		float a = -19.6f;
		FVelocity += a*ADeltaTime;
		FCam.eye.y += 0.5f*a*ADeltaTime*ADeltaTime+FVelocity*ADeltaTime;
		
		if (FVelocity<-50){
			KillPlayer();
		}
	}

	private void CheckCollision(GameObject[] AMap, Point3D AStart, Point3D AEnd){
		// you can only collide with one, x-axis, y-axis or z-axis
		// and if so then it should be changed to that value
		
		float r = 2;//1.8f;
		for (int i=0; i<AMap.length; i++){
			if (AMap[i] != null){
				switch(AMap[i].Intersect(AStart, AEnd, r)){
					case dirX_N: AEnd.x = AMap[i].FX-AMap[i].FSizeX2-r; break;
					case dirX_P: AEnd.x = AMap[i].FX+AMap[i].FSizeX2+r; break;
				
					case dirY_N: {
						AEnd.y = AMap[i].FY-AMap[i].FSizeY2-r;
						FVelocity = 0;
						break;
					}
					case dirY_P: {
						AEnd.y = AMap[i].FY+AMap[i].FSizeY2+r;
						FVelocity = 0;
						FGrounded = true;
						break;	
					}
				
					case dirZ_N: AEnd.z = AMap[i].FZ-AMap[i].FSizeZ2-r; break;
					case dirZ_P: AEnd.z = AMap[i].FZ+AMap[i].FSizeZ2+r; break;
				
					case dirNone: break;
					default: break;
				}
			}
		}
	}
	
	public void update(float ADeltaTime, GameObject[] AMap){
		if (FDead) {
			FEta -= ADeltaTime;
			if (FEta<0) FDead = false;
		}
		
		Point3D start = FCam.eye.clone();
		if (!FDead) ApplyGravity(ADeltaTime);
		InputHandler.HandleUserInput(FCam, ADeltaTime, !FDead);
	
		CheckCollision(AMap, start, FCam.eye);
	}
}
