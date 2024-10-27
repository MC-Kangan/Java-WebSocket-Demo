WebSocket Demo Project
======================

A simple Java WebSocket project to demonstrate server-client communication using `javax.websocket` API. 
This project uses a standalone WebSocket server implemented with Tyrus.

Prerequisites
-------------
- Java (Java SE 8 or later)
- Maven (ensure it's installed and configured in your system’s PATH)
- VS Code (optional, but the setup instructions are tailored for VS Code)

Installing Maven
-------------

If Maven isn’t already installed, use the instructions below based on your operating system:

- **macOS** (using Homebrew):
  ```bash
  brew install maven

After installing, verify the installation with: mvn -v 

Project Structure
-----------------
    my-websocket-project
    ├── src
    │   └── main
    │       └── java
    │           └── MyWebSocketServer.java
    │           └── MyWebSocketServerApp.java
    ├── index.html
    └── pom.xml

- `MyWebSocketServer.java`: Defines WebSocket behavior (connect, message, disconnect) without a main method.
- `MyWebSocketServerApp.java`: Contains the main method, starts the server, and connects the WebSocket server to MyWebSocketServer.
- `index.html`: A simple HTML file for the WebSocket client.
- `pom.xml`: Maven configuration file to manage dependencies and build the project.

Why do you need both MyWebSocketServerApp.java and MyWebSocketServer.java?
==================
`MyWebSocketServer`:
- This class is purely focused on handling WebSocket connections and messages.
- It is annotated with @ServerEndpoint("/echo"), which tells the WebSocket server to use this class as the endpoint where clients can connect.
- This class has methods like @OnOpen, @OnMessage, and @OnClose that define what happens during a WebSocket connection lifecycle, making it specialized for WebSocket functionality.
- WebSocket endpoint classes don’t require a main method because they’re managed by the WebSocket server (in this case, Tyrus). The WebSocket server detects and manages these endpoint classes when it starts up.

`MyWebSocketServerApp`:
- This class is responsible for starting and stopping the WebSocket server.
- It contains the main method, which is the entry point when you run the program. In Java, a main method is required to launch an application, but WebSocket endpoint classes do not use or require a main method.
- It creates and configures the Server instance from Tyrus, specifying that MyWebSocketServer is the endpoint to be used. In this way, it connects the MyWebSocketServer endpoint to a WebSocket server instance.
- By keeping these responsibilities separate, we follow a separation of concerns:
- `MyWebSocketServerApp` specifies `MyWebSocketServer.class` as the endpoint class when creating the Server instance.

In summmary: 
- MyWebSocketServer: Manages WebSocket interactions only.
- MyWebSocketServerApp: Manages the server lifecycle.




Setup Instructions
==================

Step 1: Create the Project Folder and Files
-------------------------------------------

1. Create a New Project Folder in VS Code.
   - Open an empty folder in VS Code and create the structure shown above.

2. Add the Code Files:

Step 2: Build the Project with Maven
-------------------------------------------
- Run the following Maven commands to compile the project and resolve dependencies
    ```bash
    mvn clean install
- After the build is succesful, you are good to go.


Step 3: Run the WebSocket Server
-------------------------------------------
- Use Maven to run the MyWebSocketServer class:
    ```bash
    mvn exec:java -Dexec.mainClass="MyWebSocketServer"
- Server Output:
    The server should start and listen on ws://localhost:8080/ws/echo.

Step 4: Test the WebSocket Client
-------------------------------------------
- Open index.html in a web browser.
- Send a message using the input box and click Send Message.
- You should see the server's response in the responses section of the HTML page.
