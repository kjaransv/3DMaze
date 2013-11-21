package Graphic;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.wavefront.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.utils.BufferUtils;

public class TTextures {
	public static FloatBuffer FTexCoordBuffer;
	
	public static StillModel Team0;
	public static StillModel Team1;
	
	public static Texture Wall_Wood;
	public static Texture Wall_Stone;
	
	
	public static void LoadTextures(){
		FTexCoordBuffer = BufferUtils.newFloatBuffer(48);
		FTexCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
		FTexCoordBuffer.rewind();
		
		ObjLoader loader = new ObjLoader();
		Team0 = loader.loadObj(Gdx.files.internal("assets/data/ship/ship.obj"), true);
		Team1 = loader.loadObj(Gdx.files.internal("assets/data/ship/ship.obj"), true);
		
		Wall_Wood = new Texture(Gdx.files.internal("assets/textures/wood.jpg"));
		Wall_Stone = new Texture(Gdx.files.internal("assets/textures/blackbrick.png"));
	}
}
