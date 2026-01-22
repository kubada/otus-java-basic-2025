package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Класс машины.
 * Предоставляет возможность перемещаться на определенное расстояние с указанием типа местности.
 * Не может перемещаться: густой лес и болото.
 * При перемещении тратит бензин (ограничен).
 */
public class Car extends AbstractTransport {
    private double petrol;
    private final double petrolPerUnit;

    /**
     * Создаёт машину с начальным запасом бензина 100 единиц и расходом 0.5 единиц на км.
     */
    public Car() {
        this.petrol = 100;
        this.petrolPerUnit = 0.5;
    }

    /**
     * Проверяет, может ли машина перемещаться по указанному типу местности.
     * Машина не может ехать по густому лесу и болоту.
     * @param terrain тип местности
     * @return true, если машина может ехать по этой местности
     */
    @Override
    protected boolean canMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.DENSE_FOREST && terrain != TerrainType.SWAMP;
    }

    /**
     * Рассчитывает необходимое количество топлива для перемещения на указанную дистанцию.
     * @param distance дистанция перемещения
     * @return требуемое количество топлива
     */
    @Override
    protected double getRequiredResource(double distance) {
        return distance * petrolPerUnit;
    }

    /**
     * Проверяет, достаточно ли бензина для перемещения на указанную дистанцию.
     * @param distance дистанция перемещения
     * @return true, если бензина достаточно
     */
    @Override
    protected boolean hasEnoughResource(double distance) {
        double requiredPetrol = distance * petrolPerUnit;
        return petrol >= requiredPetrol;
    }

    /**
     * Расходует бензин при перемещении на указанную дистанцию.
     * @param distance дистанция перемещения
     * @return количество затраченного бензина
     */
    @Override
    protected double consumeResource(double distance) {
        double spent = distance * petrolPerUnit;
        petrol -= spent;
        return spent;
    }

    /**
     * Возвращает текущий остаток бензина.
     * @return остаток бензина
     */
    @Override
    protected double getRemainingResource() {
        return petrol;
    }

    /**
     * Возвращает название транспорта.
     * @return название "Машина"
     */
    @Override
    protected String getTransportName() {
        return "Машина";
    }
}
