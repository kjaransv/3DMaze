import java.nio.FloatBuffer;

import GameObjects.*;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

public class First3D_Core implements ApplicationListener, InputProcessor
{
	
	private float FDeltaTime;
	
	private Camera cam;
	
	//texture stuff
	FloatBuffer texCoordBuffer;
	Texture tex;
	String textureImage = "blackbrick.png";
	
	private GameObject FObjects[]; 

	private void NextLevel(){
		int i = 0;
		
		FObjects = new GameObject[40];
		// ground
		FObjects[i++] = new Box(0,0,0, 50,1,50, 1);
		// edge walls
		FObjects[i++] = new Box(-15f,10,20, 20,20,10, 2);
		FObjects[i++] = new Box(15f,10,20, -20,20,10, 2);
		// left wall
		FObjects[i++] = new Box(-20f,5,-5, 10,10,40, 2);
		// right wall
		FObjects[i++] = new Box(20f,5,-5, -10,10,40, 2);
		
		// ramp, ground to side wall
		FObjects[i++] = new Box2(-10,5,10, 10,10,10, 3); //left
		FObjects[i++] = new Box2(10,5,10, -10,10,10, 3); //right
		
		// ramp, side wall to edge wall
		FObjects[i++] = new Box2(-22.5f,15,10, 5,10,10, 3); //left
		FObjects[i++] = new Box2(22.5f,15,10, -5,10,10, 3); //right
		
		// ramp, side wall to flag wall
		FObjects[i++] = new Box2(-10,15,-20, 10,10,10, 3); //left
		FObjects[i++] = new Box2(10,15,-20, -10,10,10, 3); //right
		
		// Flag wall
		FObjects[i++] = new Box(0,10,-20, 10,20,10, 2);
		
		// hornpunktar
		FObjects[i++] = new Box(0,0,0, 1,5,1, 2);
		FObjects[i++] = new Box(-25,0,-25, 1,5,1, 2);
		
		FObjects[i++] = new Box(-25,0,-24, 1,5,1, 2);
		FObjects[i++] = new Box(-25,0,-23, 1,5,1, 2);
		
		FObjects[i++] = new Box(-24,0,-25, 1,5,1, 1);
		FObjects[i++] = new Box(-23,0,-25, 1,5,1, 1);

		cam = new Camera(new Point3D(-5.0f, 5.0f, 5.0f), new Point3D(-3.0f, 5.0f, 6.0f), new Vector3D(0.0f, 1.0f, 0.0f));
	}
		
	@Override
	public void create() {
		Gdx.input.setInputProcessor(this);
		
		/*Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);*/
		
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 30.0f);

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

		InputHandler.HandleUserInput(cam, FDeltaTime, true);
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
		System.out.println(arg0);
		
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		//System.out.println((int)arg0);
		
		if (arg0==27) Gdx.app.exit();
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
