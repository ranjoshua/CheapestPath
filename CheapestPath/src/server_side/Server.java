package server_side;

public interface Server {
	public void open(int port, ClientHandler c) throws Exception;

	public void stop();

	public void start(ClientHandler ch);
}
