package youareell;

import controllers.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class YouAreEll {

    TransactionController transactionController;
    MessageController mm;

    public YouAreEll (TransactionController transactionController) {
        this.transactionController = transactionController;
    }

//    public static void main(String[] args) {
//        // hmm: is this Dependency Injection?
//        YouAreEll urlhandler = new YouAreEll(
//            new TransactionController(
//                new MessageController(), new IdController()
//        ));
//        System.out.println(urlhandler.MakeURLCall("/ids", "GET", ""));
//        System.out.println(urlhandler.MakeURLCall("/messages", "GET", ""));
//    }

//    private String MakeURLCall(String web, String method, String message) {
//        try {
//            URL url = new URL(web);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod(method);
////            con.set
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//    return null;
//    }

    public String get_ids() {
        return transactionController.makeURLCall("/ids", "GET", "");
    }

    public String get_messages() {
        return transactionController.makeURLCall("/messages", "GET", "");
    }


    public String post_ids(String name, String github) {
        String s = String.format("%s,%s",name,github);
        return transactionController.makeURLCall("/ids", "POST", s);
    }



}
