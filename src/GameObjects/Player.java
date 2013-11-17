package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Player {
	
	StillModel Fmodel;
	Texture tex;
	
	public Player(String modelName)
	{
		ObjLoader loader = new ObjLoader();
        Fmodel = loader.loadObj(Gdx.files.internal("assets/data/" + modelName), true);
        
        tex = new Texture(Gdx.files.internal("assets/textures/wood.jpg"));
	}
	
	public void drawPlayer()
	{
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        tex.bind();

        Fmodel.render();
        
		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

	}

}
