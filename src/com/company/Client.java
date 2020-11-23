package com.company;

import com.company.service.ChatService;
import com.company.command.Command;
import com.company.model.Message;
import com.company.model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class Client {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        Socket socket = new Socket("localhost", 8081);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        System.out.print("შეიყვანეთ მობილურის ნომერი : ");
        String n = scanner.nextLine();

        User user = ChatService.getUserByNumber(n);
        if (user == null) {
            System.out.print("შეიყვანეთ სახელი : ");
            String name = scanner.nextLine();
            user = new User(name, n);
            ChatService.addUser(user);
        }

        while (true) {

            System.out.println("\f1. ჩატის ნახვა");
            System.out.println("2. შეტყობინების გაგზავნა");
            System.out.println("0. გასვლა");

            String option = scanner.nextLine();
            if (option.equals("0")) {
                out.writeObject(Command.EXIT);
                out.close();
                in.close();
                socket.close();
                break;
            }

            switch (option) {
                case "1" :
                    out.writeObject(Command.SHOW_MESSAGES);
                    List<Message> messages = (List<Message>) in.readObject();
                    for (Message m : messages) {
                        System.out.println(m.toString());
                    }
                    break;
                case "2" :
                    System.out.println("დაწერეთ:");
                    String text = scanner.nextLine();
                    Message message = new Message(text, user.getName());
                    out.writeObject(Command.SEND_MESSAGE);
                    out.writeObject(message);
                    out.flush();
                    out.reset();
                    break;
                default:
                    System.out.println("ERROR");
            }
        }
    }
}
