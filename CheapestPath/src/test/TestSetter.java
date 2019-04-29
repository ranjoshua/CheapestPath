package test;

import server_side.BestFirstSearch;
import server_side.FileCacheManager;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import server_side.SearchableSolver;
import server_side.Server;

public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {
		// put the code here that runs your server
		s=new MySerialServer(port); // initialize
		//s.start(new MyClientHandler());
		s.start(new MyClientHandler(new SearchableSolver(new BestFirstSearch()), new FileCacheManager()));
	}

	public static void stopServer() {
		s.stop();
	}
	

}
