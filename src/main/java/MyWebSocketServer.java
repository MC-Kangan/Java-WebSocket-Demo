


// Import necessary WebSocket classes from javax.websocket and Tyrus server
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.grizzly.utils.Exceptions;

import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

// Define this class as a WebSocket endpoint with the URI "/echo"
@ServerEndpoint("/echo")
public class MyWebSocketServer {

    // static keyword makes the variable a class-level variable, meaning it is shared across all instances of the class. 
    // final keyword means that once the variable is assigned, it cannot be reassigned.
    // A HashSet automatically eliminates duplicate entries, which is ideal here since we only need each username once in ACCEPTED_USERS.
    private static final Set<String> ACCEPTED_USERS = new HashSet<>();
    private static final Gson gson = new Gson();

    // A static block in Java is used to initialize static variables or perform setup that only needs to be done once. 
    // It runs once when the class is loaded, before any instance is created or any static methods are accessed.

    static{
        // Populate with some accepted usernames
        ACCEPTED_USERS.add("Alice");
        ACCEPTED_USERS.add("Bob");
        ACCEPTED_USERS.add("Charlie");
    }

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

            // gson.fromJson(message, JsonObject.class) converts the incoming JSON string into a JsonObject for easy access to fields like user_name.
            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String userName = jsonMessage.has("user_name")? jsonMessage.get("user_name").getAsString(): "";

            // getBasicRemote() allows the server to send messages directly to the client.
            // session.getBasicRemote().sendText("Sever received: " + message);
            
            String response;
            if (ACCEPTED_USERS.contains(userName)) {
                response = "User accepted: " + userName + ", please send your request";
            } else {
                response = "User: " + userName + " not recognised";
            }
            sendJsonResponse(session, response);
        }
        // the catch block will handle it by printing the stack trace, allowing you to debug the issue without crashing the server.
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("Closed: " + session.getId());
    }

    public void sendJsonResponse(Session session, String message){
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("response", message);

        try{
            // Converts the response JsonObject back to JSON format and sends it to the client.
            session.getBasicRemote().sendText(gson.toJson(jsonResponse));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    
}
