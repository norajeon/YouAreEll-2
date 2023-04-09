package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import controllers.IdController;
import controllers.MessageController;
import controllers.TransactionController;
import models.Id;
import models.Message;
import youareell.YouAreEll;

// Simple Shell is a Console view for youareell.YouAreEll.
public class SimpleShell {


    public static void prettyPrint(String output) {
        String[] pretty = output.split(",");
        // yep, make an effort to format things nicely, eh?
        for (String p : pretty) {
            System.out.println(p);
        }
//        System.out.println(output);
    }
    public static void main(String[] args) throws java.io.IOException {
        TransactionController transactionController = new TransactionController(new MessageController(), new IdController());

        YouAreEll urll = new YouAreEll(new TransactionController(new MessageController(), new IdController()));

        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            String[] message = commandLine.split("'");
            List<String> list = new ArrayList<String>();
            List<String> msgList = new ArrayList<>();


            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);

            }

            for (int j = 0; j < message.length; j++) {
                msgList.add(message[j]);
            }

//            System.out.println(msgList);
//            System.out.println(list); //***check to see if list was added correctly***
            history.addAll(list);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.contains("ids") && list.size()==1) {
                    String results = urll.get_ids();
                    SimpleShell.prettyPrint(results);
                    continue;
                }

                if (list.contains("ids") && list.size()==3) {
                    String results = urll.post_ids(list.get(1),list.get(2));
                    SimpleShell.prettyPrint(results);
                    continue;
                }

                // messages
                if (list.contains("messages")&& list.size()==1) {
                    String results = urll.get_messages();
                    SimpleShell.prettyPrint(results);
                    continue;
                }

                //messagesforID: messages foruserID
                if(list.contains("messages") && list.size() == 2) {
                    MessageController msg = new MessageController();
                    ArrayList<Message> results;
                    results = msg.getMessagesForId((new Id(" ", list.get(1))));
                    SimpleShell.prettyPrint(results.toString());
                    continue;
                }

                //sequence: messages userID seq seq#
                //messagesFromFriend: messages myId to FriendId

                if(list.contains("messages") && list.size() > 2) {
                    MessageController msg = new MessageController();
                    ArrayList<Message> results;
                    if (list.get(2).equals("seq")) {
                        Id myId = new Id("", list.get(1));
                        Message res = msg.getMessageForSequence(list.get(3), myId);
                        SimpleShell.prettyPrint(res.toString());
                        continue;
                    }
                    results = msg.getMessagesFromFriend((new Id(" ", list.get(1))), new Id("", list.get(list.size()-1)));
                    SimpleShell.prettyPrint(results.toString());
                    continue;
                }

                //send myID 'message' or
                //send myID 'message' to 'friendID'

                if(list.get(0).equals("send")) {
                    MessageController msg = new MessageController();
                    Id myId = new Id("", list.get(1));
                    Id fromID = new Id("", list.get(list.size()-1));
                    Message ms = new Message(msgList.get(1), list.get(1), list.get(list.size()-1));

                    if (msgList.size() == 2) {
                        fromID.setGithub("");
                        ms.setFromId("");
                    }
                    Message result = msg.postMessage(myId, fromID, ms);
                    SimpleShell.prettyPrint(result.toString());
                    continue;
                }



//                if(list.contains("ids") && list.size() > 1) {
//                    MessageController msg = new MessageController();
//                    Id fromID = list.get(1);
//                    msg.postMessage();
//                    //the msg would be msgList.get(1);
//                }
                // you need to add a bunch more.

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // // wait, wait, what curiousness is this?
                // Process process = pb.start();

                // //obtain the input stream
                // InputStream is = process.getInputStream();
                // InputStreamReader isr = new InputStreamReader(is);
                // BufferedReader br = new BufferedReader(isr);

                // //read output of the process
                // String line;
                // while ((line = br.readLine()) != null)
                //     System.out.println(line);
                // br.close();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //catch ioexception, output appropriate message, resume waiting for input
//            catch (IOException e) {
//                System.out.println("Input Error, Please try again!");
//            }
//             So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }

}