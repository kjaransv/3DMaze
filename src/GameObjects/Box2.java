package GameObjects;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Box2 extends Box{
	private int FRotateX, FRotateY, FRotateZ;

	public Box2(float AX, float AY, float AZ, float ASizeX, float ASizeY, float ASizeZ, int rotateX, int rotateY, int rotateZ, String textureImage){
		super(AX, AY, AZ, ASizeX, ASizeY, ASizeZ, textureImage);
		
		FRotateX = rotateX;
		FRotateY = rotateY;
		FRotateZ = rotateZ;
	}
	
	@Override
	public void Render() {
		Gdx.gl11.glPushMatrix();
		
		Gdx.gl11.glTranslatef(FX, FY, FZ);
		Gdx.gl11.glRotatef(45, FRotateX, FRotateY, FRotateZ);
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
