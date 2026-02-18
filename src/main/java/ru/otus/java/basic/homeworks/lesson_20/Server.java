package ru.otus.java.basic.homeworks.lesson_20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Серверное приложение для выполнения математических операций.
 * Принимает от клиентов запросы в формате "число число операция" и возвращает результат.
 */
public class Server {
    /**
     * Запускает сервер на порту 8080 и обрабатывает запросы клиентов.
     * Поддерживаемые операции: +, -, *, /
     */
    /* public и (String[] args) не требуются в Java 25+ */
    static void main() {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.printf("+ сервер запущен: %s:%d %n", server.getInetAddress(), server.getLocalPort());

            while (true) {
                Socket client = server.accept();

                try (DataInputStream inputStream = new DataInputStream(client.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(client.getOutputStream())) {

                    outputStream.writeUTF("Доступные математические операции: [+, -, *, /]. Формат сообщения: '2 2 +'");
                    outputStream.flush();

                    while (true) {
                        try {
                            String userInput = inputStream.readUTF();
                            System.out.printf("> сообщение от клиента: %s (%s:%d)%n",
                                    userInput, client.getInetAddress(), client.getPort());

                            String[] userInputParts = userInput.split(" ");

                            if (userInputParts.length != 3) {
                                outputStream.writeUTF("!! длина сообщения невалидна (!= 3)");
                                outputStream.writeUTF("END");
                                outputStream.flush();
                                continue;
                            }

                            int a;
                            int b;

                            try {
                                a = Integer.parseInt(userInputParts[0]);
                                b = Integer.parseInt(userInputParts[1]);
                            } catch (NumberFormatException e) {
                                outputStream.writeUTF("!! сообщение не содержит цифры");
                                outputStream.writeUTF("END");
                                outputStream.flush();
                                continue;
                            }

                            int mathResult = 0;
                            boolean isValid = true;

                            String mathOperation = userInputParts[2];

                            switch (mathOperation) {
                                case "+":
                                    mathResult = a + b;
                                    break;
                                case "-":
                                    mathResult = a - b;
                                    break;
                                case "*":
                                    mathResult = a * b;
                                    break;
                                case "/":
                                    if (b == 0) {
                                        outputStream.writeUTF("!! деление на ноль невозможно");
                                        isValid = false;
                                    } else {
                                        mathResult = a / b;
                                    }
                                    break;
                                default:
                                    outputStream.writeUTF("!! неизвестная математическая операция");
                                    isValid = false;
                            }

                            if (isValid) {
                                outputStream.writeUTF("результат математической операции: " + mathResult);
                            }
                            outputStream.writeUTF("END");
                            outputStream.flush();


                        } catch (IOException e) {
                            System.out.printf("! Клиент (%s:%d) отключился%n", client.getInetAddress(), client.getPort());
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.printf("! ошибка сервера %s %n", e);
        }
    }
}
