package server_side;

import java.util.ArrayList;
import java.util.HashSet;

public class BestFirstSearch extends CommonSearcher<String> {

	@Override
	public String Search(Searchable s) {
		openList.add(s.getInitialState());
		HashSet<State> closedSet = new HashSet<State>();

		while (!openList.isEmpty()) {
			State n = popOpenList();
			closedSet.add(n);
			if (n.equals(s.getGoalState()))
				return backTracesPointStates(s.getInitialState(), s.getGoalState());

			ArrayList<State> successors = s.getAllPossibleStates(n);
			for (State state : successors) {
				if (!closedSet.contains(state) && !openList.contains(state)) {
					state.setCameFrom(n);
					state.setCurrentMinimalCostFromInitialState(
							state.getCost() + n.getCurrentMinimalCostFromInitialState());
					addToOpenList(state);
				} else if (state.getCurrentMinimalCostFromInitialState() > (state.getCost()
						+ n.getCurrentMinimalCostFromInitialState())) {
					if (!openList.contains(state)) {
						state.setCameFrom(n);
						state.setCurrentMinimalCostFromInitialState(
								state.getCost() + n.getCurrentMinimalCostFromInitialState());
						addToOpenList(state);
					} else {
						State newstate = new State(state.getState(), n, state.getCost(),
								state.getCost() + n.getCurrentMinimalCostFromInitialState());
						removeFromOpenList(state);
						addToOpenList(newstate);
					}
				}
			}
		}
		return null; // Mission Failed
	}

	// END OF CLASS BFS.
}
