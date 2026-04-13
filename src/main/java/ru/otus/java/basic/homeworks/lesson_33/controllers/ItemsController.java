package ru.otus.java.basic.homeworks.lesson_33.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.basic.homeworks.lesson_33.dtos.ItemDto;
import ru.otus.java.basic.homeworks.lesson_33.services.ItemsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST контроллер для управления товарами.
 * Предоставляет API для получения и создания товаров.
 */
@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private final ItemsService itemsService;

    /**
     * Конструктор контроллера.
     *
     * @param itemsService сервис для работы с товарами
     */
    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    /**
     * Получает список всех товаров.
     *
     * @return список товаров
     */
    @GetMapping
    public List<ItemDto> getItems() {
        return itemsService.getAllItems();
    }

    /**
     * Получает товар по идентификатору.
     *
     * @param id идентификатор товара
     * @return товар с указанным идентификатором
     */
    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable Long id) {
        return itemsService.getItemById(id);
    }

    /**
     * Создает новый товар.
     *
     * @param itemDto данные нового товара
     * @return созданный товар с присвоенным идентификатором
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createNewItem(@RequestBody ItemDto itemDto) {
        return itemsService.save(itemDto);
    }

    /**
     * Демонстрация указания статус кода ответа через ResponseEntity.
     * Удаляет товар и возвращает статус 204 No Content.
     *
     * @param id идентификатор товара для удаления
     * @return ResponseEntity с кастомным статусом 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        // Логика удаления товара (здесь просто демонстрация)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Демонстрация добавления JSON объекта в тело ответа с кастомным статусом.
     * Возвращает информацию о товаре с дополнительными метаданными.
     *
     * @param id идентификатор товара
     * @return ResponseEntity с JSON объектом и статусом 200
     */
    @GetMapping("/{id}/details")
    public ResponseEntity<Map<String, Object>> getItemDetails(@PathVariable Long id) {
        ItemDto item = itemsService.getItemById(id);

        // Создаем JSON объект с дополнительной информацией
        Map<String, Object> response = new HashMap<>();
        response.put("item", item);
        response.put("available", true);
        response.put("stock", 100);
        response.put("category", "Food");
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

    /**
     * Демонстрация условного возврата разных статусов.
     * Возвращает 404 если товар не найден, или 200 с данными если найден.
     *
     * @param id идентификатор товара
     * @return ResponseEntity с товаром и статусом 200 или 404
     */
    @GetMapping("/{id}/safe")
    public ResponseEntity<ItemDto> getItemSafely(@PathVariable Long id) {
        try {
            ItemDto item = itemsService.getItemById(id);
            return ResponseEntity.ok(item); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /**
     * Демонстрация возврата кастомного JSON с информацией об ошибке.
     * Валидирует товар и возвращает 400 Bad Request при невалидных данных.
     *
     * @param itemDto данные товара для валидации
     * @return ResponseEntity с результатом валидации
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateItem(@RequestBody ItemDto itemDto) {
        Map<String, Object> response = new HashMap<>();

        if (itemDto.getTitle() == null || itemDto.getTitle().isEmpty()) {
            response.put("valid", false);
            response.put("error", "Title is required");
            response.put("field", "title");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
        }

        if (itemDto.getPrice() <= 0) {
            response.put("valid", false);
            response.put("error", "Price must be greater than 0");
            response.put("field", "price");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
        }

        response.put("valid", true);
        response.put("message", "Item is valid");
        return ResponseEntity.ok(response); // 200 OK
    }
}
