package server_side;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MatrixMazePointGenerator implements SearchableGenerator<String, String> {

	private State<Point> buildState(Point indexes, int cost) {
		return new State<Point>(indexes, cost);
	}

	private List<Point> setInitAndGoalPoints(String startPoint, String goalPoint) {
		String[] p;
		int x, y;

		p = startPoint.split(",");
		x = Integer.parseInt(p[0]);
		y = Integer.parseInt(p[1]);
		Point n = new Point(x, y);

		p = goalPoint.split(",");
		x = Integer.parseInt(p[0]);
		y = Integer.parseInt(p[1]);
		Point m = new Point(x, y);

		return Arrays.asList(n, m);
	}

	@Override
	public Searchable generate(String convertToSearchable, String startPoint, String goalPoint) {
		int i = 0, j = 0, numCols = 1;
		while (convertToSearchable.charAt(i) != '\n') { // getting number of columns in matrix.
			if (convertToSearchable.charAt(i) == ',')
				numCols++;
			i++;
		}
		int numRows = (int) convertToSearchable.chars().filter(c -> c == '\n').count() + 1; // getting number of rows in
																							// matrix.

		HashMap<State, ArrayList<State<Point>>> hm = new HashMap<>(); //
		HashMap<Point, State> helper = new HashMap<>(); //
		int[] list = Arrays.stream(convertToSearchable.split("\\W")).mapToInt(Integer::parseInt).toArray(); //
		int k = 0;
		State<Point> state = null, init = null, goal = null; //
		ArrayList<Point> neighbors; //
		for (i = 0; i < numRows; i++) {
			for (j = 0; j < numCols; j++) {
				Point indexes = new Point(i, j);
				neighbors = new ArrayList<>();

				if (!helper.containsKey(indexes)) {
					state = buildState(indexes, list[k]);
					hm.put(state, new ArrayList<State<Point>>());
					helper.put(indexes, state);
				} else
					state = helper.get(indexes);

				Point p = null;
				if (i < numRows - 1) {
					p = new Point(i + 1, j);
					neighbors.add(p);
					if (!helper.containsKey(p)) {
						State friend = buildState(p, list[k + numCols]);
						helper.put(p, friend);
						hm.put(friend, new ArrayList<State<Point>>());
					}
				}

				if (i > 0) {
					p = new Point(i - 1, j);
					neighbors.add(p);
					if (!helper.containsKey(p)) {
						State friend = buildState(p, list[k - numCols]);
						helper.put(p, friend);
						hm.put(friend, new ArrayList<State<Point>>());
					}
				}

				if (j < numCols - 1) {
					p = new Point(i, j + 1);
					neighbors.add(p);
					if (!helper.containsKey(p)) {
						State friend = buildState(p, list[k + 1]);
						helper.put(p, friend);
						hm.put(friend, new ArrayList<State<Point>>());
					}
				}

				if (j > 0) {
					p = new Point(i, j - 1);
					neighbors.add(p);
					if (!helper.containsKey(p)) {
						State friend = buildState(p, list[k - 1]);
						helper.put(p, friend);
						hm.put(friend, new ArrayList<State<Point>>());
					}
				}

				if (indexes.equals(startPoint))
					init = state;
				if (indexes.equals(goalPoint))
					goal = state;

				for (Point n : neighbors) {
					State friend = helper.get(n);
					hm.get(state).add(friend);
				}

				k++;
			}
		}
		List tmp = setInitAndGoalPoints(startPoint, goalPoint);
		init = helper.get(tmp.get(0));
		goal = helper.get(tmp.get(1));
		return new MatrixMaze(init, goal, hm);
	}

}
