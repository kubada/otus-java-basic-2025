package ru.otus.java.basic.homeworks.lesson_33.dtos;

/**
 * DTO (Data Transfer Object) для товара.
 * Используется для передачи данных о товаре между слоями приложения.
 */
public class ItemDto {
    private Long id;
    private String title;
    private int price;

    /**
     * Возвращает идентификатор товара.
     *
     * @return идентификатор товара
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор товара.
     *
     * @param id идентификатор товара
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает название товара.
     *
     * @return название товара
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает название товара.
     *
     * @param title название товара
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает цену товара.
     *
     * @return цена товара
     */
    public int getPrice() {
        return price;
    }

    /**
     * Устанавливает цену товара.
     *
     * @param price цена товара
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Конструктор по умолчанию.
     * Необходим для десериализации из JSON.
     */
    public ItemDto() {
    }

    /**
     * Конструктор для создания товара со всеми параметрами.
     *
     * @param id идентификатор товара
     * @param title название товара
     * @param price цена товара
     */
    public ItemDto(Long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}
