package ru.otus.java.basic.homeworks.lesson_11;

public class Dog extends Animal {
    public Dog(String name, double runningSpeed, double swimmingSpeed, int endurance) {
        super(name, true, runningSpeed, true, swimmingSpeed, endurance);
    }

    // Переопределяем метод - собака тратит 2 единицы выносливости на метр плавания
    @Override
    public int swim(int distance) {
        return performSwim(distance, 2);
    }
}