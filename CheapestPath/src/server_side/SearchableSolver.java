package server_side;

// This class created to be an objectAdapter between Searcher&Solver.
public class SearchableSolver<Solution> implements Solver<Solution, Searchable> {
	Searcher searcher;

	public SearchableSolver(Searcher s) {
		this.searcher = s;
	}

	@Override
	public Solution solve(Searchable s) {
		return (Solution) searcher.Search(s);
	}

	public void setSearcher(Searcher s) {
		this.searcher = s;
	}
}
