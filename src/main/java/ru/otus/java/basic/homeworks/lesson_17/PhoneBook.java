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
     * @throws IllegalArgumentException если номер телефона null или пустой
     */
    public static void add(Person person, String phoneNumber) {
        String fullName = getFullName(person);

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Нельзя добавить пустой номер");
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
     * Ищет и возвращает все номера телефонов для людей, ФИО которых содержит указанную подстроку.
     * Поиск регистронезависимый.
     *
     * @param searchQuery поисковый запрос (часть имени, фамилии или полное ФИО)
     * @return список номеров телефонов найденных людей, или пустой список если никто не найден
     */
    @SuppressWarnings("UnusedReturnValue")
    public static ArrayList<String> find(String searchQuery) {
        ArrayList<String> result = new ArrayList<>();
        boolean found = false;

        for (Map.Entry<String, List<String>> entry : book.entrySet()) {
            if (entry.getKey().toLowerCase().contains(searchQuery.toLowerCase())) {
                found = true;
                System.out.printf("* Найдена запись (по запросу \"%s\"): \"%s\" - %s %n", searchQuery, entry.getKey(), entry.getValue());
                result.addAll(entry.getValue());
            }
        }

        if (!found) {
            System.out.printf("! Записи, содержащие \"%s\", отсутствуют в справочнике %n", searchQuery);
        }

        return result;
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
    public static String getFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName() + " " + person.getMiddleName();
    }
}
