package Multiplayer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import GameObjects.Point3D;

public class StateClient{
	class Listen extends Thread{		
		private UDPReceive FUdp;
		private InetAddress FHost;
		
		public Listen() throws SocketException, UnknownHostException{
			FUdp = new UDPReceive(UDPConstants.FBroadcast, UDPConstants.FPort);
			FHost = InetAddress.getLocalHost();
		}
		
		public void run(){
			while (!interrupted()){
				byte[] _data = new byte[1024];
				DatagramPacket packet = FUdp.Receive(_data);
				if (packet != null && !packet.getAddress().equals(FHost)){
					FState = _data;
				}
			}
		}
	}
	
	class Send extends Thread{
		private UDPSend FUdp;
		
		public Send() throws SocketException, UnknownHostException{
			FUdp = new UDPSend(UDPConstants.FBroadcast, UDPConstants.FPort+1);
		}
		
		public void run(){
			try {
				while (!interrupted()){
					FUdp.Send(FPlayer);
	
					sleep(500);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	private Listen FIn;
	private Send FOut;
	private byte[] FState = new byte[1024];
	private byte[] FPlayer;
	
	public StateClient() throws SocketException, UnknownHostException{
		FIn = new Listen();
		FOut = new Send();
	}
	
	public void Stop(){
		FIn.interrupt();
		FOut.interrupt();
	}

	public void UpdatePlayer(Point3D APlayer, byte ATeam){
		ByteBuffer buf = ByteBuffer.allocate(25);
		buf.putFloat(APlayer.x);
		buf.putFloat(APlayer.y);
		buf.putFloat(APlayer.z);
		
		buf.putFloat(0); // TODO: find direction
		buf.putFloat(0);
		buf.putFloat(0);
		
		buf.put(ATeam);
		
		FPlayer = buf.array();
	}
	
	public byte[] GetState(){
		return FState;
	}
}
