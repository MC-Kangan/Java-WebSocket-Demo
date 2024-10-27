import org.glassfish.tyrus.server.Server;
import javax.websocket.DeploymentException;
import java.io.IOException;

public class MyWebSocketSeverApp {
    
    // The main method is static, so it can run without an instance of the class.
    public static void main(String[] args){
        
        // /ws: The context path for the WebSocket server.
        // MyWebSocketServer.class: Specifies the WebSocket endpoint class to handle client connections and messages.

        String host = "localhost";
        int port = 8080;
        String contextPath = "/ws";

        // This line creates an instance of the Server class (from the Tyrus library), which will act as our WebSocket server.
        // Create a server instance with host, port, and endpoint path
        Server server = new Server(host, port, contextPath, null, MyWebSocketServer.class);

        try{
            server.start();
            System.out.println("WebSocket server stated at ws://localhost:8080/ws/echo");
            System.in.read(); // Keeps the server running by waiting for input (i.e., pressing Enter) in the terminal. This prevents the server from terminating immediately.

        }catch (DeploymentException | IOException e){
            e.printStackTrace();
        } finally {
            server.stop();
        }   
    }
}
