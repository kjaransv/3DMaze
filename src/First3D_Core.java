import java.awt.Point;
import java.nio.FloatBuffer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

public class First3D_Core implements ApplicationListener, InputProcessor
{
	private FloatBuffer FVertexBuffer;
	private float FDeltaTime;
	
	private Point FGoal;
	private Sphere FSphere;
	private Cell[][][] FMaze;
	private Camera cam;
	
	//texture stuff
	FloatBuffer texCoordBuffer;
	Texture tex;
	String textureImage = "blackbrick.png";
	
	private int FLevel = 5;
	private Sound[] FNextLevel;

	private long asdf; // TODO find a better name
	private void NextLevel(){
		if (FLevel>1) FNextLevel[(int)(Math.random()*FNextLevel.length)].play();
				
		int x = 3*FLevel;
		int y = 2*FLevel;
		int z = 1*FLevel;

		asdf = System.currentTimeMillis();
		
		System.out.println("Level "+ FLevel++);

		// find random location for the player and goal
		// these locations must be dead ends
		//
		/*FGoal = new Point((int)(Math.random()*width)+1, (int)(Math.random()*height)+1);
		
		Point player = new Point((int)(Math.random()*width)+1, (int)(Math.random()*height)+1);
		while (player.distance(FGoal)<2){
			player = new Point((int)(Math.random()*width)+1, (int)(Math.random()*height)+1);
		}*/
		
		FMaze = Cell.kruskalMaze3D(x, y, z);
		//FMaze = Cell.ExampleMaze(x, y, z);
		
		cam = new Camera(new Point3D(0.0f, 3.0f, 2.0f), new Point3D(2.0f, 3.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		cam.eye = new Point3D(0, 0, 0);
		
		FSphere = new Sphere(10, 30);
	}
	
	private Cell GetPointCell(Point3D APoint){
		float offset = 2.5f;
		
		return FMaze[(int)(APoint.x+offset)/5][(int)(APoint.y+offset)/5][(int)(APoint.z+offset)/5];
	}
	
	/*
	private boolean CheckCollision(Point3D AStart, Point3D AEnd){
		// TODO Also check the small edges, north/south edge of east walls and west/east edge of south walls
		
		// add or subtract wall width
		Point3D tmp = AEnd.clone();
		if (tmp.z<AStart.z) tmp.z-= 1.75f; else if (tmp.z>AStart.z) tmp.z+= 1.75f;
		if (tmp.x<AStart.x) tmp.x-= 1.75f; else if (tmp.x>AStart.x) tmp.x+= 1.75f;		
		
		// calculate start and end cell
		Point start = GetPointCoordinates(AStart);
		Point end = GetPointCoordinates(tmp);
		
		// only allow a single cell move
		
		boolean l_e = start.y>0 && (AStart.z+2.5) % 5 < 1.75f;
		boolean h_e = (AStart.z+2.5) % 5 > 3.25f;

		boolean l_s = start.x>0 && (AStart.x+2.5) % 5 < 1.75f;
		boolean h_s = (AStart.x+2.5) % 5 > 3.25f;

		// x<0 EAST
		if (end.x<start.x){
			if ((FMaze[start.x][start.y].EastWall()) ||
				(l_e && FMaze[start.x-1][start.y  ].SouthWall()) ||
			    (h_e && FMaze[start.x-1][start.y+1].SouthWall())){
				AStart.x = start.x*5-0.75f;
				tmp.x = AStart.x;
				tmp.z = AEnd.z;
				if (!CheckCollision(AStart, tmp)) AStart.z = tmp.z;				
				
				return true;
			}
		} else if (end.x>start.x) {
			if ((FMaze[start.x+1][start.y].EastWall()) ||
				(l_e && FMaze[start.x+1][start.y  ].SouthWall()) ||
			    (h_e && FMaze[start.x+1][start.y+1].SouthWall())){
				AStart.x = start.x*5+0.75f;
				tmp.x = AStart.x;
				tmp.z = AEnd.z;
				if (!CheckCollision(AStart, tmp)) AStart.z = tmp.z;
				
				return true;
			}
		}
		// y<0 SOUTH
		if (end.y<start.y){
			if ((FMaze[start.x][start.y].SouthWall()) ||
				(l_s && FMaze[start.x  ][start.y-1].EastWall()) ||
			    (h_s && FMaze[start.x+1][start.y-1].EastWall())){
				AStart.z = start.y*5-0.75f;
				tmp.x = AEnd.x;
				tmp.z = AStart.z;
				if (!CheckCollision(AStart, tmp)) AStart.x = tmp.x;
				
				return true;
			}
		} else if (end.y>start.y) {
			if ((FMaze[start.x][start.y+1].SouthWall()) ||
				(l_s && FMaze[start.x  ][start.y+1].EastWall()) ||
				(h_s && FMaze[start.x+1][start.y+1].EastWall())){
				AStart.z = start.y*5+0.75f;
				tmp.x = AEnd.x;
				tmp.z = AStart.z;
				if (!CheckCollision(AStart, tmp)) AStart.x = tmp.x;
				
				return true;
			}
			//if (l_s && FMaze[start.x-1][start.y+1].SouthWall()) return true;
			//if (h_s && FMaze[start.x+1][start.y+1].SouthWall()) return true;
		} else {
			//if (l_s && FMaze[start.x-1][start.y].SouthWall()) result true;
			//if (h_s && FMaze[start.x+1][start.y].SouthWall()) result true;
		}
		
		return false;
	}*/
		
	@Override
	public void create() {
		FNextLevel = new Sound[]{
				Gdx.audio.newSound(new FileHandle("lvl1.mp3")),
				Gdx.audio.newSound(new FileHandle("lvl2.mp3")),
				Gdx.audio.newSound(new FileHandle("lvl3.mp3"))
		};
		
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

		FVertexBuffer = BufferUtils.newFloatBuffer(72);
		FVertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
									  0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f});

		FVertexBuffer.rewind();
		
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
		Point3D start = cam.eye.clone();
		FDeltaTime = Gdx.graphics.getDeltaTime();
		InputHandler.HandleUserInput(cam, FDeltaTime, true);
		
		/*if (CheckCollision(start, cam.eye)){
			// the collision check modifies the start point in the event of a collision
			cam.eye = start; //TODO 
		}*/
		
		/*Point p = GetPointCoordinates(cam.eye);
		if (p.x==FGoal.x && p.y==FGoal.y){
			NextLevel();
		}*/
	}
	
	private void drawBox() {
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, FVertexBuffer);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		tex.bind();  //Gdx.gl11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		
		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);

		
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);

		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	/*
	private void drawFloor(int AStartX, int AStartY, int AEndX, int AEndY) {
		// set material for the floor
		float[] materialDiffuse = {0.2f, 0.3f, 0.6f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);
		
		// draw floor
		for (int fx=AStartX; fx<=AEndX; fx++){
			for (int fz=AStartY; fz<=AEndY; fz++){
				Gdx.gl11.glPushMatrix();				
				Gdx.gl11.glTranslatef(fx*5, 1.0f, fz*5);

				if (fx==FGoal.x && fz==FGoal.y){
					materialDiffuse = new float[]{0.8f, 0.3f, 0.6f, 1.0f};
					Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

			        Gdx.gl11.glPushMatrix();
			        Gdx.gl11.glTranslatef(0, 3.5f, 0);
			        long tmp = asdf-System.currentTimeMillis();
			        float xxx = (tmp % 1000)*0.001f;
			        
			        Gdx.gl11.glScalef(xxx, xxx, xxx);
			        FSphere.draw();
			        FSphere.toggleDrawLines();

			        Gdx.gl11.glScalef(1.5f, 1.5f, 1.5f);
			        Gdx.gl11.glRotatef(0.05f * tmp, 1, 1, 1);
			        FSphere.draw();

			        FSphere.toggleDrawLines();
			        Gdx.gl11.glPopMatrix();
				}
				
				Gdx.gl11.glScalef(0.95f*5, 0.95f, 0.95f*5);
				drawBox();
				Gdx.gl11.glPopMatrix();
				
				if (fx==FGoal.x && fz==FGoal.y){
					materialDiffuse = new float[]{0.2f, 0.3f, 0.6f, 1.0f};
					Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);
				}
			}
		}
	}
	*/
	private void drawWalls(int AStartX, int AStartY, int AStartZ, int AEndX, int AEndY, int AEndZ){
		// set material for the walls
		float[] materialDiffuse = {0.2f, 0.8f, 0.2f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

		// draw walls		
		for (int x=AStartX; x<AEndX; x++){
			for (int y=AStartY; y<AEndY; y++){
				for (int z=AStartZ; z<AEndZ; z++){
					// draw edges
					if (x==0){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5+2.5f, y*5, z*5);
						Gdx.gl11.glScalef(.25f, 5.25f, 5.25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
					if (y==0){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5, y*5-2.5f, z*5);
						Gdx.gl11.glScalef(5.25f, .25f, 5.25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
					if (z==0){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5, y*5, z*5-2.5f);
						Gdx.gl11.glScalef(5.25f, 5.25f, .25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
					
					// draw walls
					if (FMaze[x][y][z].Wall_X()){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5-2.5f, y*5, z*5);
						Gdx.gl11.glScalef(.25f, 5.25f, 5.25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
					if (FMaze[x][y][z].Wall_Y()){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5, y*5+2.5f, z*5);
						Gdx.gl11.glScalef(5.25f, .25f, 5.25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
					if (FMaze[x][y][z].Wall_Z()){
						Gdx.gl11.glPushMatrix();
						Gdx.gl11.glTranslatef(-x*5, y*5, z*5+2.5f);
						Gdx.gl11.glScalef(5.25f, 5.25f, .25f);
						drawBox();
						Gdx.gl11.glPopMatrix();
					}
				}
			}
		}
	}
	
	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		cam.setModelViewMatrix();
		
		/*
		// Configure light 0
		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		//float[] lightPosition = {0, 10.0f, 15.0f, 1.0f};
		float[] lightPosition = {cam.eye.x, cam.eye.y, cam.eye.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		// Configure light 1
		float[] lightDiffuse1 = {0.5f, 0.5f, 0.5f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

		float[] lightPosition1 = {-5.0f, -10.0f, -15.0f, 1.0f};
		//float[] lightPosition1 = {cam.eye.x, cam.eye.y, cam.eye.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);
		*/
		// Draw objects!
		// limit view to area around the player 20X20
		/*Point player = GetPointCoordinates(cam.eye);
		int x_start = Math.max(player.x-10, 0);
		int y_start = Math.max(player.y-10, 0);
		int x_end = Math.min(player.x+10, FMaze.length-1);
		int y_end = Math.min(player.y+10, FMaze[0].length-1);*/
		System.out.println(GetPointCell(cam.eye));
		//drawFloor(x_start, y_start, x_end, y_end);
		//drawWalls(-1, -1, -1, FMaze.length, FMaze[0].length, FMaze[0][0].length);
		drawWalls(0, 0, 0, FMaze.length, FMaze[0].length, FMaze[0][0].length);
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
