package ru.otus.java.basic.homeworks.lesson_33.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.java.basic.homeworks.lesson_33.dtos.ItemDto;
import ru.otus.java.basic.homeworks.lesson_33.repositories.ItemsRepository;
import java.util.List;

/**
 * Сервис для работы с товарами.
 * Предоставляет бизнес-логику для управления товарами.
 */
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ItemsService.class.getName());

    /**
     * Конструктор сервиса.
     *
     * @param itemsRepository репозиторий для работы с товарами
     */
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    /**
     * Получает товар по идентификатору.
     *
     * @param id идентификатор товара
     * @return товар с указанным идентификатором
     */
    public ItemDto getItemById(Long id) {
        return itemsRepository.getItemById(id);
    }

    /**
     * Получает список всех товаров.
     * Логирует имя потока для демонстрации многопоточности.
     *
     * @return список всех товаров
     */
    public List<ItemDto> getAllItems() {
        logger.info("Thread name: {}", Thread.currentThread().getName());
        return itemsRepository.getAllItems();
    }

    /**
     * Сохраняет новый товар.
     *
     * @param itemDto данные товара для сохранения
     * @return сохраненный товар с присвоенным идентификатором
     */
    public ItemDto save(ItemDto itemDto) {
        return itemsRepository.save(itemDto);
    }
}
