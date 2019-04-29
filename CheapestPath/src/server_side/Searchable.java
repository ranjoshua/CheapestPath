package server_side;

import java.util.ArrayList;

public interface Searchable<T> {
	State<T> getInitialState();

	State<T> getGoalState(); // it's not always good cause might be more than 1 goal state. so we will use
								// isGoal!
	// boolean isGoal(State s);

	ArrayList<State> getAllPossibleStates(State s);
}
