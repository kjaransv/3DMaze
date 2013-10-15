import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
	public static void HandleUserInput(Camera ACam, float ADeltaTime, boolean AGodMode){
		if (AGodMode){
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) 
				ACam.pitch(90.0f * ADeltaTime);
			
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
				ACam.pitch(-90.0f * ADeltaTime);
	
			if(Gdx.input.isKeyPressed(Input.Keys.R)) 
				ACam.slide(0.0f, 10.0f * ADeltaTime, 0.0f);
			
			if(Gdx.input.isKeyPressed(Input.Keys.F)) 
				ACam.slide(0.0f, -10.0f * ADeltaTime, 0.0f);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			ACam.yaw(-90.0f * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
			ACam.yaw(90.0f * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.W))
			ACam.slide(0.0f, 0.0f, -10.0f * ADeltaTime);

		if(Gdx.input.isKeyPressed(Input.Keys.S))
			ACam.slide(0.0f, 0.0f, 10.0f * ADeltaTime);
		
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			ACam.slide(-10.0f * ADeltaTime, 0.0f, 0.0f);
		
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			ACam.slide(10.0f * ADeltaTime, 0.0f, 0.0f);
	}
}
