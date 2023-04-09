package controllers;

import models.Id;

import java.util.ArrayList;
import java.util.List;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController msgController, IdController idController) {
        msgCtrl = msgController;
        idCtrl = idController;
    }

    public MessageController getMsgCtrl() {return msgCtrl;}
    public IdController getIdCtrl() {
        return idCtrl;
    }

    public List<Id> getIds() {
        return null;
    }
    public String postId(String idToRegister, String githubName) {
        Id tid = new Id(idToRegister, githubName);
        tid = idCtrl.postId(tid);
        return ("Id registered.");
    }

    public String makeURLCall(String endPath, String httpMethod, String jsonStr) {
        switch(httpMethod){
            case "GET":
                if(endPath.contains("ids")){
                    ArrayList<Id> ids = idCtrl.getIds();
                    return ids.toString();
                } else if(endPath.contains("messages")){
                    msgCtrl.getMessages();
                }
                break;
            case "POST":
                if(endPath.contains("ids")){
                    String[] idInfo = jsonStr.split(",");
                    return idCtrl.postId(new Id(idInfo[0],idInfo[1])).toString();
                } else if(endPath.contains("messages")){
                    //msgCtrl.getMessages();
                }
                break;
            default:
                break;
        }
        return null;
    }
}