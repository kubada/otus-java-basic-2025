package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Класс велосипеда.
 * Предоставляет возможность перемещаться на определенное расстояние с указанием типа местности.
 * Не может перемещаться: болото.
 * При перемещении тратит силы человека (ограничен)
 */
public class Bicycle extends AbstractTransport {
    private final Human human;
    private final double endurancePerUnit;

    /**
     * Создаёт велосипед с расходом сил человека 0.3 единиц на км.
     */
    public Bicycle(Human human) {
        this.human = human;
        this.endurancePerUnit = 0.3;
    }

    /**
     * Проверяет, может ли велосипед перемещаться по указанному типу местности.
     * Велосипед не может ехать по болоту.
     *
     * @param terrain тип местности
     * @return true, если велосипед может ехать по этой местности
     */
    @Override
    protected boolean canMoveOnTerrain(TerrainType terrain) {
        return terrain != TerrainType.SWAMP;
    }

    /**
     * Рассчитывает необходимое количество сил человека для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return требуемое количество сил человека
     */
    @Override
    protected double getRequiredResource(double distance) {
        return distance * endurancePerUnit;
    }

    /**
     * Проверяет, достаточно ли у человека сил для перемещения на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return true, если сил у человека достаточно
     */
    @Override
    protected boolean hasEnoughResource(double distance) {
        double requiredEndurance = distance * endurancePerUnit;
        return human.getEndurance() >= requiredEndurance;
    }

    /**
     * Расходует силы человека при перемещении на указанную дистанцию.
     *
     * @param distance дистанция перемещения
     * @return количество затраченных сил человека
     */
    @Override
    protected double consumeResource(double distance) {
        double spent = distance * endurancePerUnit;
        human.decreaseEndurance(spent);
        return spent;
    }

    /**
     * Возвращает текущий остаток сил человека.
     *
     * @return остаток сил человека
     */
    @Override
    protected double getRemainingResource() {
        return human.getEndurance();
    }

    /**
     * Возвращает название транспорта.
     *
     * @return название "Велосипед"
     */
    @Override
    protected String getTransportName() {
        return "Велосипед";
    }
}
