package ru.otus.java.basic.homeworks.lesson_17;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PhoneBook {
    private static final Map<String, List<String>> book = new LinkedHashMap<>();

    /**
     * Добавляет номер телефона для указанного человека (если человек уже есть, добавляет в существующий список).
     *
     * @param person      объект Person с данными человека
     * @param phoneNumber номер телефона
     */
    public static void add(Person person, String phoneNumber) {
        String fullName = getFullName(person);

        if (phoneNumber == null || phoneNumber.isBlank()) {
            System.out.println("Нельзя добавить пустой номер");
            return;
        }


        if (book.containsKey(fullName)) {
            book.get(fullName).add(phoneNumber);
        } else {
            ArrayList<String> phoneNumberList = new ArrayList<>();
            phoneNumberList.add(phoneNumber);

            book.put(fullName, phoneNumberList);
        }

        System.out.printf("+ Добавлена запись: \"%s\" - %s %n", fullName, phoneNumber);
    }

    /**
     * Ищет и возвращает все номера телефонов для указанного человека.
     *
     * @param person объект Person с данными человека
     * @return список номеров телефонов для указанного человека, или null если человек не найден
     */
    @SuppressWarnings("UnusedReturnValue")
    public static ArrayList<String> find(Person person) {
        String fullName = getFullName(person);

        if (book.containsKey(fullName)) {
            ArrayList<String> phoneNumbers = new ArrayList<>(book.get(fullName));
            System.out.printf("* Найдена запись: \"%s\" - %s %n", fullName, phoneNumbers);
            return phoneNumbers;
        } else {
            System.out.printf("! Запись \"%s\" отсутствует в справочнике %n", fullName);
            return null;
        }
    }

    /**
     * Проверяет, есть ли указанный номер телефона в справочнике.
     *
     * @param phoneNumber номер телефона
     * @return true, если указанный номер телефона есть в справочнике, иначе false
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean containsPhoneNumber(String phoneNumber) {
        for (List<String> v : book.values()) {
            if (v.contains(phoneNumber)) {
                System.out.printf("* Найден номер телефона: \"%s\"%n", phoneNumber);
                return true;
            }
        }
        System.out.printf("! Номер телефона \"%s\" отсутствует в справочнике %n", phoneNumber);
        return false;
    }

    /**
     * Формирует полное ФИО из объекта Person.
     *
     * @param person объект Person с данными человека
     * @return строку вида "Имя Фамилия Отчество"
     */
    private static String getFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName() + " " + person.getMiddleName();
    }
}
