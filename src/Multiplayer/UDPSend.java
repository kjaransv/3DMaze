package Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPSend {
	private DatagramSocket FSocket;
	private DatagramPacket FData;
	
	public UDPSend(String AIpAddress, int APort) throws SocketException, UnknownHostException{
		FSocket = new DatagramSocket();
		FData = new DatagramPacket(
			new byte[]{}, 0, InetAddress.getByName(AIpAddress), APort
		);
	}
	
	public void close(){
		FSocket.close();
		FSocket = null;
	}
	
	public void Send(byte[] AData){
		FData.setData(AData, 0, AData.length);
		try {
			FSocket.send(FData);
		} catch (IOException e) {
		}
	}
}
