package ru.otus.java.basic.homeworks.lesson_32.app;

/**
 * Представляет элемент (товар) с идентификатором, названием и ценой.
 * Используется для демонстрации работы с JSON в REST API.
 */
public class Item {
    private Long id;
    private String name;
    private int price;

    /**
     * Возвращает идентификатор элемента.
     *
     * @return идентификатор элемента
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор элемента.
     *
     * @param id идентификатор элемента
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает название элемента.
     *
     * @return название элемента
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название элемента.
     *
     * @param name название элемента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает цену элемента.
     *
     * @return цена элемента
     */
    public int getPrice() {
        return price;
    }

    /**
     * Устанавливает цену элемента.
     *
     * @param price цена элемента
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Конструктор по умолчанию.
     * Необходим для десериализации из JSON.
     */
    public Item() {
    }

    /**
     * Конструктор для создания элемента со всеми параметрами.
     *
     * @param id идентификатор элемента
     * @param name название элемента
     * @param price цена элемента
     */
    public Item(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
