package com.company.Connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClientServer {
    public void startServerLoop(int PORT) {
        new Thread(() -> {
            ServerSocket s = null;
            try {
                s = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Сервер слушает порт: " + PORT);
            try {
                while (true) {
                    Socket socket = s.accept();
                    try {
                        new Handler(socket);
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
