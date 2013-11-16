import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
	private static final int FSpeed = 10;
	
	public static void HandleUserInput(Camera ACam, float ADeltaTime, boolean AFlightMode){
		float y = ACam.eye.y;
		
		ACam.yaw(Gdx.input.getX()-Gdx.graphics.getWidth()/2);
		ACam.pitch(Gdx.graphics.getHeight()/2-Gdx.input.getY());
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
		// rotate
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
			ACam.roll(90 * ADeltaTime);
		if(Gdx.input.isKeyPressed(Input.Keys.E))
			ACam.roll(-90 * ADeltaTime);

		// slide up/down
		if(Gdx.input.isKeyPressed(Input.Keys.R)) 
			ACam.slide(0.0f, FSpeed * ADeltaTime, 0.0f);
		if(Gdx.input.isKeyPressed(Input.Keys.F)) 
			ACam.slide(0.0f, -FSpeed * ADeltaTime, 0.0f);
		
		// slide forward/backwards
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			ACam.slide(0.0f, 0.0f, -FSpeed * ADeltaTime);
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			ACam.slide(0.0f, 0.0f, FSpeed * ADeltaTime);
		
		// slide left/right
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			ACam.slide(-FSpeed * ADeltaTime, 0.0f, 0.0f);
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			ACam.slide(FSpeed * ADeltaTime, 0.0f, 0.0f);
		
		//ACam.eye.y = y;
	}
}
