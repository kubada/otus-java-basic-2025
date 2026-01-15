package ru.otus.java.basic.homeworks.lesson_10;

import java.util.Random;
import java.time.Year;

public class Main {
    private static final Random random = new Random();

    // Массивы для случайной генерации
    private static final String[] LAST_NAMES = {
            "Иванов", "Петров", "Сидоров"
    };

    private static final String[] FIRST_NAMES = {
            "Александр", "Пётр", "Андрей"
    };

    private static final String[] MIDDLE_NAMES = {
            "Дмитриевич", "Сергеевич", "Владимирович"
    };

    private static final String[] EMAIL_DOMAINS = {
            "gmail.com", "mail.ru", "yandex.ru"
    };

    // Генерация случайного пользователя
    public static User generateRandomUser() {
        // ФИО
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String middleName = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];

        // Год рождения (1952-2026)
        int birthYear = 1952 + random.nextInt(75);

        // Email: 10 случайных букв + @домен
        char[] emailChars = new char[10];
        for (int i = 0; i < 10; i++) {
            emailChars[i] = (char) ('a' + random.nextInt(26));
        }
        String emailName = new String(emailChars);

        String domain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
        String email = emailName + "@" + domain;

        return new User(lastName, firstName, middleName, birthYear, email);
    }

    static void main(String[] args) {
        // Количество пользователей
        int userCount = 10;

        // Массив пользователей
        User[] users = new User[userCount];

        // Заполняем массив случайными пользователями
        for (int i = 0; i < userCount; i++) {
            users[i] = generateRandomUser();
        }

        // Текущий год
        int currentYear = Year.now().getValue();

        // Выводим информацию только о пользователях старше 40 лет
        for (User user : users) {
            int age = currentYear - user.getBirthYear();

            if (age > 40)
                user.printInfo();
        }
    }
}