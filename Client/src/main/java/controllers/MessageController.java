package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import models.Id;
import models.Message;

import javax.sound.midi.MidiMessage;

public class MessageController {

    private HashSet<Message> messagesSeen;
    ArrayList<Message> msg;
    // why a HashSet??
    public MessageController() {
        this.messagesSeen = new HashSet<>();
    }

    public static void main(String[] args) {


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
        msg = new ArrayList<>();
        while (it.hasNext()) {
            JSONObject json = new JSONObject(it.next().toString());
            String message = json.getString("message");
            String id = json.getString("fromid");
            String toId = json.getString("toid");
            msg.add(new Message(message, id, toId));
        }
        return msg;
    }


    public ArrayList<Message> getMessagesForId(Id idFor) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://zipcode.rocks:8085/messages"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            ArrayList<Message> idMessages = parse(result);
            idMessages.removeIf(m -> !m.getFromId().equals(idFor.getGithub()));
            return idMessages;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Message getMessageForSequence(String seq) {
        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

//    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, Object> mappy) {
//        StringBuilder builder = new StringBuilder();
//        for (Map.Entry<String, Object> entry : mappy.entrySet()) {
//            if (builder.length() > 0) {
//                builder.append(":");
//            }
//            builder.append(URLEncoder.encode(entry.getKey().toString()));
//            builder.append(" : ");
//            builder.append(URLEncoder.encode(entry.getValue().toString()));
//        }
//        return HttpRequest.BodyPublishers.ofString(builder.toString());
//    }

//    public Message postMessage(Id myId, Id toId, Message msg){
////        String jsonString = String.format("{\"fromId\" :  %s, ))")};
////        HttpRequest request = HttpRequest.newBuilder()
////                .uri(URI.create("http://zipcode.rocks:8085/messages"))
////                .method("POST", HttpRequest.BodyPublishers.ofString(jsonString))
////                .build();
//
//
////        String fromid = String.valueOf(myId);
////        String toID = String.valueOf(toId);
////        String message = String.valueOf(msg);
////        Map<String, Object> mappy = new HashMap<>();
////        mappy.put("fromid", myId);
////        mappy.put("toid", toId);
////        mappy.put("message", msg);
//
//        HttpClient client = HttpClient.newHttpClient();
////
////        HttpRequest request = HttpRequest.newBuilder()
////                .POST(buildFormDataFromMap(mappy))
////                .uri(URI.create("http://zipcode.rocks:8085/messages"))
//////                .method("POST", HttpRequest.BodyPublishers.noBody())
////                .build();
//        HttpResponse<String> response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());;
//            JSONObject json = new JSONObject();
//            String a = json.getString("message");
//            String b = json.getString("fromid");
//            String c = json.getString("toid");
//            Message result = new Message(b, c, a);
//            return result;
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}