package GameObjects;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Box extends GameObject{
	protected static FloatBuffer FVertexBuffer = CreateBuffer();
	
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
		super(AX, AY, AZ, ASizeX, ASizeY, ASizeZ);
		
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
	
	public int abc(float APoint_a, float APoint_b, float ARect_a, float ARect_b, float ASize_a, float ASize_b){
		float da = APoint_a-ARect_a;
		float db = APoint_b-ARect_b;
		
		float w2 = ASize_a/2;
		float h2 = ASize_b/2;
		
		float s = db/da;
		float sw2 = s*w2;
		float sh2 = h2/s;
		
		boolean ca = Math.abs(sw2) <= h2;
		boolean cb = Math.abs(sh2) <= w2;
		
		if (ca ^ cb){
			if (ca) {
				if (APoint_a>ARect_a) return 1;
				return 0;
			}
			if (APoint_b>ARect_b) return 3;
			return 2;
		}
		//System.out.println("E1");
		return -10;
	}
	
	@Override
	public Direction Intersect(Point3D AStart, Point3D AEnd, float ARadius){
		boolean intersects =
				FX-FSizeX2 < AEnd.x+ARadius && FX+FSizeX2 > AEnd.x-ARadius &&
				FY-FSizeY2 < AEnd.y+ARadius && FY+FSizeY2 > AEnd.y-ARadius &&
				FZ-FSizeZ2 < AEnd.z+ARadius && FZ+FSizeZ2 > AEnd.z-ARadius;
		if (intersects){
			int xy =  abc(AEnd.x, AEnd.y, FX, FY, FSizeX, FSizeY);
			int yz =  abc(AEnd.y, AEnd.z, FY, FZ, FSizeY, FSizeZ)+2;
			int zx = (abc(AEnd.z, AEnd.x, FZ, FX, FSizeZ, FSizeX)+4) % 6;
			
			// 3 boxes
			// 
			
			// find line
			// what line collides first
			// return that
			
			if (xy == yz) {
				if (yz % 2 == 1) return Direction.dirY_P;
				return Direction.dirY_N;
			} else if (yz == zx) {
				if (zx % 2 == 1) return Direction.dirZ_P;
				return Direction.dirZ_N;
			} else if (zx == xy) {
				if (xy % 2 == 1) return Direction.dirX_P;
				return Direction.dirX_N;
			} else {
				System.out.println("error");
				return Direction.dirNone;
			}
		} else {
			return Direction.dirNone;
		}
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
