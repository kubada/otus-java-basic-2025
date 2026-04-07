package ru.otus.java.basic.homeworks.lesson_12;

public class Cat {
    private final String name;
    private final int appetite;   // аппетит (сколько еды нужно коту, чтобы насытиться)
    private boolean satiety;    // сытость (изменяется при приёме пищи)

    public Cat(String name, Integer appetite) {
        this.name = name;
        this.appetite = appetite;
        this.satiety = false;   // изначально созданные коты голодные
    }

    /**
     * Метод приёма пищи.
     * Если еды в тарелке хватает на аппетит - сытость (satiety) = true.
     * Если еды не хватает - кот не трогает тарелку.
     * Кот не мб наполовину сыт.
     *
     * @param dish тарелка.
     * @return результат выполнения (true/false).
     */
    public boolean eat(Dish dish) {
        // проверка на сытость
        if (this.satiety) {
            System.out.printf("! Кот \"%s\" уже сыт.", name);
            return false;
        }

        Integer dishCurrentAmountFood = dish.getCurrentAmountFood();

        // если не хватило еды - кот не трогает тарелку
        if (dishCurrentAmountFood < appetite) {
            System.out.printf("! Кот \"%s\" не трогает тарелку. Не хватает единиц еды (нужно %d, в тарелке %d).%n", name, appetite, dishCurrentAmountFood);

            return false;
        } else {
            // уменьшаем количество еды в тарелке
            if (dish.reducingFood(appetite)) {
                this.satiety = true;
                System.out.printf("* Кот \"%s\" поел.%n", name);

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Вывод информации о коте в консоль.
     */
    public void info() {
        System.out.printf("%n" +
                        "Имя: %s%n" +
                        "Аппетит (сколько еды нужно, чтобы насытится): %d%n" +
                        "Сытость: %s" +
                        "%n---%n",
                name, appetite, satiety ? "Да (не голоден)" : "Нет (голодный)");
    }
}
