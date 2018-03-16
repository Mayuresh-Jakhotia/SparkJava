import static spark.Spark.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import spark.Spark;

public class Main {

  static Map<Integer, Message> messages = new ConcurrentHashMap<Integer, Message>();
  static AtomicInteger id = new AtomicInteger();

  public static void main(String[] args) {
    setRoutes();
  }
  
  public static void setRoutes() {

    // Basic Hello World endpoint
    get("/hello", (req, res) -> "Hello World");

    // Add a message
    post("/messages", (request, response) -> {
      return addMessage(id.getAndIncrement(), new JSONObject(request.body()));
    });

    // Get message by ID
    get("/messages/:id", (request, response) -> {
      if(Integer.parseInt(request.params(":id")) >= messages.size()) {
        return 404;
      }
      
      Message responseMessage = messages.get(Integer.parseInt(request.params(":id")));
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("id", Integer.parseInt(request.params(":id")));
      jsonObj.put("text", responseMessage.getText());
      jsonObj.put("from", responseMessage.getFrom());
      jsonObj.put("to", responseMessage.getTo());

      return jsonObj;
    });
  }

  public static JSONObject addMessage(int id, JSONObject jsonObj) { 
    jsonObj.put("id", id);
    String text = jsonObj.getString("text");
    String from = jsonObj.getString("from");
    String to = jsonObj.getString("to");    
    messages.putIfAbsent(id, new Message(text, from, to));

    return jsonObj;
  }
    
}