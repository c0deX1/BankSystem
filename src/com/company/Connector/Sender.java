package com.company.Connector;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public final class Sender {
    public static void Send(String sendMessage, int PORT) {
        new Thread(() -> {
            try (Socket socket = new Socket("localhost", PORT);
                 DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
                while (!socket.isOutputShutdown()) {
                    oos.writeUTF(sendMessage);
                    oos.flush();
                    break;
                }
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }).start();
    }
}
