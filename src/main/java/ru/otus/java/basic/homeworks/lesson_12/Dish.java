package ru.otus.java.basic.homeworks.lesson_12;

public class Dish {
    public Integer getCurrentAmountFood() {
        return currentAmountFood;
    }

    private Integer currentAmountFood;  // текущее количество еды (куски = pieces)
    private final Integer maxAmountFood;  // максимальное количество еды (куски = pieces)

    public Dish(Integer maxAmountFood) {
        this.currentAmountFood = maxAmountFood;
        this.maxAmountFood = maxAmountFood;
    }

    /**
     * Метод добавления еды в тарелку.
     *
     * @param pieces количество кусков еды.
     * @return результат выполнения (true/false).
     */
    public boolean addFood(Integer pieces) {
        // не мб больше maxAmountFood
        if (pieces + currentAmountFood <= maxAmountFood) {
            currentAmountFood += pieces;
            // System.out.printf("+ В тарелку добавлено %d единиц еды.%n", pieces);
            return true;
        }

        return false;
    }

    /**
     * Метод уменьшения количества еды в тарелке.
     *
     * @param pieces количество кусков еды.
     * @return результат выполнения (true/false).
     */
    public boolean reducingFood(Integer pieces) {
        // не мб отрицательного количества еды
        // если в тарелке >=0 pieces возвращаем true, иначе false
        if (currentAmountFood - pieces >= 0) {
            currentAmountFood -= pieces;
            // System.out.printf("- Количество еды в тарелке уменьшилось на %d единиц.%n", pieces);
            return true;
        }

        return false;
    }
}
