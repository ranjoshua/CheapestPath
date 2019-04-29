package server_side;

public interface Solver<Solution, Problem> {
	public Solution solve(Problem p);
}
