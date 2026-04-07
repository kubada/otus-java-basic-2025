package ru.otus.java.basic.homeworks.lesson_10;

public class User {
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final int birthYear;
    private final String email;

    public User(String lastName, String firstName, String middleName, int birthYear, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthYear = birthYear;
        this.email = email;
    }

    public void printInfo() {
        System.out.printf("ФИО: %s %s %s%nГод рождения: %d%ne-mail: %s%n%n",
                lastName, firstName, middleName, birthYear, email);
    }

    public int getBirthYear() {
        return birthYear;
    }
}
