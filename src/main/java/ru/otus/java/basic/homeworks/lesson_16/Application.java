package ru.otus.java.basic.homeworks.lesson_16;

import java.util.ArrayList;

public class Application {
    static void main() {
        ArrayList<Integer> range = createRange(1, 3);
        System.out.println("createRange: " + range);

        int sum = sumAboveFive(createRange(1, 10));
        System.out.println("sumAboveFive: " + sum);

        ArrayList<Integer> fill = createRange(2, 14);
        fillAllElements(8, fill);
        System.out.println("fillAllElements список ПОСЛЕ: " + fill);

        ArrayList<Integer> increment = createRange(3, 10);
        incrementAllElements(4, increment);
        System.out.println("incrementAllElements список ПОСЛЕ: " + increment);

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Сотрудник 1", 34));
        employees.add(new Employee("Сотрудник 2", 25));
        employees.add(new Employee("Сотрудник 3", 19));

        ArrayList<String> employeeNames = Employee.getEmployeeNames(employees);
        System.out.printf("getEmployeeNames: %s %n", employeeNames);

        ArrayList<Employee> ageGreaterOrEqual = Employee.getAgeGreaterOrEqual(employees, 20);
        System.out.printf("getAgeGreaterOrEqual: %s %n", ageGreaterOrEqual);

        int minAvgAge = 15;
        boolean averageAge = Employee.checkAverageAge(employees, minAvgAge);
        System.out.printf("checkAverageAge: %s %n", averageAge ? "средний возраст сотрудников превышает " + minAvgAge : "средний возраст сотрудников не превышает " + minAvgAge);

        Employee youngestEmployee = Employee.getYoungestEmployee(employees);
        System.out.printf("getYoungestEmployee: %s %n", youngestEmployee);
    }

    /**
     * Создаёт ArrayList по диапазону чисел.
     *
     * @param min минимальное число
     * @param max максимальное число
     * @return ArrayList с набором последовательных значений в указанном диапазоне (min и max включительно, шаг - 1)
     */
    public static ArrayList<Integer> createRange(int min, int max) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = min; i <= max; i += 1) {
            list.add(i);
        }
        return list;
    }

    /**
     * Суммирует все элементы списка, значение которых больше 5.
     *
     * @param list список целых чисел
     * @return сумма элементов, значение которых больше 5
     */
    public static int sumAboveFive(ArrayList<Integer> list) {
        int sum = 0;

        for (int i : list) {
            if (i > 5) {
                sum += i;
            }
        }
        return sum;
    }

    /**
     * Переписывает каждую заполненную ячейку списка указанным числом (number).
     *
     * @param number целое число
     * @param list   ссылка на список
     */
    public static void fillAllElements(int number, ArrayList<Integer> list) {
        System.out.printf("fillAllElements список ДО: %s%n", list);

        for (int i = 0; i < list.size(); i++) {
            list.set(i, number);
        }
    }

    /**
     * Увеличивает каждый элемент списка на указанное число.
     *
     * @param number целое число
     * @param list   ссылка на список
     */
    public static void incrementAllElements(int number, ArrayList<Integer> list) {
        System.out.printf("incrementAllElements список ДО: %s%n", list);

        list.replaceAll(each -> each + number);
    }

}
