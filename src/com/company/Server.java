package com.company;

import com.company.service.ChatService;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        ChatService chatService = new ChatService();

        ServerSocket serverSocket = new ServerSocket(8081);

        while (true) {
            Socket socket = serverSocket.accept();
            ChatThread chatThread = new ChatThread(socket, chatService);
            chatThread.start();
        }

    }

}
