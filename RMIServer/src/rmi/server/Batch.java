package rmi.server;

import java.util.ArrayList;

public class Batch {
	private long takenTime;
	private ArrayList<Operation> operations;

	public Batch() {
		this.operations = new ArrayList<>();
	}

	public void addOperation(Operation operation) {
		operations.add(operation);
	}

	public String performAllOperations() {
		long startTime = System.nanoTime();
		String queryResults = "";
		for (Operation operation : this.operations) {
			operation.perform();
			if (operation.getType() == 'Q' || operation.getType() == 'q') {
				queryResults += (operation.getQueryResult() + "\n");
			}
		}
		this.takenTime = System.nanoTime() - startTime;
		return queryResults;
	}

	public ArrayList<Operation> getOperations() {
		return this.operations;
	}

	public long getTakenTime() {
		return this.takenTime;
	}
}
