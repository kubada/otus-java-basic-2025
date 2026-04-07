package ru.otus.java.basic.homeworks.lesson_10;

public class Box {
    private final int width;
    private final int height;
    private final int depth;
    private String color;
    private boolean isOpen;
    private String item;

    public Box(int width, int height, int depth, String color) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = color;
        this.isOpen = false;
        this.item = null;
    }

    public void open() {
        this.isOpen = true;
        System.out.println("* коробка открыта\n");
    }

    public void close() {
        this.isOpen = false;
        System.out.println("* коробка закрыта\n");
    }

    public void repaint(String color) {
        this.color = color;
        System.out.printf("* коробка перекрашена в %s цвет%n%n", color);
    }

    public void printInfo() {
        System.out.printf("Информация о коробке:%n" +
                        "  Ширина: %d%n" +
                        "  Высота: %d%n" +
                        "  Глубина: %d%n" +
                        "  Цвет: %s%n" +
                        "  Состояние: %s%n" +
                        "  Предмет внутри: %s%n%n",
                width, height, depth, color,
                isOpen ? "открыта" : "закрыта",
                item != null ? item : "пусто");
    }

    public void put(String newItem) {
        if (!isOpen) {
            System.out.println("! Нельзя положить предмет в закрытую коробку\n");
            return;
        }

        if (item != null) {
            System.out.printf("! В коробке уже есть предмет: %s%n%n", item);
            return;
        }

        this.item = newItem;
        System.out.printf("* В коробку положили предмет: %s%n%n", newItem);
    }

    public void takeOut() {
        if (!isOpen) {
            System.out.println("! Нельзя достать предмет из закрытой коробки\n");
            return;
        }

        if (item == null) {
            System.out.println("! В коробке нет предмета\n");
            return;
        }

        String takenItem = item;
        item = null;
        System.out.printf("* Из коробки достали предмет: %s%n%n", takenItem);
    }
}
