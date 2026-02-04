package ru.otus.java.basic.homeworks.lesson_16;

import java.util.ArrayList;

public class Employee {
    private final String name;
    private final int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Возвращает список имён сотрудников.
     *
     * @param employees список сотрудников
     * @return список имён сотрудников
     */
    public static ArrayList<String> getEmployeeNames(ArrayList<Employee> employees) {
        ArrayList<String> employeeNames = new ArrayList<>();

        for (Employee employee : employees) {
            employeeNames.add(employee.name);
        }
        return employeeNames;
    }

    /**
     * Возвращает список сотрудников, возраст которых больше либо равен указанному аргументу.
     *
     * @param employees список сотрудников
     * @param minAge    минимальный возраст
     * @return список сотрудников, возраст которых больше либо равен указанному аргументу (minAge)
     */
    public static ArrayList<Employee> getAgeGreaterOrEqual(ArrayList<Employee> employees, int minAge) {
        ArrayList<Employee> employeesAgeGreaterOrEqual = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.age >= minAge) {
                employeesAgeGreaterOrEqual.add(employee);
            }
        }

        return employeesAgeGreaterOrEqual;
    }

    /**
     * Проверяет, что средний возраст сотрудников превышает указанный аргумент.
     *
     * @param employees список сотрудников
     * @param minAvgAge минимальный средний возраст (аргумент)
     * @return true - если средний возраст сотрудников превышает аргумент (minAvgAge)
     */
    public static boolean checkAverageAge(ArrayList<Employee> employees, int minAvgAge) {
        int totalAge = 0;

        for (Employee employee : employees) {
            totalAge += employee.age;
        }

        return (double) totalAge / employees.size() > minAvgAge;
    }

    /**
     * Возвращает ссылку на самого молодого сотрудника.
     *
     * @param employees список сотрудников
     * @return ссылка на самого молодого сотрудника
     */
    public static Employee getYoungestEmployee(ArrayList<Employee> employees) {
        Employee youngestSoFar = employees.getFirst();

        for (Employee employee : employees) {
            if (employee.age < youngestSoFar.age) {
                youngestSoFar = employee;
            }
        }

        return youngestSoFar;
    }


    @Override
    public String toString() {
        return "Имя: " + name + ", возраст: " + age + ".";
    }
}

