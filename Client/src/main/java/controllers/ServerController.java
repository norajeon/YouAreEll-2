import models.Id;
//import spiffyUrlManipulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;

import static jdk.internal.net.http.HttpRequestImpl.USER_AGENT;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;

public class ServerController() {
    private String rootURL = "http://zipcode.rocks:8085";

    private ServerController svr = new ServerController();

    private ServerController() {}

    public static shared() {
        return svr;
    }

    //jsonstring?
    public String idGet() throws IOException {
        URL obj = new URL("http://zipcode.rocks:8085/ids");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = null;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            in.close();
        }
        return response.toString();


        // url -> /ids/
        // send the server a get with url
        // return json from server
    }
    public String idPost(Id id) {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://zipcode.rocks:8085/ids"))
                .header("first header", "api key")
                .POST(BodyPublisher.ofString(""))




        // url -> /ids/
        // create json from Id
        // request
        // reply
        // return json
    }
    public String idPut(Id id) {
        // url -> /ids/
    }


}

// ServerController.shared.doGet()