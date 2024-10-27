


// Import necessary WebSocket classes from javax.websocket and Tyrus server
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;


// Define this class as a WebSocket endpoint with the URI "/echo"
@ServerEndpoint("/echo")
public class MyWebSocketServer {

    @OnOpen
    public void onOpen(Session session){
        // session represents the WebSocket session between the server and a particular client.
        System.out.println("Connected: " + session.getId());
    }


    // Trigger when client sends a message to the websocket server
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Received: " + message);
        try{
            // getBasicRemote() allows the server to send messages directly to the client.
            session.getBasicRemote().sendText("Sever received: " + message);
        }
        // If an IOException occurs (e.g., if the connection is interrupted), 
        // the catch block will handle it by printing the stack trace, allowing you to debug the issue without crashing the server.
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("Closed: " + session.getId());
    }


    
}
