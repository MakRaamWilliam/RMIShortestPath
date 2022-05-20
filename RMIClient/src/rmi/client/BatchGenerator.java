package rmi.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BatchGenerator {
	private ArrayList<String> operations ; 
	private double writingPercentage ;
	private int numOfQueries;
	private int numOfNodes ;
	Random nodeRandomGenerator ;
	Random operRandomGenerator ;
	public BatchGenerator(double writingPercentage , int numOfQueries , int numOfNodes) {
		 this.operations = new ArrayList<String>();
		 this.nodeRandomGenerator = new Random();
		 this.operRandomGenerator = new Random();
		 this.writingPercentage = writingPercentage;
		 this.numOfQueries = numOfQueries;
		 this.numOfNodes = numOfNodes;
	}
	
	public void setWritingPercentage(double writingPercentage) {
		this.writingPercentage = writingPercentage;
	}
	
	public void setNumOfQueries(int numOfQueries) {
		this.numOfQueries = numOfQueries;
	}
	
	public Batch getReqeust() {
		
		constructRequest();
		String parsedRequest = "" ;
		for(String oper : operations) {
			parsedRequest+=oper;
			parsedRequest+="\n";
		}
		parsedRequest+="F";
		return new Batch(parsedRequest , operations.size());
	}
	
	private  void constructRequest() {
		this.operations = new ArrayList<String>();
		for(int i=0;i<numOfQueries;i++) {
			if(i < numOfQueries*writingPercentage) {
				operations.add(generateWriteOperation());
			}else {
				operations.add(generateReadOperation());
			}
		}
        Collections.shuffle(operations);
	}
	
	private String generateTwoNodes() {
		int node1 = nodeRandomGenerator.nextInt(numOfNodes)+1;
		int node2 = nodeRandomGenerator.nextInt(numOfNodes)+1;
		return node1 + " " + node2 ;
	}
	
	private String generateWriteOperation() {
		int isAdd = operRandomGenerator.nextInt(2);
		if(isAdd== 1) {
			return "A " + generateTwoNodes();
		}else {
			return "D " + generateTwoNodes();
		}
	}
	
	private String generateReadOperation() {
		return "Q " + generateTwoNodes();
	}
}

 class Batch {
	private String operations ;
	private int numOfOperations ;
	private long responseTime ;
	private String reponse ;
	private double writePercentage ;
	public Batch(String operations , int numOfOperations) {
		this.operations = operations;
		this.numOfOperations = numOfOperations;
	}
	public String getOperations() {
		return operations;
	}
	public int getNumOfOperations() {
		return numOfOperations;
	}
	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public double getWritePercentage() {
		return writePercentage ;
	}

	public void setWritePercentage(double writePercentage) {
		this.writePercentage = writePercentage;
	}
}
