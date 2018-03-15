import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainIT {

  Main main = null;
  HttpURLConnection http;
  URL urlObj;
  
  String baseUrl = "http://localhost:4567/";
  
  @Before
  public void setUp() throws InterruptedException {
    Main.init();
    main = new Main();
    
  }

  @Test
  public void testMessages() throws Exception {
    postMessage();
    getMessage();
  }
  
  public void postMessage() throws Exception {
    String url = baseUrl + "messages";
    
    urlObj = new URL(url);
    http = (HttpURLConnection) urlObj.openConnection();
    http.setRequestMethod("POST");
    http.setRequestProperty("Content-Type", "application/json");
    String body = "{\"text\":\"First Message\",\"from\":\"Mayuresh\",\"to\":\"Eclipse\"}";
    http.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(http.getOutputStream());
    wr.writeBytes(body);
    wr.flush();
    wr.close();

    int responseCode = http.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Post parameters : " + body);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();
    
    JSONObject responseObj = new JSONObject(response.toString());
    assertEquals("First Message", responseObj.get("text"));
    assertEquals("Mayuresh", responseObj.get("from"));
    assertEquals("Eclipse", responseObj.get("to"));
    assertEquals(0, responseObj.get("id"));
  }  

  public void getMessage() throws Exception {
    String url = baseUrl + "messages/0";
    
    urlObj = new URL(url);
    http = (HttpURLConnection) urlObj.openConnection();
    http.setRequestMethod("GET");
    int responseCode = http.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();
    
    JSONObject responseObj = new JSONObject(response.toString());
    assertEquals("First Message", responseObj.get("text"));
    assertEquals("Mayuresh", responseObj.get("from"));
    assertEquals("Eclipse", responseObj.get("to"));
    assertEquals(0, responseObj.get("id"));
  }  
  
  @After
  public void tearDown() throws InterruptedException {
    //Stop the server after integration tests are done
    Main.stop();
  }
  
}