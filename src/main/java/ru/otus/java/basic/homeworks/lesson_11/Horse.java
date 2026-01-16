package ru.otus.java.basic.homeworks.lesson_11;

public class Horse extends Animal {
    public Horse(String name, double runningSpeed, double swimmingSpeed, int endurance) {
        super(name, true, runningSpeed, true, swimmingSpeed, endurance);
    }

    // Переопределяем метод - лошадь тратит 4 единицы выносливости на метр плавания
    @Override
    public int swim(int distance) {
        return performSwim(distance, 4);
    }
}