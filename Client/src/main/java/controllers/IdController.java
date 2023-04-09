package controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

import models.Id;
//
//import static java.lang.VersionProps.build;
//import static org.graalvm.compiler.hotspot.nodes.type.MethodPointerStamp.method;

public class IdController {
    private HashMap<String, Id> allIds;

    Id myId;

    public ArrayList<Id> getIds() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://zipcode.rocks:8085/ids"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();;
        }
        return null;
    }

    public Id postId(Id id) {


        // create json from id
        // call server, get json result Or error
        // result json to Id obj

        return null;
    }

    public Id putId(Id id) {
        return null;
    }
 
}