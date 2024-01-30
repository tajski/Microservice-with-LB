# Microservice-with-LB  

Steps to run the microservices and client properly:
1. In terminal, when in /app directory run "uvicorn main:app --reload --port xxxx" with specifying port to listen on (e.g. 1111).
2. Repeat for every instance of web service you want to have, remembering to set other port, which is not already in use (in new terminal).
3. When web services are running, in Main.java put all the ports that are currently running in "ports" list (default: 1111, 2222 and 3333 for services running on corresponding ports) inside method "main".
4. Run Main.java and enjoy!
