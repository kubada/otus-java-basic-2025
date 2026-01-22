package ru.otus.java.basic.homeworks.lesson_11;

public class Animal {
    private final String name;
    private final boolean isCanRun;   // возможность бегать
    private final double runningSpeed;
    private final boolean isCanSwim;  // возможность плавать
    private final double swimmingSpeed;
    private int endurance;    // выносливость (изменяется при действиях)
    private boolean fatigue; // усталость (изменяется при нехватке выносливости)

    public Animal(String name, boolean isCanRun, double runningSpeed, boolean isCanSwim, double swimmingSpeed, int endurance) {
        this.name = name;
        this.isCanRun = isCanRun;
        this.runningSpeed = runningSpeed;
        this.isCanSwim = isCanSwim;
        this.swimmingSpeed = swimmingSpeed;
        this.endurance = endurance;
        this.fatigue = false;
    }

    /**
     * Унифицированный метод выполнения действия.
     *
     * @param distance          преодолеваемая дистанция.
     * @param endurancePerMeter количество единиц выносливости на 1 метр действия.
     * @param actionSpeed       скорость выполнения действия.
     * @param action            действие для вывода (например, "бег", "плавание").
     * @return время, затраченное на выполняемое действие.
     */
    private double performAction(int distance, boolean isCan, int endurancePerMeter, double actionSpeed, String action) {
        String animalType = getClass().getSimpleName();

        // положительная дистанция
        if (distance <= 0 || endurancePerMeter <= 0) {
            System.out.printf("! \"%s\" (%s): Дистанция и/или количество единиц выносливости должны быть положительными.%n", name, animalType);
            return -1;
        }

        if (!isCan) {
            System.out.printf("! \"%s\" (%s): не может выполнить действие [%s].%n", name, animalType, action);
            return -1;
        }
        // нет ли усталости
        if (fatigue) {
            System.out.printf("! \"%s\" (%s): устало и не может выполнить действие.%n", name, animalType);
            return -1;
        }

        // расчёт требуемой выносливости на дистанцию
        int requiredEndurance = distance * endurancePerMeter;

        //  достаточно ли выносливости
        if (endurance >= requiredEndurance) {
            // отнимаем единицы выносливости
            endurance -= requiredEndurance;
            // расчёт времени
            double timeSpent = distance / actionSpeed;
            // вывод результата
            System.out.printf("* \"%s\" (%s): [%s] на %d метра(ов) за %.2f секунд. Потрачено %d единиц выносливости.%n",
                    name, animalType, action, distance, timeSpent, requiredEndurance);

            return timeSpent;

            // выносливости не хватает на преодоление дистанции - появляется усталость
        } else {
            this.fatigue = true;
            System.out.printf("! \"%s\" (%s): не хватает единиц выносливости для выполнения действия (есть %d, необходимо %d).%n",
                    name, animalType, endurance, requiredEndurance);
            return -1;
        }
    }

    /**
     * Protected метод для бега. Используется в дочерних классах если нужно.
     *
     * @param distance          преодолеваемая дистанция.
     * @param endurancePerMeter количество единиц выносливости на 1 метр.
     * @return время затраченное на бег.
     */
    // Подавляем предупреждение "Value of parameter 'endurancePerMeter' is always '1'": параметр может быть переопределён в методах дочерних классов
    @SuppressWarnings("SameParameterValue")
    protected int performRun(int distance, int endurancePerMeter) {
        return (int) performAction(distance, isCanRun, endurancePerMeter, runningSpeed, "бег");
    }

    /**
     * Protected метод для плавания. Используется в дочерних классах.
     *
     * @param distance          преодолеваемая дистанция.
     * @param endurancePerMeter количество единиц выносливости на 1 метр.
     * @return время затраченное на плавание.
     */
    protected int performSwim(int distance, int endurancePerMeter) {
        return (int) performAction(distance, isCanSwim, endurancePerMeter, swimmingSpeed, "плавание");
    }

    /**
     * Метод бега.
     * Все животные тратят на 1 метр бега 1 единицу выносливости.
     *
     * @param distance преодолеваемая дистанция.
     * @return время затраченное на бег.
     */
    // Подавляем предупреждение "Return value of the method is never used ": возврат явно не используется
    @SuppressWarnings("UnusedReturnValue")
    public int run(int distance) {
        return performRun(distance, 1);
    }

    /**
     * Метод плавания.
     * Собаки на 1 метр плавания тратят 2 единицы выносливости. Лошади на 1 метр плавания тратят 4 единицы выносливости.
     *
     * @param distance преодолеваемая дистанция.
     * @return время затраченное на плавание.
     */
    // Подавляем предупреждение "Return value of the method is never used ": возврат явно не используется
    @SuppressWarnings("UnusedReturnValue")
    public int swim(int distance) {
        return performSwim(distance, 1);
    }

    /**
     * Вывод состояние животного в консоль.
     */
    public void info() {
        System.out.printf("%n" +
                        "Животное: %s%n" +
                        "Имя: %s%n" +
                        "Скорость бега: %.2f м/с%n" +
                        "Скорость плавания: %.2f м/с%n" +
                        "Выносливость: %d единиц%n" +
                        "Усталость: %s" +
                        "%n---%n",
                getClass().getSimpleName(), name, runningSpeed, swimmingSpeed, endurance, fatigue ? "Да" : "Нет");
    }
}

