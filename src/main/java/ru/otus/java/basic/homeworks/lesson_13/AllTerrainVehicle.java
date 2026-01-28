package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Класс вездехода.
 * Предоставляет возможность перемещаться на определенное расстояние с указанием типа местности.
 * Перемещается без ограничений.
 * При перемещении тратит бензин (ограничен).
 */
public class AllTerrainVehicle extends AbstractTransport {
    private double petrol;
    private final double petrolPerUnit;

    /**
     * Создаёт вездеход с начальным запасом бензина 150 единиц и расходом 1.5 единиц на км.
     */
    public AllTerrainVehicle() {
        this.petrol = 150;
        this.petrolPerUnit = 1.5;
    }

    /**
     * Проверяет, может ли вездеход перемещаться по указанному типу местности.
     * Вездеход может ехать по любой местности без ограничений.
     *
     * @param terrain тип местности
     * @return всегда true
     */
    @Override
    protected boolean canMoveOnTerrain(TerrainType terrain) {
        return true;
    }

    /**
     * Рассчитывает необходимое количество топлива для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return требуемое количество топлива
     */
    @Override
    protected double getRequiredResource(double distance) {
        return distance * petrolPerUnit;
    }


    /**
     * Проверяет, достаточно ли топлива для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return true, если топлива достаточно
     */
    @Override
    protected boolean hasEnoughResource(double distance) {
        double requiredPetrol = distance * petrolPerUnit;
        return petrol >= requiredPetrol;
    }

    /**
     * Расходует топливо при перемещении на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return количество затраченного топлива
     */
    @Override
    protected double consumeResource(double distance) {
        double spent = distance * petrolPerUnit;
        petrol -= spent;
        return spent;
    }

    /**
     * Возвращает текущий остаток топлива.
     *
     * @return остаток топлива
     */
    @Override
    protected double getRemainingResource() {
        return petrol;
    }

    /**
     * Возвращает название транспорта.
     *
     * @return название "Вездеход"
     */
    @Override
    protected String getTransportName() {
        return "Вездеход";
    }
}
