package controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import models.Id;
import org.json.JSONArray;
import org.json.JSONObject;

public class IdController {
    private String rootURL = "http://zipcode.rocks:8085/ids";
    private HashMap<String, Id> allIds; // githubID , Id
    private ArrayList<Id> ids;
    Id myId;



    public ArrayList<Id> getIds() {
        HttpRequest request = buildRequest("GET","");
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ids = parseJSONtoIDs(response.body());
            return ids;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Id> parseJSONtoIDs(String data){
        JSONArray myObject = new JSONArray(data);
        Iterator<Object> it = myObject.iterator();
        ArrayList<Id> ids = new ArrayList<>();
        while(it.hasNext()) {
            JSONObject json = new JSONObject(it.next().toString());
            String name = json.getString("name");
            String id = json.getString("userid");
            String github = json.getString("github");
            ids.add(new Id(name,github));
        }
        return ids;
    }

    public Id postId(Id id) {
        String jsonString = String.format("{\"userid\" : \"%s\",\"name\" : \"%s\",\"github\" : \"%s\"}",
                "-",id.getName(),id.getGithub());
        if(ids==null){
            ids = getIds();
        }
        if(ids.stream().anyMatch(idd -> idd.getGithub().equals(id.getGithub()))){
            return putId(jsonString);
        } else {
            HttpRequest request = buildRequest("POST",jsonString);
            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject json = new JSONObject(response.body());
                ids = getIds();
                return new Id(json.getString("name"),json.getString("github"));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Id putId(String jsonString) {
        HttpRequest request = buildRequest("PUT",jsonString);
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            ids = getIds();
            return new Id(json.getString("name"),json.getString("github"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpRequest buildRequest(String method, String jsonString){
        return HttpRequest.newBuilder()
                .uri(URI.create(rootURL))
                .method(method,
                        method.contains("GET") ?
                                HttpRequest.BodyPublishers.noBody():
                                HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
    }

}