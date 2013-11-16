import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.omg.CORBA.Environment;

import GameObjects.*;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Model;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;




public class First3D_Core implements ApplicationListener, InputProcessor
{
	private float FDeltaTime;
	
	private Camera cam;
	
	//texture stuff
	FloatBuffer texCoordBuffer;
	Texture tex;
	String textureImage = "blackbrick.png";
	
	//Model stuff
	Player player;
	
	private GameObject FObjects[];
	private byte FGameState[] = new byte[]{0,3,
			//61,-43,-106,-92,65,29,5,53,66,7,6,47
			0,0,0,0, 0,0,0,0, 0,0,0,0,
			0,0,0,0, 0,0,0,0, 0,0,0,0,    0,

			// corner
			65,37,95,-110,64,-109,-8,33,65,-17,-36,114,
			0,0,0,0, 0,0,0,0, 0,0,0,0,    0,
			
			//
			65,34,98,-49,64,-115,29,23,66,11,-73,31,
			0,0,0,0, 0,0,0,0, 0,0,0,0,    1
			
	}; // TODO make test

	private void NextLevel(){
		int i = 0;
		
		FObjects = new GameObject[40];
		
		// ground
		FObjects[i++] = new Box(0,0,-30, 50,1,50, 0,"blackbrick.png");
		
		FObjects[i++] = new Box(0,0,30, 50,1,-50, 0,"blackbrick.png");
		
		// edge walls
		FObjects[i++] = new Box(-15f,10,-10, 20,20,10, 0,"blackbrick.png"); //left
		FObjects[i++] = new Box(15f,10,-10, -20,20,10, 0,"blackbrick.png"); //right

		FObjects[i++] = new Box(-15f,10,10, 20,20,-10, 0,"blackbrick.png"); //left
		FObjects[i++] = new Box(15f,10,10, -20,20,-10, 0,"blackbrick.png"); //right
		
		// side edge walls
		FObjects[i++] = new Box(-20f,5,-35, 10,10,40, 0,"blackbrick.png"); //left
		FObjects[i++] = new Box(20f,5,-35, -10,10,40, 0,"blackbrick.png"); //right
		
		FObjects[i++] = new Box(-20f,5,35, 10,10,-40, 0,"blackbrick.png"); //left
		FObjects[i++] = new Box(20f,5,35, -10,10,-40, 0,"blackbrick.png"); //right
		
		// ramp, ground to side wall
		FObjects[i++] = new Box2(-10,4.25f,-20, 15,1,5, 0, 0,0,1, "wood.jpg"); //left
		FObjects[i++] = new Box2(10,4.25f,-20, -15,1,5, 0, 0,0,-1, "wood.jpg"); //right
		
		FObjects[i++] = new Box2(-10,4.25f,17.5f, 15,1,-5, 0, 0,0,1, "wood.jpg"); //left
		FObjects[i++] = new Box2(10,4.25f,17.5f, -15,1,-5, 0, 0,0,-1, "wood.jpg"); //right
		
		// ramp, side wall to edge wall
		FObjects[i++] = new Box2(-22.5f,14.25f,-20, 5,1,15, 0, -1,0,0, "wood.jpg"); //left
		FObjects[i++] = new Box2(22.5f,14.25f,-20, -5,1,15, 0, -1,0,0, "wood.jpg"); //right
		
		FObjects[i++] = new Box2(-22.5f,14.25f,20, 5,1,-15, 0, 1,0,0, "wood.jpg"); //left
		FObjects[i++] = new Box2(22.5f,14.25f,20, -5,1,-15, 0, 1,0,0, "wood.jpg"); //right
		
		// ramp, side wall to flag wall
		FObjects[i++] = new Box2(-10,14.25f,-50, 15,1,10, 0, 0,0,-1, "wood.jpg"); //left
		FObjects[i++] = new Box2(10,14.25f,-50, -15,1,10, 0, 0,0,1, "wood.jpg"); //right
		
		FObjects[i++] = new Box2(10,14.25f,50, -15,1,-10, 0, 0,0,1, "wood.jpg"); //right
		FObjects[i++] = new Box2(-10,14.25f,50, -15,1,10, 0, 0,0,-1, "wood.jpg"); //left
		
		// Flag wall
		FObjects[i++] = new Box(0,10,-50, 10,20,10, 0,"blackbrick.png");
		FObjects[i++] = new Box(0,10,50, 10,20,-10, 0,"blackbrick.png");
		
		//Center bridges
		FObjects[i++] = new Box2(-5,19.5f,-5, 10,1,5, 0, 0,1,0, "wood.jpg");
		FObjects[i++] = new Box2(5,19.5f,5, 5,1,10, 0, 0,-1,0, "wood.jpg");
		
		FObjects[i++] = new Box2(5,19.5f,-5, 10,1,5, 0, 0,-1,0, "wood.jpg");
		FObjects[i++] = new Box2(-5,19.5f,5, 5,1,10, 0, 0,1,0, "wood.jpg");
		
		//ScullFloor
		//finna einhverja fokking skull mynd sem virkar og setja hérna inn
		FObjects[i++] = new ScullFloor(0,0,0, 10,1,10, 0, "wood.jpg");
		
		cam = new Camera(new Point3D(-5.0f, 5.0f, 5.0f), new Point3D(-3.0f, 5.0f, 6.0f), new Vector3D(0.0f, 1.0f, 0.0f));
	}

	
	@Override
	public void create() {

		Gdx.input.setInputProcessor(this);
		

		
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 100.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
		
		//texture start ******************
		
		texCoordBuffer = BufferUtils.newFloatBuffer(48);
		
		texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
								

		texCoordBuffer.rewind();

		
		tex = new Texture(Gdx.files.internal("assets/textures/" + textureImage));

		//texture end ******************
		
		NextLevel();
		
	}
	
	private void update() {
		FDeltaTime = Gdx.graphics.getDeltaTime();

		InputHandler.HandleUserInput(cam, FDeltaTime, false);
	}
	
	private void RenderPlayer(ByteBuffer Abuffer){
		float x = Abuffer.getFloat();
		float y = Abuffer.getFloat();
		float z = Abuffer.getFloat();

		float xr = Abuffer.getFloat();
		float yr = Abuffer.getFloat();
		float zr = Abuffer.getFloat();
		
		int team = Abuffer.get();
		
		Player p = new Player("ship.obj");
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(x, y, z);
		System.out.println(x+":"+y+":"+z);
		p.drawPlayer();
		Gdx.gl11.glPopMatrix();
	}
		
	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		cam.setModelViewMatrix();
		
		// Draw objects!
		// limit view to area around the player 8X8X8
		for (int i=0; i<FObjects.length; i++){
			if (FObjects[i] != null)
				FObjects[i].Render();
		}
		
		// Draw State
		byte[] state = FGameState;
		ByteBuffer buf = ByteBuffer.wrap(state);
		if (buf.get()>0){
			// TODO: render bubble powerup
		}
		int players = buf.get();
		for (int i=0; i<players; i++){
			RenderPlayer(buf);
		}
		
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
			System.out.println(cam);
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
