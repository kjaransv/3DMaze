import java.nio.ByteBuffer;

import GameLogic.TPlayer;
import GameObjects.*;
import Graphic.Point3D;
import Graphic.TTextures;
import Multiplayer.StateClient;
import Multiplayer.StateServer;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class First3D_Core implements ApplicationListener, InputProcessor
{
	private StateClient FClient;
	private StateServer FServer;
	
	private GameObject FObjects[];
	private Player FTeam0;
	private Player FTeam1;
	
	private TPlayer FPlayer;
	
	public First3D_Core(StateServer AServer, StateClient AClient, byte ATeam){
		FServer = AServer;
		FClient = AClient;
		FPlayer = new TPlayer(ATeam, new Point3D(0,50,30-60*ATeam));
	}
	
	@Override
	public void create() {
		TTextures.LoadTextures();
		
		Gdx.input.setInputProcessor(this);
	
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 100.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		FTeam0 = new Player(TTextures.Team0);
		FTeam1 = new Player(TTextures.Team1);
		
		FObjects = Map.GenerateMap();
	}
	
	private void update() {
		FPlayer.update(Gdx.graphics.getDeltaTime(), FObjects);
		
		FClient.UpdatePlayer(FPlayer);
	}
	
	private void RenderPlayer(ByteBuffer Abuffer){
		float x = Abuffer.getFloat();
		float y = Abuffer.getFloat();
		float z = Abuffer.getFloat();

		float xr = Abuffer.getFloat();
		float yr = Abuffer.getFloat();
		float zr = Abuffer.getFloat();
		
		byte team = Abuffer.get();
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(x, y, z);
		Gdx.gl11.glScalef(3f,3f,3f);
		if (team == 0) FTeam0.drawPlayer();
		else FTeam1.drawPlayer();
		Gdx.gl11.glPopMatrix();
	}
		
	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		FPlayer.FCam.setModelViewMatrix();
		
		// Draw objects!
		for (int i=0; i<FObjects.length; i++){
			if (FObjects[i] != null)
				FObjects[i].Render();
		}
		
		// Draw State
		ByteBuffer buf = ByteBuffer.wrap(FClient.GetState());
		if (buf.get()>0){
			// TODO: render bubble powerup
		}
		int players = buf.get();
		for (int i=0; i<players; i++){
			RenderPlayer(buf);
		}
	}
	
	private void setFlags()
	{
		
		
	}

	@Override
	public void render() {
		
		update();
		
		display();
	  
	}

	
////////////////////////////
// bunch of empty methods //
////////////////////////////

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		if (arg0==27) {
			if (FServer != null) FServer.Stop();
			if (FClient != null) FClient.Stop();
			
			Gdx.app.exit();
		}
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}
}
