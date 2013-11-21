import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.SocketException;
import java.net.UnknownHostException;

import Multiplayer.StateClient;
import Multiplayer.StateServer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopStarter
{
	public static void main(String[] args)
	{
        // Getting the current desktop screen resolution.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();

        // Create Lwjg configuration. This is an alternative way for configuring
        // our applications
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        // Set the screen size to same as the desktop screen size.
        cfg.width = scrnsize.width;
        cfg.height = scrnsize.height;
        cfg.useGL20 = false;

        // Set OpenGL to game mode (full screen.)
        cfg.fullscreen = true;
        
        // Enable vSync
        cfg.vSyncEnabled = true;
        
        boolean host = args.length == 2 && args[1].equals("h");
        byte team = (byte)((args.length == 0) ? 0 : Byte.parseByte(args[0]));
        
        // create server
        StateServer server = null;
		if (host){
			try {
				server = new StateServer();
			} catch (SocketException | UnknownHostException e) {
				e.printStackTrace();
			}
		}

        // create client
		StateClient client = null;
		try {
			client = new StateClient();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
			if (server != null) server.Stop();
		}
        
        new LwjglApplication(new First3D_Core(server, client, team), cfg);
	}
}
