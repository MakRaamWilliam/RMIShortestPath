package rmi.server;

public class Operation {
	private char type;
	private int node1;
	private int node2;
	private Graph graph;
	private int result;

	public Operation(char type, int node1, int node2, Graph graph) {
		this.type = type;
		this.node1 = node1;
		this.node2 = node2;
		this.graph = graph;
	}
	public void perform(char algotype) {
		long startTime = System.nanoTime();
		if (this.type == 'Q' || this.type == 'q') {
			this.result = this.graph.getShortestPath(node1, node2,algotype);
		} else if (this.type == 'A' || this.type == 'a') {
			this.graph.addEdge(node1, node2);
		} else if (this.type == 'D' || this.type == 'd') {
			this.graph.removeEdge(node1, node2);
		} else {
			System.err.println("Error in Operation type");
		}
	}

	public int getQueryResult() {
		return result;
	}

	public char getType() {
		return this.type;
	}

}
