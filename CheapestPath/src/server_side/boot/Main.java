package server_side.boot;

import java.io.IOException;
import server_side.MySerialServer;
import server_side.StringReverserClientHandler;
import server_side.Server;


public class Main {
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 0) {
			Server s = new MySerialServer(args[0]);
			s.start(new StringReverserClientHandler());
			s.stop();
		}
		else
			System.out.println("no args");
	}
	

}
