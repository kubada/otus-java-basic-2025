package ru.otus.java.basic.homeworks.lesson_20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Клиентское приложение для отправки математических запросов на сервер.
 * Подключается к серверу на localhost:8080 и обменивается сообщениями.
 */
public class Client {
    /**
     * Подключается к серверу и отправляет математические запросы.
     * Формат запроса: "число число операция" (например: "2 2 +")
     */
    /* public и (String[] args) не требуются в Java 25+ */
    static void main() {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.printf("+ подключено к серверу: %s:%d %n", socket.getInetAddress(), socket.getPort());

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            String operations = inputStream.readUTF();
            System.out.printf("* сообщение от сервера: %s %n", operations);

            while (true) {
                System.out.println("> введите сообщение для сервера: ");
                String input = scanner.nextLine();

                outputStream.writeUTF(input);
                outputStream.flush();

                while (true) {
                    String serverMessage = inputStream.readUTF();
                    if (serverMessage.equals("END")) break;
                    System.out.printf("* сообщение от сервера: %s %n", serverMessage);
                }
            }

        } catch (IOException e) {
            System.out.printf("! не удалось подключиться к серверу: %s %n", e);
        }
    }
}
