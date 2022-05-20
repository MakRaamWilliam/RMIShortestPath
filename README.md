# RMI Application: Incremental calculation of shortest path in dynamic graphs
## RMI Architecture 
RMI (Remote method invocation) applications often comprise two separate programs, a server and a client. 
- A typical server program creates some remote objects, makes references to these objects accessible, and waits for clients to invoke methods on these objects.
- A typical client program obtains a remote reference to one or more remote objects on a server and then invokes methods on them.
- RMI provides the mechanism by which the server and the client communicate and pass information back and forth. Such an application is sometimes referred to as a distributed object application.
![RMI Architecture](https://www.tutorialspoint.com/java_rmi/images/registry.jpg)

## Code Organization
The application consiste from three parts:
#### RMI Registry
- It contains the common interface which represents the contract between the server and the client.
- The common interface (GraphService) contains two methods:
     - excuteBatchOperations: the client uses this method to invoke batch requests from the server and returns the query results.
     - getCurrentGraph:  the client uses this method to get the current state of the local graph in the server.
#### RMI Server
- Contains the actual implementation of the common interface.
- The classes in this module are
    - **GraphServer**
        - Implements the GraphService interface (common interface).
        - Binds the server to RMI registry to register the common service that the client will use.
        Graph
        - Used to store all information related to the local graph in the server.
        - ontains methods to add edge, remove edge and get the shortest path between two vertices.
    - **Request**
        - Used to track each request coming to the server with all required information like all - - - - Operations in the request, time elapsed to perform the request.
        - Contains a method to invoke all operations in the request.
    - **Operation**
        - Used to track each operation with a request.
        - Contains all information related to an operation like its type, the vertices on which the - - operation will perform, the result of the operation and elapsed time to perform the operation.
        - Contains a method to perform the operation based on its type.
    - **AppLogger**
        - Provide the basic functionality to perform logging in the server.
#### RMI Client
- Contains the implementation of the client side of the application.
- The classes in this module are :
   - **GraphManipulation**
        - Create multiple threads to simulate different numbers of clients 
   - **Client** 
       - Extend thread class in java 
        - Contain the main methods to establish connection with the registry and send the generated requests to the Graphservice.
    - **Request**  
       - Contains information of the request and record response time and his response 
    - **RequestGenerator** 
        - Generate the request which contains sequence of operations based on different values for writingPercentage , numOfOperations and numOfNodes 

## Algorithms
-  used BFS and Dijkstra algorithms to compute the shortest path between two nodes in the graph.

## References
- https://docs.oracle.com/javase/tutorial/rmi/TOC.html 
- https://www.baeldung.com/java-rmi 
- https://www.youtube.com/watch?v=OjXTkgW0wDQ 
- https://stackoverflow.com/questions/464687/running-rmi-server-classnotfound 
- http://www.javacoffeebreak.com/articles/javarmi/javarmi.html#code 


        
