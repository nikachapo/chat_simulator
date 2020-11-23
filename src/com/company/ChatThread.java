package com.company;

import com.company.command.Command;
import com.company.model.Message;
import com.company.service.ChatService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatThread extends Thread {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final ChatService chatService;

    public ChatThread(Socket socket, ChatService chatService) throws Exception {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
        this.chatService = chatService;
    }

    @Override
    public void run() {
        boolean finished = false;
        while (!finished) {
            try {
                Command command = (Command) in.readObject();
                switch (command) {
                    case SHOW_MESSAGES:
                        out.writeObject(chatService.getMessages());
                        out.flush();
                        out.reset();
                        break;
                    case SEND_MESSAGE:
                        Message message = (Message) in.readObject();
                        chatService.sendMessage(message);
                        break;
                    case EXIT:
                        out.close();
                        in.close();
                        socket.close();
                        finished = true;
                        break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
