package server_side;

public interface Searcher<SearchSolution> {
	SearchSolution Search(Searchable s);

	int getNumberOfNodesEvaluated(); // use this function to compare which algorithms is optimal for the specific
										// problem.
}
