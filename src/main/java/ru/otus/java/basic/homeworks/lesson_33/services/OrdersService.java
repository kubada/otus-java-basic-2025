package ru.otus.java.basic.homeworks.lesson_33.services;

import org.springframework.stereotype.Service;

/**
 * Сервис для работы с заказами.
 * Предоставляет бизнес-логику для создания и управления заказами.
 * Взаимодействует с сервисами товаров, клиентов и промо-акций.
 */
@Service
public class OrdersService {
    private final ItemsService itemsService;
    private final CustomersService customersService;
    private final PromoService promoService;

    /**
     * Конструктор сервиса.
     *
     * @param itemsService сервис для работы с товарами
     * @param customersService сервис для работы с клиентами
     * @param promoService сервис для работы с промо-акциями
     */
    public OrdersService(ItemsService itemsService, CustomersService customersService, PromoService promoService) {
        this.itemsService = itemsService;
        this.customersService = customersService;
        this.promoService = promoService;
    }

    /**
     * Создает новый заказ (метод-заглушка).
     * Демонстрирует бизнес-логику создания заказа с расчетом скидок и применением промо-кодов.
     * В реальной реализации принимает ID клиента и DTO с данными заказа.
     */
    public void createOrder(/*customerId, createOrderDtoRq*/) {
        // for item : cart.items
        //  item.check
        //  totalPrice += item.price
        // BigDecimal discount = customerService.getDiscountLevel(customerId);
        // if (createOrderDtoRq.promo != null) {
        //  promoService.applyPromo(customerId, createOrderDtoRq.promo);
        //  discount += 0.10;
        // }
        // finalPrice = totalPrice * (1.0 - discount);
        // Order order = new Order(items, finalPrice, ...);
        // save(order);
        // return order.toDto();
    }
}
