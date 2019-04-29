package server_side;

import java.util.ArrayList;
import java.util.HashMap;

public class MatrixMazeStringGenerator implements SearchableGenerator<String, String> {

	private State<String> buildState(String state, String cost) {
		int val = Integer.parseInt(cost);
		return new State<String>(state, val);
	}

	@Override
	public Searchable generate(String convertToSearchable, String startPoint, String goalPoint) {
		int i = 0, j = 0, numCols = 1;
		while (convertToSearchable.charAt(i) != '\n') { // getting number of columns in matrix
			if (convertToSearchable.charAt(i) == ',')
				numCols++;
			i++;
		}
		int numRows = (int) convertToSearchable.chars().filter(c -> c == '\n').count() + 1; // getting number of rows in
																							// matrix

		HashMap<State, ArrayList<State<String>>> hm = new HashMap<>();
		HashMap<String, State> helper = new HashMap<>();
		String[] s1 = convertToSearchable.split("\\W"); // the regex "\W" matches any non-word character. so it will
														// split str by "," and "\n"

		int k = 0;
		State<String> state = null, init = null, goal = null;
		ArrayList<String> neighbors;
		for (i = 0; i < numRows; i++) {
			for (j = 0; j < numCols; j++) {
				String indexes = new String(i + "," + j);
				neighbors = new ArrayList<>();

				if (!helper.containsKey(indexes)) {
					state = buildState(indexes, s1[k]);
					hm.put(state, new ArrayList<State<String>>());
					helper.put(indexes, state);
				} else
					state = helper.get(indexes);

				if (i < numRows - 1) {
					String s = "" + (i + 1) + "," + j;
					neighbors.add(s);
					if (!helper.containsKey(s)) {
						State friend = buildState(s, s1[k + numCols]);
						helper.put(s, friend);
						hm.put(friend, new ArrayList<State<String>>());
					}
				}

				if (i > 0) {
					String s = "" + (i - 1) + "," + j;
					neighbors.add(s);
					if (!helper.containsKey(s)) {
						State friend = buildState(s, s1[k - numCols]);
						helper.put(s, friend);
						hm.put(friend, new ArrayList<State<String>>());
					}
				}

				if (j < numCols - 1) {
					String s = "" + (i) + "," + (j + 1);
					neighbors.add(s);
					if (!helper.containsKey(s)) {
						State friend = buildState(s, s1[k + 1]);
						helper.put(s, friend);
						hm.put(friend, new ArrayList<State<String>>());
					}
				}

				if (j > 0) {
					String s = "" + (i) + "," + (j - 1);
					neighbors.add(s);
					if (!helper.containsKey(s)) {
						State friend = buildState(s, s1[k - 1]);
						helper.put(s, friend);
						hm.put(friend, new ArrayList<State<String>>());
					}
				}

				if (state.getState().equals(startPoint))
					init = state;
				if (state.getState().equals(goalPoint))
					goal = state;

				for (String s : neighbors) {
					State friend = helper.get(s);
					hm.get(state).add(friend);
					// hm.get(friend).add(state);
				}

				k++;
			}
		}
		return new MatrixMaze(init, goal, hm);
	}

}
