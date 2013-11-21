package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Player {
	
	StillModel FModel;
	
	public Player(StillModel AModel)
	{
        FModel = AModel;
	}
	
	public void drawPlayer()
	{
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        FModel.render();
        
		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

	}

}
