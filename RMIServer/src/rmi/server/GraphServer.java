package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//import rmi.registery.GraphService;

import registery.GraphService;
import rmi.client.Client;


public class GraphServer  implements GraphService {

	private Graph graph;
	private static AppLogger logger = new AppLogger();
	private ArrayList<Batch> requests;

	public GraphServer() {
		super();
		graph = new Graph("C:\\Users\\makrm\\IdeaProjects\\BigData proj\\RMIShortestPath\\RMIServer\\local_graph.txt");
		requests = new ArrayList<>();
	}

	@Override
	public String excuteBatchOperations(String batch,char algotype) throws RemoteException {
		logger.logInfo("New batch request");
		Batch newRequest = parseBatchRequest(batch);
		String results = newRequest.performAllOperations(algotype);
		requests.add(newRequest);
		logger.logInfo("End batch request");
		return results;
	}

	@Override
	public String getCurrentGraph() throws RemoteException {
		return graph.GetGraphEdges();
	}

	private Batch parseBatchRequest(String batch) {
		String[] operations = batch.split("\n");
		Batch newRequest = new Batch();
		for (String operation : operations) {
			if (operation.equals("F") || operation.equals("f"))
				break;
			String[] parts = operation.split(" ", 3);
			newRequest.addOperation(new Operation(parts[0].charAt(0), Integer.parseInt(parts[1]),
					Integer.parseInt(parts[2]), graph));
		}
		return newRequest;
	}

	public static void main(String[] args) {

		try {
			String name = "GraphService";
			GraphService server = new GraphServer();
			GraphService stub = (GraphService) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1100); // run on local host and on post 1099
			registry.rebind(name, stub);
			logger.logInfo("rebind RMI registry with Graph server on port 1100");

			Client clientThread = new Client();
			clientThread.start();

			Client clientThread2 = new Client();
			clientThread2.start();
//			Client clientThread3 = new Client();
//			clientThread3.start();
//			Client clientThread4= new Client();
//			clientThread4.start();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
