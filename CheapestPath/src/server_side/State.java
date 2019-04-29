package server_side;

import java.util.Objects;

public class State<T> {
	private T state;
	private State<T> cameFrom;
	private double cost;
	private double currentMinimalCostFromInitialState;

	public State(T state) {
		this.state = state;
		this.cameFrom = null;
		this.cost = 0;
		this.currentMinimalCostFromInitialState = 0;
	}

	public State() {
		this.state = null;
		this.cameFrom = null;
		this.cost = 0;
		this.currentMinimalCostFromInitialState = 0;
	}

	public State(T state, double cost) {
		this.state = state;
		this.cameFrom = null;
		this.cost = cost;
		this.currentMinimalCostFromInitialState = cost;
	}

	public State(T state, State<T> cameFrom, double cost, double currentMinimalCostFromInitialState) {
		this.state = state;
		this.cameFrom = cameFrom;
		this.cost = cost;
		this.currentMinimalCostFromInitialState = currentMinimalCostFromInitialState;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}


	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> s) {
		cameFrom = s;
	}

	public void setCost(double c) {
		cost = c;
	}

	public void incCost(double c) {
		cost += c;
	}

	public double getCost() {
		return cost;
	}

	public double getCurrentMinimalCostFromInitialState() {
		return currentMinimalCostFromInitialState;
	}

	public void setCurrentMinimalCostFromInitialState(double c) {
		this.currentMinimalCostFromInitialState = c;
	}

	@Override
	public int hashCode() {
		return Objects.hash(state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		return Objects.equals(state, other.state);
	}

}
