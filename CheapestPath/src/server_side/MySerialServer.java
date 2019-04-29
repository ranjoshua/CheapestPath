package server_side;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {
	private int port;
	private volatile boolean stop;

	public MySerialServer(int port) {
		this.port = port;
		stop = false;
	}

	public MySerialServer(String port) {
		this.port = Integer.parseInt(port);
	}

	@Override
	public void open(int port, ClientHandler c) throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);
		while (!stop) {
			try {
				Socket aClient = server.accept();
				try {
					InputStream in = aClient.getInputStream();
					OutputStream out = aClient.getOutputStream();
					c.handleClient(in, out);
					in.close();
					out.close();
					aClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SocketTimeoutException e) {
				e.getCause();
			}
		}
		server.close();
	}

	@Override
	public void stop() {
		this.stop = true;
	}

	public void start(ClientHandler c) {
		new Thread(() -> {
			try {
				open(this.getPort(), c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
