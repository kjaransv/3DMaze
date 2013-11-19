package GameObjects;

public class Stairs extends Box{

	public Stairs(float AX, float AY, float AZ, float ASizeX, float ASizeY, float ASizeZ, String textureImage) {
		super(AX, AY, AZ, ASizeX, ASizeY, ASizeZ, textureImage);
	}

	@Override
	public boolean Intersect(Point3D ALocation, float ARadius){
		boolean result = super.Intersect(ALocation, ARadius);
		
		if (result){
			if (ALocation.y+ARadius+1.25f>=FY+FSizeY/2){
				//ALocation.y += 0.25f;
				return true;
			}
		}
		return result;
	}
}
