package Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPReceive {
	private DatagramSocket FSocket;
	private DatagramPacket FData;
	
	public UDPReceive(String AIpAddress, int APort) throws SocketException, UnknownHostException{
		FSocket = new DatagramSocket(APort);
		FData = new DatagramPacket(new byte[]{}, 0);
	}
	
	public void close(){
		FSocket.close();
		FSocket = null;
	}
	
	public DatagramPacket Receive(byte[] AData){
		FData.setData(AData, 0, AData.length);
		try {
			FSocket.receive(FData);
			
			return FData;
		} catch (IOException e) {
			return null;
		}
	}
}
