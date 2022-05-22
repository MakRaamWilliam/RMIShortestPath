package rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//import rmi.registery.GraphService;

import registery.GraphService;
import rmi.client.Client;


public class GraphServer  implements GraphService {

	private Graph localGraph;
	private static AppLogger logger = new AppLogger();
	private ArrayList<Batch> requests;
	private static ReadWriteLock readWriteLock;

	public GraphServer() {
		super();
		localGraph = new Graph("C:\\Users\\makrm\\IdeaProjects\\BigData proj\\RMIShortestPath\\RMIServer\\local_graph.txt");
		requests = new ArrayList<>();
		readWriteLock = new ReentrantReadWriteLock();
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
		return localGraph.serializeGraph();
	}

	private Batch parseBatchRequest(String batch) {
		String[] operations = batch.split("\n");
		Batch newRequest = new Batch();
		for (String operation : operations) {
			if (operation.equals("F") || operation.equals("f"))
				break;
			String[] parts = operation.split(" ", 3);
			newRequest.addOperation(new Operation(parts[0].charAt(0), Integer.parseInt(parts[1]),
					Integer.parseInt(parts[2]), localGraph));
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
			logger.logInfo("Server register graph service into RMI registery");

            readWriteLock.writeLock().lock();
			Client clientThread = new Client();
			clientThread.start();
			readWriteLock.writeLock().unlock();
			readWriteLock.writeLock().lock();
			Client clientThread2 = new Client();
			clientThread2.start();
//			Client clientThread3 = new Client();
//			clientThread3.start();
//			Client clientThread4= new Client();
//			clientThread4.start();

			readWriteLock.writeLock().unlock();

		} catch (Exception e) {
			logger.logInfo("GraphServer exception while registering graph service into RMI registery");
			e.printStackTrace();
		}
	}
}
