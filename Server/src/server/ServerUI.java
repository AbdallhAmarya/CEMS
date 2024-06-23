package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.net.UnknownHostException;

import gui.ServerGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {
   private static ServerGUI aFrame;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InvocationTargetException {
        try {
            aFrame = new ServerGUI(); // create ServerGUI
            aFrame.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runServer(String p) throws UnknownHostException {
        int port = 0; // Port to listen on

        try {
            port = Integer.parseInt(p); // Set port to specified value
        } catch (Throwable t) {
            System.out.println("ERROR - Could not connect!");
            return; // Exit the method if the port is invalid
        }

        if (isServerRunning(port)) {
            System.out.println("Server is already running on port " + port);
            return; // Exit the method if the server is already running
        }

        CemsServer sv = new CemsServer(port,aFrame);

        try {
            sv.listen(); // Start listening for connections
            System.out.println("Server started on port " + port);
            System.out.println("Host: " + sv.getHost());
            System.out.println("IP address: " + sv.getIP().getHostAddress());
            System.out.println("Status: Running");
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
    
    private static boolean isServerRunning(int port) {
        try (java.net.ServerSocket socket = new java.net.ServerSocket()) {
            socket.setReuseAddress(true);
            socket.bind(new java.net.InetSocketAddress(port));
            return false;
        } catch (IOException e) {
            return true;
        }
    }

	
}
