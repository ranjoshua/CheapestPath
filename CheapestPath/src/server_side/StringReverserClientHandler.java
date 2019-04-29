package server_side;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StringReverserClientHandler implements ClientHandler {
	private Solver solver; // I'm going to use lambda expression for solver implementation, because it's
							// easier than creating special StringBuilder class that extend solver.
	private CacheManager cm;

	public StringReverserClientHandler() {
		this.cm = new FileCacheManager();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			try {
				String line;
				while (!(line = in.readLine()).equals("end")) {
					if (cm.cacheExist(line))
						out.println(cm.getCache(line));
					else {
						Solver<String, String> solver = (s) -> new StringBuilder(s).reverse().toString();
						String sol = solver.solve(line);
						cm.addCache(line, sol);
						out.println(sol);
					}
					out.flush();
				}
				in.close();
				out.close();
			} catch (Exception e) {
				e.getStackTrace();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public Solver<?, ?> getSolver() {
		return solver;
	}

	public void setSolver(Solver<?, ?> sol) {
		this.solver = sol;
	}

	public CacheManager getCm() {
		return cm;
	}

	public void setCm(CacheManager cm) {
		this.cm = cm;
	}

}
