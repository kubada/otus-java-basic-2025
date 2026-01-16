package ru.otus.java.basic.homeworks.lesson_11;

public class Cat extends Animal {
    public Cat(String name, double runningSpeed, int endurance) {
        super(name, true, runningSpeed, false, 0, endurance);
    }

    // Кот не умеет плавать - проверка происходит в Animal через isCanSwim = false
    // Переопределение метода swim не требуется
}