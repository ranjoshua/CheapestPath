package server_side;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public abstract class CommonSearcher<SearchSolution> implements Searcher<SearchSolution> {
	protected PriorityQueue<State> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		openList = new PriorityQueue<State>((a, b) -> Double.compare(a.getCost(), b.getCost())); // queue will prefer
																									// low values.
		evaluatedNodes = 0;
	}

	protected State popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	protected void removeFromOpenList(State s) {
		evaluatedNodes++;
		openList.remove(s);
	}

	protected void addToOpenList(State s) {
		evaluatedNodes++;
		openList.add(s);
	}

	@Override
	public abstract SearchSolution Search(Searchable s);

	@Override
	public int getNumberOfNodesEvaluated() {
		return this.evaluatedNodes;
	}

	protected String backTracesPointStates(State<Point> start, State<Point> goal) { // this methods builds the backTrace
																					// of State<Point>.
		StringBuilder arrows = new StringBuilder();
		int a, b, c, d;
		while (goal.getCameFrom() != null) {
			a = goal.getState().x;
			b = goal.getState().y;
			c = goal.getCameFrom().getState().x;
			d = goal.getCameFrom().getState().y;

			if (c < a)
				arrows.append("Down,");
			else if (c > a)
				arrows.append("Up,");
			else if (d < b)
				arrows.append("Right,");
			else
				arrows.append("Left,");

			goal = goal.getCameFrom();
		}
		List<String> arrowsList = Arrays.asList(arrows.toString().split(","));
		Collections.reverse(arrowsList);
		return String.join(",", arrowsList);
	}

	protected String backTracesStringStates(State<String> start, State<String> goal) { // this methods builds the
																						// backTrace of
																						// State<String>.
		StringBuilder arrows = new StringBuilder();
		String s, p[];
		int x, y, a, b;
		while (goal.getCameFrom() != null) {
			s = (String) goal.getCameFrom().getState(); // father
			p = (String[]) s.toString().split(",");
			x = Integer.parseInt((java.lang.String) p[0]);
			y = Integer.parseInt((java.lang.String) p[1]);

			s = (String) goal.getState();
			p = (String[]) s.toString().split(",");
			a = Integer.parseInt((java.lang.String) p[0]);
			b = Integer.parseInt((java.lang.String) p[1]);

			if (x < a)
				arrows.append("Down,");
			else if (x > a)
				arrows.append("Up,");
			else if (y < b)
				arrows.append("Right,");
			else
				arrows.append("Left,");

			goal = goal.getCameFrom();
		}
		List<String> arrowsList = Arrays.asList(arrows.toString().split(","));
		Collections.reverse(arrowsList);
		return String.join(",", arrowsList);
	}

}
