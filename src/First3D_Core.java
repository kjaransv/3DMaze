import java.awt.Point;
import java.nio.FloatBuffer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class First3D_Core implements ApplicationListener, InputProcessor
{
	private Cell[][] FMaze;
	private Camera cam;

	private void Reset(){
		FMaze = Cell.ExampleWalls(); // TODO change to randomized maze
		
		cam = new Camera(new Point3D(0.0f, 3.0f, 2.0f), new Point3D(2.0f, 3.0f, 3.0f), new Vector3D(0.0f, 1.0f, 0.0f));
		cam.slide(10, 0, -10); // TODO set random location inside maze, example the initial cell of the maze or some dead end
	}
	
	private Point GetPointCoordinates(Point3D APoint){
		Point result = new Point();
		result.x = (int)(APoint.x+2)/5;
		result.y = (int)(APoint.z+2)/5;
		return result;
	}
			
	private boolean CheckCollision(Point3D AStart, Point3D AEnd){
		// first is there a chance of collision?
		// are there walls in the cells of movement?
		Point start = GetPointCoordinates(AStart);
		Point end = GetPointCoordinates(AEnd);
		
		int min_x, min_y, max_x, max_y;
		
		if (start.x<end.x){
			min_x = start.x;
			max_x = end.x;
		} else {
			min_x = end.x;
			max_x = start.x;
		}
		if (start.y<end.y){
			min_y = start.y;
			max_y = end.y;
		} else {
			min_y = end.y;
			max_y = start.y;
		}
		
		// TODO add wall width to check, could be a collision on cells next to the walls
		for (int x=min_x; x<=max_x; x++){
			for (int y=min_y; y<=max_y; y++){
				if (FMaze[x][y].WestWall() || FMaze[x][y].SouthWall()){
					// yes there is a wall, but is there a collision?
					return true;
				}
			}
		}
		
		return false;
		
		// TODO collision
		// old position, new position
		// does this line intersect any wall
		// TODO retrace or go as far as possible

	}
	
	@Override
	public void create() {
		
		Gdx.input.setInputProcessor(this);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		
		Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 30.0f);

		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		FloatBuffer vertexBuffer = BufferUtils.newFloatBuffer(72);
		vertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
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
		vertexBuffer.rewind();

		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		
		Reset();
	}
	
	private void update() {
		Point3D start = cam.eye.clone();
		float deltaTime = Gdx.graphics.getDeltaTime();
		InputHandler.HandleUserInput(cam, deltaTime, true); // TODO set god mode to false
		
		if (CheckCollision(start, cam.eye)){
			// the collision check modifies the start point in the event of a collision
			cam.eye = start;
		}
		
		Point p = GetPointCoordinates(cam.eye);
		if (p.x==0 || p.y==0){
			return;
		}
	}
	
	private void drawBox() {
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
	}
	
	private void drawFloor(int AStartX, int AStartY, int AEndX, int AEndY) {
		// set material for the floor
		float[] materialDiffuse = {0.2f, 0.3f, 0.6f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);
		
		// draw floor
		for (int fx=AStartX; fx<AEndX; fx++){
			for (int fz=AStartY; fz<AEndY; fz++){
				Gdx.gl11.glPushMatrix();				
				Gdx.gl11.glTranslatef(fx*5, 1.0f, fz*5);
				Gdx.gl11.glScalef(0.95f*5, 0.95f, 0.95f*5);
				drawBox();
				Gdx.gl11.glPopMatrix();
			}
		}
	}
	
	private void drawWalls(int AStartX, int AStartY, int AEndX, int AEndY){
		// set material for the walls
		float[] materialDiffuse = {0.2f, 0.1f, 0.6f, 1.0f};
		Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, materialDiffuse, 0);

		// draw walls		
		for (int i=AStartX; i<AEndX; i++){
			for (int j=AStartY; j<AEndY; j++){
				if (FMaze[i][j].WestWall()){
					Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef(i*5, 6.0f, j*5-2.5f);
					Gdx.gl11.glScalef(5.25f, 10.0f, 0.25f);
					drawBox();
					Gdx.gl11.glPopMatrix();
				} else {
					Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef(i*5, 1.0f, j*5-2.5f);
					Gdx.gl11.glScalef(5.25f, 0.95f, 0.25f);
					drawBox();
					Gdx.gl11.glPopMatrix();
				}
				if (FMaze[i][j].SouthWall()){
					Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef(i*5-2.5f, 6.0f, j*5);
					Gdx.gl11.glScalef(0.25f, 10.0f, 5.25f);
					drawBox();
					Gdx.gl11.glPopMatrix();
				} else {
					Gdx.gl11.glPushMatrix();
					Gdx.gl11.glTranslatef(i*5-2.5f, 1.0f, j*5);
					Gdx.gl11.glScalef(0.25f, 0.95f, 5.25f);
					drawBox();
					Gdx.gl11.glPopMatrix();
				}
			}
		}
	}
	
	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		cam.setModelViewMatrix();
				
		// Configure light 0
		float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse, 0);

		//float[] lightPosition = {this.wiggleValue, 10.0f, 15.0f, 1.0f};
		float[] lightPosition = {cam.eye.x, cam.eye.y+10, cam.eye.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition, 0);

		// Configure light 1
		float[] lightDiffuse1 = {0.5f, 0.5f, 0.5f, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, lightDiffuse1, 0);

		//float[] lightPosition1 = {-5.0f, -10.0f, -15.0f, 1.0f};
		float[] lightPosition1 = {cam.eye.x, cam.eye.y, cam.eye.z, 1.0f};
		Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition1, 0);
		
		// Draw objects!
		// limit view to area around the player 20X20
		Point player = GetPointCoordinates(cam.eye);
		int x_start = Math.max(player.x-10, 0);
		int y_start = Math.max(player.y-10, 0);
		int x_end = Math.min(player.x+10, FMaze.length-1);
		int y_end = Math.min(player.y+10, FMaze[0].length-1);
		
		drawFloor(x_start, y_start, x_end, y_end);
		drawWalls(x_start, y_start, x_end, y_end);
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
		if (arg0 == 27) Reset(); // TODO asd
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
