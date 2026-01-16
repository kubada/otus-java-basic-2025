package ru.otus.java.basic.homeworks.lesson_11;

public class Main {
    static void main() {
        Cat cat = new Cat("Кошка", 2.5, 30);
        Dog dog = new Dog("Собака", 12.5, 7.5, 100);
        Horse horse = new Horse("Лошадь", 22.5, 5, 150);

        cat.info();
        dog.info();
        horse.info();

        cat.run(15);
        dog.run(31);
        horse.run(50);

        cat.info();
        dog.info();
        horse.info();

        cat.swim(10);
        dog.swim(23);
        horse.swim(30);

        cat.info();
        dog.info();
        horse.info();

        cat.run(25);
        dog.run(41);
        horse.run(5);

        cat.info();
        dog.info();
        horse.info();
    }
}
