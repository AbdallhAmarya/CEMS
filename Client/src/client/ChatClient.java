package client;

import logic.myfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.ChatIF;
import ocsf.client.AbstractClient;

public class ChatClient extends AbstractClient {
    public static boolean ExamCheckingCopies=false;
    public static boolean awaitResponse = false;
    public static boolean checkuser = false;
    public static boolean checkQuestionSave = false;
    public static boolean checkExamSave = false;
    public static boolean isexsit;
    public static boolean sqldone;
    public static boolean ExamLockedStudent=true;
    public static List<Map<String, Object>> resultList = new ArrayList<>();
    public static myfile Myfile;

    // Constructors
    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port);
        openConnection();
    }

    // Instance methods

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        awaitResponse = false;
        if (msg instanceof String) {
            System.out.println("--> handleMessageFromServer");
            String message = (String) msg;
            if (message.contains("loginSuccess")) {
                checkuser = true;
            }
            else if (message.contains("DataExist")) {
                isexsit = true;
            } else if (message.contains("DataNotExist")) {
                isexsit = false;
            } else if (message.contains("SqlOperationSuccss")) {
                sqldone = true;
            } else if (message.contains("SqlOperationfail")) {
                sqldone = false;
            }else if(message.contains("checking Exams for")) {
            	ExamCheckingCopies=true;
            }
        } else if (msg instanceof List) {
            List<?> list = (List<?>) msg;
            if (!list.isEmpty() && list.get(0) instanceof Map) {
                resultList.clear();
                for (Object element : list) {
                    if (element instanceof Map) {
                        Map<String, Object> map = (Map<String, Object>) element;
                        resultList.add(map);
                    }
                }
            }
        } else if (msg instanceof byte[]) {
            byte[] message = (byte[]) msg;
            Myfile = new myfile(null, null, null, null, message);
        }
    }

    /**
     * This method handles all data coming from the UI.
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(Object message) {
        try {
            awaitResponse = true;
            sendToServer(message);
            while (awaitResponse) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
            // Ignore errors on quit
        }
        System.exit(0);
    }
}
