package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server started on port 80");

        while (true) {
            Socket localsocket = serverSocket.accept();
            System.out.println("Accepted connection from " + localsocket.getRemoteSocketAddress());

        }
    }
}
