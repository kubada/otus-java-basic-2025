package ru.otus.java.basic.homeworks.lesson_13;

/**
 * Перечисление типов местности.
 */
public enum TerrainType {
    DENSE_FOREST("Густой лес"),
    PLAIN("Равнина"),
    SWAMP("Болото");

    private final String title;

    /**
     * Создаёт тип местности с указанным названием.
     *
     * @param title название местности
     */
    TerrainType(String title) {
        this.title = title;
    }

    /**
     * Возвращает название местности.
     *
     * @return название местности
     */
    public String getTitle() {
        return title;
    }
}
