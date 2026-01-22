package ru.otus.java.basic.homeworks.lesson_12;

import java.util.Random;

public class Main {
    private static final Random random = new Random();

    static void main() {
        Dish dish = new Dish(random.nextInt(99) + 1);

        int catCount = random.nextInt(9) + 1;
        Cat[] cats = new Cat[catCount];

        System.out.printf("Создано котов: %d.%nСоздана тарелка: %d единиц еды.%n%n",
                catCount, dish.getCurrentAmountFood());

        for (int i = 0; i < catCount; i++) {
            cats[i] = new Cat("Кот № " + i, random.nextInt(9) + 1);
            cats[i].eat(dish);
        }

        System.out.println("\n=== Информация о котах после кормления ===");
        for (Cat cat : cats) {
            cat.info();
        }
    }
}
