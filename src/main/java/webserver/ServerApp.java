package webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server started on port 80");

        while (true) {
            Socket localSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + localSocket.getRemoteSocketAddress());

            new Thread(()->{
                try {
                    InputStream is = localSocket.getInputStream();
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
                    OutputStream os = localSocket.getOutputStream();
                    if (!command.equalsIgnoreCase("GET")) {
                        String httpResponseHead = """
                                HTTP/1.1 405 Method Not Allowed
                                Server: Simple-web-server-Buddhi
                                Date: %s
                                Content-Type: text/html
                                
                                """.formatted(LocalDateTime.now());
                        os.write(httpResponseHead.getBytes());
                        os.flush();
                        String httpResponseBody = """
                                <!DOCTYPE html>
                                <html>
                                <head>
                                <title>WEB Server | 405 Method Not Allowed</title>
                                </head>
                                <body>
                                    <h1>405 Method Not Allowed </h1>
                                    <h2>DEP Server does not support %s method.</h2>
                                </body>
                                </html>
                                """.formatted(command);
                        os.write(httpResponseBody.getBytes());
                        os.flush();
                    }


                }catch (IOException e) {}





            }).start();








        }
    }
}
