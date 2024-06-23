package server;

import java.util.HashMap;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import logic.Client_info;
import logic.myfile;
import logic.sqlmessage;
import messageProcessor.FileHandler;
import messageProcessor.SqlMessageProcessor;
import messageProcessor.StringMessageProcessor;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import gui.ServerGUI;

public class CemsServer extends AbstractServer  {
    private InetAddress ip;
    public static HashMap<String, Client_info> Client_info = new HashMap<>();
    private ServerGUI serverGUI;
    
    public CemsServer(int port, ServerGUI serverGUI) {
        super(port);
        try {
            this.ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.serverGUI = serverGUI; // Store the reference to the ServerGUI instance
    }  
    @Override
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof String) {
        	new StringMessageProcessor().processMessage((String)msg, client, serverGUI, this);  
        }else if (msg instanceof sqlmessage) {
        	new SqlMessageProcessor().processMessage((sqlmessage)msg,serverGUI, this,client);  
        }else if (msg instanceof myfile) {
        	 myfile fileObject = (myfile) msg;
        	 if(fileObject.getWhatToDo().equals("save")) {
        		String examId = fileObject.getFileName();
         	    String filePath = fileObject.getPath();
         	    byte[] fileData = fileObject.getFileData();
         	    String fileType = fileObject.getType();
         	    
         	    // Save the file using the FileHandler class
         	    boolean saveResult = FileHandler.saveFile(filePath, fileData, fileType,fileObject.getFileName());
         	    
         	   if (saveResult) {
                   System.out.println("File saved successfully");
               } else {
                   System.out.println("Failed to save the file");
               }
         	  try {
				client.sendToClient("");
			} catch (IOException e) {
				e.printStackTrace();
			}
        	 }
        	 else if (fileObject.getWhatToDo().equals("get")) {
        		 byte[] fileData = FileHandler.getFileData(fileObject.getPath());
        		 if (fileData != null) {
                     try {
                         client.sendToClient(fileData);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 } else {
                     System.out.println("File not found");
                 }
        	 }
        	
        }
        
    }
    public String getHost() {
        return ip.getHostName();
    }
    public InetAddress getIP() {
        return ip;
    }
    
    public HashMap<String, Client_info> getClientInfoMap() {
        return Client_info;
    }
}