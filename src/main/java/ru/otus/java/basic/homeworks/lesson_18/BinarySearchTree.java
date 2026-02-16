package ru.otus.java.basic.homeworks.lesson_18;

import java.util.ArrayList;
import java.util.List;

/**
 * Двоичное дерево поиска из отсортированного списка.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SearchTree<T> {
    private final Node<T> root;

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
        }
    }

    /**
     * Создаёт дерево из отсортированного списка.
     *
     * @param sortedList отсортированный список
     */
    public BinarySearchTree(List<T> sortedList) {
        this.root = build(sortedList, 0, sortedList.size() - 1);
    }

    private Node<T> build(List<T> list, int start, int end) {
        if (start > end) {
            return null;
        }

        int middle = start + (end - start) / 2;
        Node<T> node = new Node<>(list.get(middle));

        node.left = build(list, start, middle - 1);
        node.right = build(list, middle + 1, end);

        return node;
    }

    /**
     * Поиск элемента в дереве.
     *
     * @param element элемент для поиска
     * @return найденный элемент или null
     */
    @Override
    public T find(T element) {
        if (root == null) {
            return null;
        }

        Node<T> node = root;

        while (node != null) {
            int comparison = element.compareTo(node.value);

            if (comparison == 0) {
                return node.value;
            } else if (comparison < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }

    /**
     * Возвращает отсортированный список элементов дерева.
     *
     * @return отсортированный список
     */
    @Override
    public List<T> getSortedList() {
        List<T> result = new ArrayList<>();
        traverse(root, result);
        return result;
    }

    private void traverse(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        traverse(node.left, result);
        result.add(node.value);
        traverse(node.right, result);
    }
}
