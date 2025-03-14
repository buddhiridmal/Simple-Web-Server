package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server started on port 80");

        while (true) {
            Socket localsocket = serverSocket.accept();
            System.out.println("Accepted connection from " + localsocket.getRemoteSocketAddress());

            new Thread(()->{
                try {
                    InputStream is = localsocket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String commandLine = br.readLine();
                    if (commandLine == null) return;
                    String[] cmdArray = commandLine.split(" ");
                    String command = cmdArray[0];
                    String resourcePath = cmdArray[1];

                    String host = null;
                    String line;
                    while ((line = br.readLine()) != null && !line.isBlank()) {
                        String header = line.split(":")[0].strip();
                        String value = line.substring(line.indexOf(":") + 1).strip();
                        if (header.equalsIgnoreCase("Host")) host = value;
                    }



                }catch (IOException e) {}





            }).start();








        }
    }
}
