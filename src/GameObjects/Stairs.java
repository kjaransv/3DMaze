package GameObjects;

import com.badlogic.gdx.graphics.Texture;

import Graphic.Point3D;

public class Stairs extends Box{

	public Stairs(	float AX, float AY, float AZ,
					float ASizeX, float ASizeY, float ASizeZ,
					Texture ATexture
	) {
		super(AX, AY, AZ, ASizeX, ASizeY, ASizeZ, ATexture);
	}

	@Override
	public Direction Intersect(Point3D AStart, Point3D AEnd, float ARadius){
		Direction result = super.Intersect(AStart, AEnd, ARadius);
		
		if (result == Direction.dirX_P || result == Direction.dirZ_P ||
			result == Direction.dirX_N || result == Direction.dirZ_N){
			return Direction.dirY_P;
		}
		return result;
	}
}
