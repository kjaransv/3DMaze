package GameObjects;

import Graphic.Point3D;

public abstract class GameObject {
	public enum Direction{
		dirNone, dirX_P, dirY_P, dirZ_P, dirX_N, dirY_N, dirZ_N
	}
	
	public float FX, FY, FZ;
	public float FSizeX, FSizeY, FSizeZ;
	public float FSizeX2, FSizeY2, FSizeZ2;
	
	public abstract void Render();
	public abstract Direction Intersect(Point3D AStart, Point3D AEnd, float ARadius);
	
	public GameObject(float AX, float AY, float AZ, float ASizeX, float ASizeY, float ASizeZ){
		FX = -AX;
		FY = AY;
		FZ = AZ;
		
		FSizeX = ASizeX;
		FSizeY = ASizeY;
		FSizeZ = ASizeZ;
		
		FSizeX2 = ASizeX/2;
		FSizeY2 = ASizeY/2;
		FSizeZ2 = ASizeZ/2;
	}
}
