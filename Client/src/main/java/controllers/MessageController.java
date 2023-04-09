package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import models.Id;
import models.Message;

public class MessageController {

    private HashSet<Message> messagesSeen;
    // why a HashSet??
    public MessageController() {
        this.messagesSeen = new HashSet<>();
    }


    public ArrayList<Message> getMessages() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://zipcode.rocks:8085/messages"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
//functions that would match every endpoint (messages, ids)
        //once json is sent to endpoint, have to catch it and return it back

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return parse(result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Message> parse (String a) {
        JSONArray myObject = new JSONArray(a);
        Iterator<Object> it = myObject.iterator();
        ArrayList<Message> msg = new ArrayList<>();
        while (it.hasNext()) {
            JSONObject json = new JSONObject(it.next().toString());
            String message = json.getString("message");
            String id = json.getString("fromid");
            String toId = json.getString("toid");
            msg.add(new Message(message, id, toId));
        }
        return msg;
    }


    public ArrayList<Message> getMessagesForId(Id Id) {
        return null;
    }
    public Message getMessageForSequence(String seq) {
        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        return null;
    }
 
}