package server_side;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyClientHandler implements ClientHandler { // this ClientHandler should handle MatrixMaze problems.
	Solver solver;
	CacheManager cm;

	public MyClientHandler() {
		this.cm = new FileCacheManager();
	}

	public MyClientHandler(Solver solver, CacheManager cm) {
		this.solver = solver;
		this.cm = cm;
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter out = new PrintWriter(outToClient);
			try {
				String line, problemInput, start, end;
				StringBuilder sbInput = new StringBuilder();
				while (!(line = in.readLine()).equals("end")) // read input problem from client
					sbInput.append(line + "\n");

				start = in.readLine(); // read the startPoint
				end = in.readLine(); // read the endPoint
				sbInput.deleteCharAt(sbInput.length() - 1);
				problemInput = sbInput.toString();

				sbInput.append("\n" + start + "\n" + end);
				if (cm.cacheExist(sbInput.toString())) // First check if solution exist in cache
					out.println(cm.getCache(problemInput));
				else { // there's no solution for this problem, need to generate new Searchable(Graph)
						// and find solution using BFS/DFS/A*/HillClimbing
					SearchableGenerator myGenerator = new MatrixMazePointGenerator();
					Searchable myMatrixMaze = myGenerator.generate(problemInput, start, end);
					solver = new SearchableSolver(new BestFirstSearch());
					String solution = (String) solver.solve(myMatrixMaze);
					cm.addCache(problemInput, solution);
					out.println(solution);
				}
				out.flush();
				in.close();
				out.close();
			} catch (Exception e) {
				e.getStackTrace();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
