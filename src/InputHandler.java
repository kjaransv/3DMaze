import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
	private static final int FSpeed = 10;
	
	public static void HandleUserInput(Camera ACam, float ADeltaTime, boolean AFlightMode){
		
		
		if (AFlightMode){
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
				ACam.pitch(90.0f * ADeltaTime);
			
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
				ACam.pitch(-90.0f * ADeltaTime);
	
			if(Gdx.input.isKeyPressed(Input.Keys.R)) 
				ACam.slide(0.0f, FSpeed * ADeltaTime, 0.0f);
			
			if(Gdx.input.isKeyPressed(Input.Keys.F)) 
				ACam.slide(0.0f, -FSpeed * ADeltaTime, 0.0f);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			ACam.yaw(-180.0f * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
			ACam.yaw(180.0f * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			ACam.slide(0.0f, 0.0f, -FSpeed * ADeltaTime);

		if(Gdx.input.isKeyPressed(Input.Keys.S))
			ACam.slide(0.0f, 0.0f, FSpeed * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			ACam.slide(-FSpeed * ADeltaTime, 0.0f, 0.0f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			ACam.slide(FSpeed * ADeltaTime, 0.0f, 0.0f);
	}
}
