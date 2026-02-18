package ru.otus.java.basic.homeworks.lesson_20;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс для выполнения математических операций.
 */
public class Calculator {
    /**
     * Выполняет математическую операцию и отправляет результат клиенту.
     *
     * @param a         первое число
     * @param b         второе число
     * @param operation операция (+, -, *, /)
     * @param out       поток для отправки результата
     * @throws IOException если ошибка отправки данных
     */
    public static void calculate(int a, int b, String operation, DataOutputStream out) throws IOException {
        int mathResult = 0;
        boolean isValid = true;
        
        switch (operation) {
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
                    out.writeUTF("!! деление на ноль невозможно");
                    isValid = false;
                } else {
                    mathResult = a / b;
                }
                break;
            default:
                out.writeUTF("!! неизвестная математическая операция");
                isValid = false;
        }

        if (isValid) {
            out.writeUTF("результат математической операции: " + mathResult);
        }
        out.writeUTF("END");
        out.flush();
    }
}
