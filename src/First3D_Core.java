import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.omg.CORBA.Environment;

import GameObjects.*;
import Multiplayer.StateClient;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	Player FPlayer;
	private float FVelocity;
	private boolean FGrounded;
	
	private StateClient FClient;
	
	private GameObject FObjects[];

	private void NextLevel(){
		int i = 0;
		
		FObjects = new GameObject[80];
		
		// ground
		FObjects[i++] = new Box(0,-1,-30, 			50,2,50,		"blackbrick.png");
		
		FObjects[i++] = new Box(0,-1,30, 			50,2,50,		"blackbrick.png");
		
		// edge walls
		FObjects[i++] = new Box(-15f,10,-10, 		 20,20,10, 		"blackbrick.png"); //left
		FObjects[i++] = new Box(15f,10,-10, 		 20,20,10, 		"blackbrick.png"); //right

		FObjects[i++] = new Box(-15f,10,10, 		 20,20,10, 		"blackbrick.png"); //left
		FObjects[i++] = new Box(15f,10,10, 			 20,20,10, 		"blackbrick.png"); //right
		
		// side edge walls
		FObjects[i++] = new Box(-20f,5,-35, 		 10,10,40, 		"blackbrick.png"); //left
		FObjects[i++] = new Box(20f,5,-35, 			 10,10,40, 		"blackbrick.png"); //right
		
		FObjects[i++] = new Box(-20f,5,35, 			 10,10,40, 		"blackbrick.png"); //left
		FObjects[i++] = new Box(20f,5,35,			 10,10,40, 		"blackbrick.png"); //right
		
		// ramp, ground to side wall
		FObjects[i++] = new Box(-12.5f,2.5f,-20, 	 5,5,10, 		"wood.jpg"); //left
		FObjects[i++] = new Box( 12.5f,2.5f,-20, 	 5,5,10, 		"wood.jpg"); //right
		
		for (int j=1; j<8; j++){
			float x = j*1.25f;
			FObjects[i++] = new Stairs(-5.625f-x,x/2,20,	1.25f,x,10,	"wood.jpg"); //left
			FObjects[i++] = new Stairs( 5.625f+x,x/2,20,	1.25f,x,10,	"wood.jpg"); //right
		}
		
		// ramp, side wall to edge wall
		FObjects[i++] = new Box(-20f,12.5f,-17.5f,	5,5,5, 		"wood.jpg"); //left
		FObjects[i++] = new Box( 20f,12.5f,-17.5f,	5,5,5, 		"wood.jpg"); //right
		
		FObjects[i++] = new Box(-20f,12.5f,17.5f,	5,5,5, 		"wood.jpg"); //left
		FObjects[i++] = new Box( 20f,12.5f,17.5f,	5,5,5, 		"wood.jpg"); //right
		
		// ramp, side wall to flag wall
		FObjects[i++] = new Box( -7.5f,13.75f,-50,	5,2.5f,10,	"wood.jpg"); //left
		FObjects[i++] = new Box(-12.5f,11.25f,-50,	5,2.5f,10,	"wood.jpg"); //left
		FObjects[i++] = new Box(  7.5f,13.75f,-50,	5,2.5f,10,	"wood.jpg"); //right
		FObjects[i++] = new Box( 12.5f,11.25f,-50,	5,2.5f,10,	"wood.jpg"); //right
		
		FObjects[i++] = new Box( -7.5f,13.75f,50,	5,2.5f,10,	"wood.jpg"); //left
		FObjects[i++] = new Box(-12.5f,11.25f,50,	5,2.5f,10,	"wood.jpg"); //left
		FObjects[i++] = new Box(  7.5f,13.75f,50,	5,2.5f,10,	"wood.jpg"); //right
		FObjects[i++] = new Box( 12.5f,11.25f,50,	5,2.5f,10,	"wood.jpg"); //right
		
		// Flag wall
		FObjects[i++] = new Box(0,8.75f,-50,	10,17.5f,10,		"blackbrick.png");
		FObjects[i++] = new Box(0,8.75f, 50,	10,17.5f,10,		"blackbrick.png");
		
		//Center bridges
		FObjects[i++] = new Box2(-5,19.4f,-5, 	10,1,5, 	0, 1,0, "wood.jpg");
		FObjects[i++] = new Box2( 5,19.4f, 5, 	5,1,10, 	0,-1,0,	"wood.jpg");
		
		FObjects[i++] = new Box2( 5,19.5f,-5, 	10,1,5, 	0,-1,0,	"wood.jpg");
		FObjects[i++] = new Box2(-5,19.5f, 5, 	5,1,10, 	0, 1,0,	"wood.jpg");
		
		//ScullFloor
		FObjects[i++] = new Box(0,-1,0,	10,2,10,	"wood.jpg");
		
		//Flags
		FObjects[i++] = new Flag(0,16.5f,-47.5f,	0,0,0,		0); //Flag 1
		FObjects[i++] = new Flag(0,16.5f,47.5f,	180,0,1,	0); //Flag 2
		
		cam = new Camera(new Point3D(0.0f, 15.0f, 30.0f), new Point3D(-3.0f, 5.0f, 6.0f), new Vector3D(0.0f, 1.0f, 0.0f));
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
		
		FPlayer = new Player("ship/ship.obj");
		
		try {
			FClient = new StateClient();
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean CheckCollision(){
		for (int i=0; i<FObjects.length; i++){
			if (FObjects[i] != null && FObjects[i].Intersect(cam.eye, 1.8f)) return true;
		}
		return false;
	}
	
	private void ApplyGravity(){
		if(FGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			FVelocity = 9.8f;
		}
		FGrounded = false;
		
		float a = -19.6f;
		FVelocity += a*FDeltaTime;
		cam.eye.y += 0.5f*a*FDeltaTime*FDeltaTime+FVelocity*FDeltaTime;
	}
	
	private void update() {
		FDeltaTime = Gdx.graphics.getDeltaTime();

		Point3D start = cam.eye.clone();
		
		// TODO only apply gravity if there is no Y-collision
		
		ApplyGravity();
		InputHandler.HandleUserInput(cam, FDeltaTime, true);
	
		if (CheckCollision()) {
			cam.eye.x = start.x;
			cam.eye.y = start.y;
			cam.eye.z = start.z;
			
			FVelocity = 0;
			FGrounded = true;
		}
		
		FClient.UpdatePlayer(cam.eye, (byte) 1); // TODO missing team variable
	}
	
	private void RenderPlayer(ByteBuffer Abuffer){
		float x = Abuffer.getFloat();
		float y = Abuffer.getFloat();
		float z = Abuffer.getFloat();

		float xr = Abuffer.getFloat();
		float yr = Abuffer.getFloat();
		float zr = Abuffer.getFloat();
		
		int team = Abuffer.get();
		
		Gdx.gl11.glPushMatrix();
		Gdx.gl11.glTranslatef(x, y, z);
		Gdx.gl11.glScalef(3f,3f,3f);
		//FPlayer.drawPlayer(); // TODO enable
		Gdx.gl11.glPopMatrix();
	}
		
	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		cam.setModelViewMatrix();
		
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
