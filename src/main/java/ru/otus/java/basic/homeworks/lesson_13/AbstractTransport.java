package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Абстрактный класс транспорта.
 * Реализует общую логику перемещения для всех видов транспорта.
 * Дочерние классы должны реализовать методы проверки местности, расчёта и расхода ресурсов.
 */
public abstract class AbstractTransport implements Transport {
    /**
     * Водитель транспорта.
     */
    protected Human driver = null;

    /**
     * Проверяет, есть ли у транспорта водитель.
     *
     * @return true, если у транспорта есть водитель
     */
    public boolean isDriver() {
        return driver != null;
    }

    /**
     * Устанавливает или убирает водителя транспорта.
     *
     * @param human человек-водитель или null для удаления водителя
     */
    public void setDriver(Human human) {
        this.driver = human;
    }


    /**
     * Проверяет, может ли транспорт перемещаться по указанному типу местности.
     *
     * @param terrain тип местности
     * @return true, если транспорт может ехать по этой местности
     */
    protected abstract boolean canMoveOnTerrain(TerrainType terrain);

    /**
     * Проверяет, достаточно ли ресурса для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return true, если ресурса достаточно
     */
    protected abstract boolean hasEnoughResource(double distance);

    /**
     * Рассчитывает необходимое количество ресурса для перемещения.
     *
     * @param distance дистанция перемещения
     * @return требуемое количество ресурса
     */
    protected abstract double getRequiredResource(double distance);

    /**
     * Расходует ресурс при перемещении.
     *
     * @param distance дистанция перемещения
     * @return количество затраченного ресурса
     */
    protected abstract double consumeResource(double distance);

    /**
     * Возвращает текущий остаток ресурса.
     *
     * @return остаток ресурса
     */
    protected abstract double getRemainingResource();

    /**
     * Возвращает название транспорта для вывода в консоль.
     *
     * @return название транспорта
     */
    protected abstract String getTransportName();

    /**
     * Возвращает строковое представление транспорта.
     *
     * @return название транспорта
     */
    @Override
    public String toString() {
        return getTransportName();
    }

    /**
     * Выполняет перемещение транспорта на указанную дистанцию по указанному типу местности.
     *
     * @param distance дистанция перемещения
     * @param terrain  тип местности
     * @return true, если перемещение успешно выполнено, false в противном случае
     */
    @Override
    public boolean move(int distance, TerrainType terrain) {
        if (!isDriver()) {
            System.out.printf("! Объект [%s] не может переместиться самостоятельно.%n", getTransportName());
            return false;
        }

        if (!canMoveOnTerrain(terrain)) {
            System.out.printf("! Объект [%s] не может переместиться по местности [%s].%n",
                    getTransportName(), terrain.getTitle());
            return false;
        }

        if (!hasEnoughResource(distance)) {
            double required = getRequiredResource(distance);
            double remaining = getRemainingResource();
            System.out.printf("! Объект [%s] не может переместиться. Недостаточно ресурса (топливо/сила), (есть %.2f, необходимо %.2f).%n",
                    getTransportName(), remaining, required);
            return false;
        }

        double unitsSpent = consumeResource(distance);
        System.out.printf("* Объект [%s] переместился на %d км по местности [%s]. Потрачено %.2f единиц ресурса (топливо/сила).%n",
                getTransportName(), distance, terrain.getTitle(), unitsSpent);
        return true;

    }
}
