package GameObjects;

public abstract class GameObject {	
	public abstract void Render();
	public abstract boolean Intersect(Point3D ALocation, float ARadius);
	//public abstract void hello();
}
