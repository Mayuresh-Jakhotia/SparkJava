import static spark.Spark.*;

import java.util.HashMap;

import org.json.JSONObject;

public class Main {

  static HashMap<Integer, Message> messages = new HashMap<Integer, Message>();

  public static void main(String[] args) {

    // Basic Hello World endpoint
    get("/hello", (req, res) -> "Hello World");

    // Add a message
    post("/messages", (request, response) -> {
      return addMessage(new JSONObject(request.body()));
    });

    // Get message by ID
    get("/messages/:id", (request, response) -> {
      if(Integer.parseInt(request.params(":id")) >= messages.size()) {
        return 404;
      }
      
      Message responseMessage = messages.get(Integer.parseInt(request.params(":id")));
      JSONObject jsonObj = new JSONObject();
      jsonObj.put("id", request.params(":id"));
      jsonObj.put("text", responseMessage.getText());
      jsonObj.put("from", responseMessage.getFrom());
      jsonObj.put("to", responseMessage.getTo());

      return jsonObj;
    });
  }

  public static JSONObject addMessage(JSONObject jsonObj) {  
    int id = messages.size();

    jsonObj.put("id", id);

    String text = jsonObj.getString("text");
    String from = jsonObj.getString("from");
    String to = jsonObj.getString("to");    
    messages.put(id, new Message(text, from, to));

    return jsonObj;
  }
}