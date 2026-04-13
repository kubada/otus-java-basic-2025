package ru.otus.java.basic.homeworks.lesson_33.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.otus.java.basic.homeworks.lesson_33.dtos.ItemDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Репозиторий для работы с товарами.
 * Предоставляет доступ к данным товаров, хранящихся в памяти.
 */
@Component
public class ItemsRepository {
    private List<ItemDto> items;

    /**
     * Инициализирует репозиторий тестовыми данными.
     * Вызывается автоматически после создания бина Spring.
     */
    @PostConstruct
    public void init() {
        items = new ArrayList<>(Arrays.asList(
                new ItemDto(1L, "Milk", 150),
                new ItemDto(2L, "Bread", 50),
                new ItemDto(3L, "Cheese", 400),
                new ItemDto(4L, "Chocolate", 100)
        ));
    }

    /**
     * Получает товар по идентификатору.
     *
     * @param id идентификатор товара
     * @return товар с указанным идентификатором
     */
    public ItemDto getItemById(Long id) {
        return items.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    /**
     * Получает список всех товаров.
     *
     * @return неизменяемый список всех товаров
     */
    public List<ItemDto> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Сохраняет новый товар.
     * Автоматически присваивает новый идентификатор.
     *
     * @param itemDto данные товара для сохранения
     * @return сохраненный товар с присвоенным идентификатором
     */
    public ItemDto save(ItemDto itemDto) {
        Long newId = items.stream().mapToLong(ItemDto::getId).max().orElse(0L) + 1L;
        itemDto.setId(newId);
        items.add(itemDto);
        return itemDto;
    }
}
