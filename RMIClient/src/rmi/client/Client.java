package rmi.client;

import registery.GraphService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
//import rmi.registery.GraphService;


import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Client extends Thread {
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	long totresp = 0;
	public void run() {
			try {
				System.err.println("ClientID: "+ Thread.currentThread().getId());
				GraphService graphService = this.getGraphService();
				ArrayList<Batch> batches = this.generateRequestsBatch();
				Random randomGenerator = new Random();
				readWriteLock.writeLock().lock();
				readWriteLock.readLock().lock();

				for(Batch batch : batches) {
					long startTime = System.currentTimeMillis();
					String response = graphService.excuteBatchOperations(batch.getOperations());
					long endTime = System.currentTimeMillis();
					long responseTime = endTime-startTime;
					batch.setReponse(response);
					batch.setResponseTime(responseTime);
					this.logInformation(batch);
					int sleepTime = randomGenerator.nextInt(100);
					Thread.sleep(sleepTime);
				}

			} catch (NotBoundException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private GraphService getGraphService() throws RemoteException, NotBoundException {
		String name = "GraphService";
		Registry registry = LocateRegistry.getRegistry("localhost",1100);
		GraphService graphService = (GraphService) registry.lookup(name);
		return graphService;
	}
	
	private ArrayList<Batch> generateRequestsBatch(){
		ArrayList<Batch> batches = new ArrayList<Batch>();
		Random randomGenerator = new Random();

		double writePercentage = 0.6;

		BatchGenerator batchGenerator = new BatchGenerator(writePercentage, 5 , 5);
		int numOfRequests = 3 ; // randomGenerator.nextInt(10)+1;
		for(int i=0;i<numOfRequests;i++) {
			Batch batch = batchGenerator.getReqeust();
			batch.setWritePercentage(writePercentage);
			batches.add(batch);
		}
		
		return batches;
	}
	
	private void logInformation(Batch batch) throws IOException {
		File logFile = new File("log_output.txt");
		if(!logFile.exists()) {
			logFile.createNewFile();
		}
		FileWriter logFileWriter = new FileWriter(logFile , true);
		logFileWriter.write("Clinet ID: "+Thread.currentThread().getId()+" ");
		logFileWriter.write("Request : \n");
		logFileWriter.write(batch.getOperations());
		logFileWriter.write("\n Response : \n");
		logFileWriter.write(batch.getReponse());
		logFileWriter.write("response time  : " + batch.getResponseTime() + "MilliSec\n");
		totresp += batch.getResponseTime();
		logFileWriter.write("total = "+totresp);
//		System.out.println("Client ID: "+Thread.currentThread().getId()+ " res= "+ request.getResponseTime());

//		System.out.println("Client ID: "+Thread.currentThread().getId()+ " tot= "+ totresp );
		logFileWriter.write("-------------------------------\n");
		logFileWriter.close();
	}

}
