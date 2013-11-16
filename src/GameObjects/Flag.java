package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Flag extends GameObject{
	
	StillModel Fmodel;
	Texture tex;
	private float Fx,Fy,Fz,Fangle, FrotateX,FrotateY,FrotateZ;
	
	public Flag(float AX, float AY, float AZ, int Aangle, int ArotateX, int ArotateY, int ArotateZ)
	{
		Fx = AX;
		Fy = AY;
		Fz = AZ;
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
		
		Gdx.gl11.glTranslatef(Fx, Fy, Fz);
		Gdx.gl11.glScalef(0.4f,0.4f,0.4f);
		Gdx.gl11.glRotatef(Fangle, FrotateX, FrotateY, FrotateZ);
		
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        tex.bind();

        Fmodel.render();
        
		Gdx.gl11.glPopMatrix();
	}

}
