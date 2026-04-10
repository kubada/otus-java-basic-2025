package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Класс лошади.
 * Предоставляет возможность перемещаться на определенное расстояние с указанием типа местности.
 * Не может перемещаться: болото.
 * При перемещении тратит силы (ограничены)
 */
public class Horse extends AbstractTransport {
    private double stamina;
    private final double staminaPerUnit;

    /**
     * Создаёт лошадь с начальным запасом сил 85 единиц и расходом 2.75 единиц на км.
     */
    public Horse() {
        this.stamina = 85;
        this.staminaPerUnit = 2.75;
    }

    /**
     * Проверяет, может ли лошадь перемещаться по указанному типу местности.
     * Лошадь не может перемещаться по болоту.
     *
     * @param terrain тип местности
     * @return true, если лошадь может перемещаться по этой местности
     */
    @Override
    protected boolean canMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.SWAMP;
    }

    /**
     * Рассчитывает необходимое количество топлива для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return требуемое количество топлива
     */
    @Override
    protected double getRequiredResource(double distance) {
        return distance * staminaPerUnit;
    }

    /**
     * Проверяет, достаточно ли бензина для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return true, если бензина достаточно
     */
    @Override
    protected boolean hasEnoughResource(double distance) {
        double requiredPetrol = distance * staminaPerUnit;
        return stamina >= requiredPetrol;
    }

    /**
     * Расходует бензин при перемещении на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return количество затраченного бензина
     */
    @Override
    protected double consumeResource(double distance) {
        double spent = distance * staminaPerUnit;
        stamina -= spent;
        return spent;
    }

    /**
     * Возвращает текущий остаток бензина.
     *
     * @return остаток бензина
     */
    @Override
    protected double getRemainingResource() {
        return stamina;
    }

    /**
     * Возвращает название транспорта.
     *
     * @return название "Лошадь"
     */
    @Override
    protected String getTransportName() {
        return "Лошадь";
    }
}
