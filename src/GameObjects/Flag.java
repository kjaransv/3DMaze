package GameObjects;

import Graphic.Point3D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Flag extends GameObject{
	
	StillModel Fmodel;
	Texture tex;
	private float Fangle, FrotateX,FrotateY,FrotateZ;
	
	public Flag(float AX, float AY, float AZ, int Aangle, int ArotateX, int ArotateY, int ArotateZ)
	{
		super(AX, AY, AZ, 0, 0, 0); // TODO what size is it?

		Fangle = Aangle;
		FrotateX = ArotateX;
		FrotateY = ArotateY;
		FrotateZ = ArotateZ;
		
		ObjLoader loader = new ObjLoader();
        Fmodel = loader.loadObj(Gdx.files.internal("assets/data/trex/trex.obj"), true);
        
        tex = new Texture(Gdx.files.internal("assets/textures/wood.jpg"));
	}
	
	public void Render()
	{
		Gdx.gl11.glPushMatrix();
		
		Gdx.gl11.glTranslatef(FX, FY, FZ);
		Gdx.gl11.glScalef(0.4f,0.4f,0.4f);
		Gdx.gl11.glRotatef(Fangle, FrotateX, FrotateY, FrotateZ);
		
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        tex.bind();

        Fmodel.render();
        
		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
		Gdx.gl11.glPopMatrix();
	}

	@Override
	public Direction Intersect(Point3D AStart, Point3D AEnd, float ARadius) {
		// TODO Auto-generated method stub
		return Direction.dirNone;
	}

}
