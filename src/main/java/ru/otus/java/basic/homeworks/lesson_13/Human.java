package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Класс человека.
 * Может сесть на любой транспорт и встать с него.
 * Переместиться на некоторое расстояние при условии, что находится на транспорте.
 * Если для перемещения не указан транспорт, считаем, что идём пешком.
 */
public class Human {
    private final String name;
    private double endurance;
    private final double endurancePerUnit;
    private Transport currentTransport;

    public double getEndurance() {
        return endurance;
    }

    public void decreaseEndurance(double spent) {
        endurance -= spent;
    }

    /**
     * Создаёт человека с начальным запасом сил 50 и расходом 0.7 единиц на км.
     */
    public Human(String name) {
        this.name = name;
        this.endurance = 50;
        this.endurancePerUnit = 0.7;
        this.currentTransport = null;
    }

    /**
     * Выполняет посадку на транспорт.
     *
     * @param transport транспорт
     */
    public void setTransport(Transport transport) {
        if (currentTransport == null) {
            this.currentTransport = transport;
            transport.setDriver(this);
            System.out.printf("* Человек \"%s\" выполнил [посадку] на транспорт [%s].%n",
                    name, transport);
        } else {
            System.out.printf("! Человек \"%s\" не может выполнить [посадку] на транспорт [%s], т.к. находится на другом - [%s].%n",
                    name, transport, currentTransport);
        }
    }

    /**
     * Выполняет сход с транспорта.
     *
     * @param transport транспорт
     */
    public void leaveTransport(Transport transport) {
        if (currentTransport == null) {
            System.out.printf("! Человек \"%s\" не находится на транспорте.%n", name);
            return;
        }

        if (currentTransport == transport) {
            this.currentTransport = null;
            transport.setDriver(null);
            System.out.printf("* Человек \"%s\" выполнил [сход] с транспорта [%s].%n",
                    name, transport);
        } else {
            System.out.printf("! Человек \"%s\" не может выполнить [сход] с транспорта [%s], т.к. не находится на нём.%n",
                    name, transport);
        }
    }


    /**
     * Выполняет перемещение человека на указанную дистанцию по указанному типу местности.
     *
     * @param distance дистанция перемещения
     * @param terrain  тип местности
     * @return true, если перемещение успешно выполнено, false в противном случае
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean move(int distance, TerrainType terrain) {
        if (currentTransport != null) {
            return currentTransport.move(distance, terrain);
        } else {
            double required = distance * endurancePerUnit;

            if (endurance >= required) {
                endurance -= required;
                System.out.printf("* Человек \"%s\" переместился [пешком] на %d км по местности [%s]. Потрачено %.2f единиц выносливости.%n",
                        name, distance, terrain.getTitle(), required);
                return true;
            } else {

                System.out.printf("! Человек \"%s\" не может переместиться. Недостаточно выносливости, (есть %.2f, необходимо %.2f).%n",
                        name, endurance, required);
                return false;
            }
        }
    }
}
