package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Интерфейс транспорта.
 * Определяет возможность перемещения на определённую дистанцию по определённому типу местности.
 */
public interface Transport {
    /**
     * Проверяет, есть ли у транспорта водитель.
     *
     */
    boolean isDriver();

    /**
     * Устанавливает водителя транспорту.
     *
     * @param human объект человека
     */
    void setDriver(Human human);

    /**
     * Выполняет перемещение на указанную дистанцию по указанному типу местности.
     *
     * @param distance дистанция перемещения
     * @param terrain  тип местности
     * @return true, если перемещение успешно выполнено, false в противном случае
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean move(int distance, TerrainType terrain);
}
