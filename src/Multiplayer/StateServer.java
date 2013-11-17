package Multiplayer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class StateServer{
	class Listen extends Thread{
		private static final int MAX_PLAYERS = 40;
		
		private UDPReceive FUdp;
		private InetAddress FHost;
		
		private HashMap<String, Integer> FConnections = new HashMap<String, Integer>();
		
		public Listen() throws SocketException, UnknownHostException{
			FUdp = new UDPReceive(UDPConstants.FBroadcast, UDPConstants.FPort+1);
			FHost = InetAddress.getLocalHost();
		}
		
		public void run(){
			while (!interrupted()){
				byte[] _data = new byte[25];
				DatagramPacket packet = FUdp.Receive(_data);
				if (packet != null /*&& !packet.getAddress().equals(FHost)*/){
					String s = packet.getAddress().getHostName();
					//System.out.println(s);
					Integer i = FConnections.get(s);
					if (i == null){
						i = FConnections.size();
						if (i>MAX_PLAYERS) return;
						FConnections.put(s, i);
						FState[1]++;
					}
					
					int offset = 2+i*25;
					for (int j=0; j<_data.length; j++){
						FState[j+offset] = _data[j];
					}
				}
			}
		}
	}
	
	class Send extends Thread{
		private UDPSend FUdp;
		
		public Send() throws SocketException, UnknownHostException{
			FUdp = new UDPSend(UDPConstants.FBroadcast, UDPConstants.FPort);
		}
		
		public void run(){
			try {
				while (!interrupted()){
					FUdp.Send(FState);
	
					sleep(500);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	private Listen FIn;
	private Send FOut;
	private byte[] FState = new byte[1024];
	
	public StateServer() throws SocketException, UnknownHostException{
		FIn = new Listen();
		FOut = new Send();
		
		FIn.start();
		FOut.start();
	}
	
	public void Stop(){
		FIn.interrupt();
		FOut.interrupt();
	}

}
