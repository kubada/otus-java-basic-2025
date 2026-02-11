package ru.otus.java.basic.homeworks.lesson_17;

public class Application {
    static void main() {
        // первый человек с одним номером
        Person personOne = new Person("Имя", "Фамилия", "Отчество");
        String personOnePhoneNumberOne = "+79211111111";

        PhoneBook.add(personOne, personOnePhoneNumberOne);
        PhoneBook.find(PhoneBook.getFullName(personOne));
        PhoneBook.containsPhoneNumber(personOnePhoneNumberOne);

        // тот же человек с другим номером
        String personOnePhoneNumberTwo = "+79211111112";

        PhoneBook.add(personOne, personOnePhoneNumberTwo);
        PhoneBook.find(PhoneBook.getFullName(personOne));

        // второй человек
        Person personTwo = new Person("Имя 1", "Фамилия 1", "Отчество 1");
        String personTwoPhoneNumberOne = "+79211111113";

        PhoneBook.add(personTwo, personTwoPhoneNumberOne);
        PhoneBook.find(PhoneBook.getFullName(personTwo));
        PhoneBook.containsPhoneNumber(personTwoPhoneNumberOne);

        // поиск несуществующего человека
        Person personThree = new Person("Имя 2", "Фамилия 2", "Отчество 2");
        PhoneBook.find(PhoneBook.getFullName(personThree));

        // поиск несуществующего номера
        String missingPhoneNumberOne = "+79211111114";
        PhoneBook.containsPhoneNumber(missingPhoneNumberOne);

        // поиск по части фамилии
        PhoneBook.find("Фамилия");

        // поиск по части имени (найдёт обоих людей с "Имя")
        PhoneBook.find("Имя");

        // поиск по несуществующей части имени
        PhoneBook.find("Несуществующий");

        // попытка добавить пустой номер
        try {
            PhoneBook.add(personOne, "");
        } catch (IllegalArgumentException e) {
            System.out.println("! " + e.getMessage());
        }
    }
}
