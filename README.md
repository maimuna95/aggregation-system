Overview:

This project is an aggregation system using JAVA RMI, which allows multiple clients to retrieve data from one or more content servers while managing failures and ensuring data consistency. The system includes a RESTful API for communication and supports Lamport clocks for synchronization. 

Checklist:
1. Both of the servers are running
2. Client is communicating with the servers
3. Using JSON GET & PUT methods
4. Handling errors

FYI: Couldn't manage to write fully functionable Junit Testcases, that's why didn't add it.

Run and Test the code:
1. Open a terminal, command "rmiregistry"
2. Open another terminal, command "java ContentServer" to run the content server
3. Open another terminal, command "java AggregationServer" to run the aggregation server
4. Open another terminal, command "java Client" to run the client file. Following that, it will ask you what data you want to retrieve. Right after you enter your desired data request, you will be able to see the retrieved data from the content server. 
5. You can run multiple server at the same time

