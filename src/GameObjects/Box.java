package GameObjects;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Box extends GameObject{
	protected static FloatBuffer FVertexBuffer = CreateBuffer();

	protected float FX, FY, FZ;
	protected float FSizeX, FSizeY, FSizeZ;
	
	protected FloatBuffer texCoordBuffer;
	protected Texture tex;
	
	public static FloatBuffer CreateBuffer(){
		FloatBuffer VertexBuffer = BufferUtils.newFloatBuffer(72);
		VertexBuffer.put(new float[] {-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
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

		VertexBuffer.rewind();
		
		return VertexBuffer;
	}
	
	public Box(float AX, float AY, float AZ, float ASizeX, float ASizeY, float ASizeZ, String textureImage){
		FX = -AX;
		FY = AY;
		FZ = AZ;
		
		FSizeX = ASizeX;
		FSizeY = ASizeY;
		FSizeZ = ASizeZ;
		
		texCoordBuffer = BufferUtils.newFloatBuffer(48);
        texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                                                                        0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f});
        texCoordBuffer.rewind();
        
        tex = new Texture(Gdx.files.internal("assets/textures/" + textureImage));
	}
	
	@Override
	public boolean Intersect(Point3D ALocation, float ARadius){
		return
				FX-FSizeX/2 < ALocation.x+ARadius && FX+FSizeX/2 > ALocation.x-ARadius &&
				FY-FSizeY/2 < ALocation.y+ARadius && FY+FSizeY/2 > ALocation.y-ARadius &&
				FZ-FSizeZ/2 < ALocation.z+ARadius && FZ+FSizeZ/2 > ALocation.z-ARadius;
	}
	
	@Override
	public void Render() {
		Gdx.gl11.glPushMatrix();
		
		Gdx.gl11.glTranslatef(FX, FY, FZ);
		Gdx.gl11.glScalef(FSizeX, FSizeY, FSizeZ);

		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, FVertexBuffer);
		
		// Texture related BEGIN
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
        
        Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
        Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
        tex.bind();

        Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);
        //Texture related END
		
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

		Gdx.gl11.glPopMatrix();
	}

}
