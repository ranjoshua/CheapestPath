package server_side;

import java.util.ArrayList;
import java.util.Map;

public class MatrixMaze<T> implements Searchable {
	State initialState;
	State goalState;
	Map<State, ArrayList<State>> hs;

	public MatrixMaze(State start, State goal, Map<State, ArrayList<State>> hs) {
		this.initialState = start;
		this.goalState = goal;
		this.hs = hs;
	}

	@Override
	public State getInitialState() {
		return initialState;
	}

	@Override
	public State getGoalState() {
		return goalState;
	}

	// @Override
	// public boolean isGoal(State s) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public void setGoalState(State goalState) {
		this.goalState = goalState;
	}

	public void setAllPossibleStates(Map<State, ArrayList<State>> hs) {
		this.hs = hs;
	}

	@Override
	public ArrayList<State> getAllPossibleStates(State s) { // return all PossibleStates from particular State. in other
															// words, it returns the adjacency list of particular state.
		return hs.get(s);
	}

}
