package controllers;

import java.io.*;

import models.Id;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;
    private List<Id> id;

    public TransactionController(MessageController m, IdController j) {
        this.msgCtrl = m;
        this.idCtrl = j;
        this.id = new ArrayList<>();
    }

    public List<Id> getIds() {
        return id;
    }
    public String postId(String idtoRegister, String githubName) {
        Id tid = new Id(idtoRegister, githubName);
        tid = idCtrl.postId(tid);
        return ("Id registered.");
    }

    public String makecall(String url, String get, String s1) {
    if (url.contains("ids")) {
       return idCtrl.getIds().toString();
    }
    else if (url.contains("messages")) {
       return msgCtrl.getMessages().toString();
    }
return null;
    }
}
