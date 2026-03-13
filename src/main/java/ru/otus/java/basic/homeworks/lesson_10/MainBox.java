package ru.otus.java.basic.homeworks.lesson_10;

public class MainBox {
    static void main() {
        Box box = new Box(3, 3, 3, "графит");

        box.printInfo();

        box.repaint("красный");
        box.printInfo();

        box.open();
        box.printInfo();

        box.close();
        box.printInfo();

        box.put("распределяющая шляпа");

        box.open();
        box.put("распределяющая шляпа");
        box.printInfo();

        box.takeOut();
        box.printInfo();

        box.close();
        box.takeOut();
        box.printInfo();
    }
}
