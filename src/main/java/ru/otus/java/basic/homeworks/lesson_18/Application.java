package ru.otus.java.basic.homeworks.lesson_18;

import java.util.Arrays;
import java.util.List;

public class Application {
    /* public и (String[] args) не требуются в Java 25+ */
    static void main() {
        // Создаём отсортированный список
        List<Integer> sortedList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Исходный отсортированный список: " + sortedList);

        // Строим дерево из списка
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(sortedList);

        // Тестируем поиск существующих элементов
        System.out.println("\nПоиск существующих элементов:");
        System.out.println("Поиск 5: " + tree.find(5));
        System.out.println("Поиск 1: " + tree.find(1));
        System.out.println("Поиск 10: " + tree.find(10));

        // Тестируем поиск несуществующих элементов
        System.out.println("\nПоиск несуществующих элементов:");
        System.out.println("Поиск 15: " + tree.find(15));
        System.out.println("Поиск 0: " + tree.find(0));

        // Получаем отсортированный список обратно
        System.out.println("\nОтсортированный список из дерева:");
        System.out.println(tree.getSortedList());

        // Пример со строками
        System.out.println("\n--- Пример со строками ---");
        List<String> sortedNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        System.out.println("Исходный список: " + sortedNames);

        BinarySearchTree<String> nameTree = new BinarySearchTree<>(sortedNames);
        System.out.println("Поиск 'Charlie': " + nameTree.find("Charlie"));
        System.out.println("Поиск 'Frank': " + nameTree.find("Frank"));
        System.out.println("Отсортированный список: " + nameTree.getSortedList());
    }
}
