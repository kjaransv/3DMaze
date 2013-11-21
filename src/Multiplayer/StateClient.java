package Multiplayer;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import GameLogic.TPlayer;

public class StateClient{
	class Listen extends Thread{		
		private UDPReceive FUdp;
		
		public Listen() throws SocketException, UnknownHostException{
			FUdp = new UDPReceive(UDPConstants.FBroadcast, UDPConstants.FPort);
		}
		
		public void run(){
			while (!interrupted()){
				byte[] _data = new byte[1024];
				DatagramPacket packet = FUdp.Receive(_data);
				if (packet != null){
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
	
					sleep(100);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	private Listen FIn;
	private Send FOut;
	private byte[] FState = new byte[1024];
	private byte[] FPlayer = new byte[25];
	
	public StateClient() throws SocketException, UnknownHostException{
		FIn = new Listen();
		FOut = new Send();
		
		FIn.start();
		FOut.start();
	}
	
	public void Stop(){
		FIn.interrupt();
		FOut.interrupt();
	}

	public void UpdatePlayer(TPlayer APlayer){
		ByteBuffer buf = ByteBuffer.allocate(25);
		buf.putFloat(APlayer.FCam.eye.x);
		buf.putFloat(APlayer.FCam.eye.y);
		buf.putFloat(APlayer.FCam.eye.z);
		
		buf.putFloat(0); // TODO: find direction
		buf.putFloat(0);
		buf.putFloat(0);
		
		buf.put(APlayer.FTeam);
		
		FPlayer = buf.array();
	}
	
	public byte[] GetState(){
		return FState;
	}
}
