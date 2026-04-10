package ru.otus.java.basic.homeworks.lesson_27;

import java.util.ArrayList;

/**
 * Коробка для хранения фруктов определённого типа.
 * Использует generics для type safety.
 *
 * @param <T> тип фруктов, которые можно хранить в коробке (должен быть подклассом {@link Fruit})
 */
public class Box<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    private static final System.Logger logger = System.getLogger(Box.class.getName());

    /**
     * Добавляет фрукт в коробку.
     *
     * @param fruit фрукт для добавления (не может быть null)
     * @throws IllegalArgumentException если фрукт равен null
     */
    public void addingFruit(T fruit) {
        if (fruit == null) {
            logger.log(System.Logger.Level.ERROR, "Попытка добавить null фрукт");
            throw new IllegalArgumentException("Фрукт не может быть null");
        }
        fruits.add(fruit);
        logger.log(System.Logger.Level.DEBUG,
                String.format("Добавлен %s (вес: %d). Всего в коробке: %d шт.",
                        fruit.getClass().getSimpleName(), fruit.getWeight(), fruits.size()));
    }

    /**
     * Высчитывает вес коробки как сумму весов всех фруктов.
     *
     * @return общий вес всех фруктов в коробке
     */
    public double weight() {
        double boxWeight = 0;

        for (T fruit : fruits) {
            boxWeight += fruit.getWeight();
        }

        logger.log(System.Logger.Level.DEBUG,
                String.format("Вес коробки с %d фруктами: %.2f", fruits.size(), boxWeight));

        return boxWeight;
    }

    /**
     * Сравнивает текущую коробку с другой по весу.
     * Коробки считаются равными, если разница весов меньше 0.0001.
     *
     * @param box коробка для сравнения (не может быть null)
     * @return true если веса коробок равны, иначе false
     * @throws IllegalArgumentException если переданная коробка равна null
     */
    public boolean compare(Box<?> box) {
        if (box == null) {
            logger.log(System.Logger.Level.ERROR, "Попытка сравнить с null коробкой");
            throw new IllegalArgumentException("Коробка для сравнения не может быть null");
        }

        double thisWeight = this.weight();
        double otherWeight = box.weight();
        boolean isEqual = Math.abs(thisWeight - otherWeight) < 0.0001;

        logger.log(System.Logger.Level.INFO,
                String.format("Сравнение коробок: %.2f vs %.2f = %s",
                        thisWeight, otherWeight, isEqual ? "равны" : "не равны"));

        return isEqual;
    }


    /**
     * Пересыпает все фрукты из текущей коробки в другую.
     * После операции текущая коробка становится пустой.
     * Целевая коробка должна быть совместимого типа (Box<T> или Box<Fruit>).
     *
     * @param box коробка, в которую пересыпаются фрукты (не может быть null)
     * @throws IllegalArgumentException если целевая коробка равна null
     * @throws IllegalStateException    если текущая коробка пуста
     */
    public void sprinkleFruit(Box<? super T> box) {
        if (box == null) {
            logger.log(System.Logger.Level.ERROR, "Попытка пересыпать в null коробку");
            throw new IllegalArgumentException("Целевая коробка не может быть null");
        }

        if (fruits.isEmpty()) {
            logger.log(System.Logger.Level.WARNING, "Попытка пересыпать из пустой коробки");
            throw new IllegalStateException("Невозможно пересыпать из пустой коробки");
        }

        int count = fruits.size();
        logger.log(System.Logger.Level.INFO,
                String.format("Пересыпание %d фруктов в другую коробку...", count));

        for (T fruit : fruits) {
            box.addingFruit(fruit);
        }
        fruits.clear();

        logger.log(System.Logger.Level.INFO,
                String.format("Успешно пересыпано %d фруктов. Текущая коробка пуста.", count));
    }

    /**
     * Возвращает количество фруктов в коробке.
     *
     * @return количество фруктов
     */
    public int getFruitCount() {
        return fruits.size();
    }
}
