package ru.otus.java.basic.homeworks.lesson_27;

/**
 * Демонстрация работы с коробками фруктов.
 * Показывает различные сценарии использования generics.
 */
public class Application {
    private static final System.Logger logger = System.getLogger(Application.class.getName());

    static void main() {
        scenario1CreateAndFillBoxes();
        scenario2CompareBoxes();
        scenario3TransferFruits();
        scenario4ErrorHandling();
    }

    /**
     * Сценарий 1: Создание и наполнение коробок разными типами фруктов.
     */
    private static void scenario1CreateAndFillBoxes() {
        logger.log(System.Logger.Level.INFO, "\n--- Сценарий 1: Создание и наполнение коробок ---");

        Box<Apple> appleBox = new Box<>();
        appleBox.addingFruit(new Apple(10));
        appleBox.addingFruit(new Apple(12));
        appleBox.addingFruit(new Apple(11));
        logger.log(System.Logger.Level.INFO,
                String.format("Коробка с яблоками: %d шт., общий вес: %.2f", appleBox.getFruitCount(),
                        appleBox.weight()));

        Box<Orange> orangeBox = new Box<>();
        orangeBox.addingFruit(new Orange(15));
        orangeBox.addingFruit(new Orange(14));
        logger.log(System.Logger.Level.INFO,
                String.format("Коробка с апельсинами: %d шт., общий вес: %.2f",
                        orangeBox.getFruitCount(), orangeBox.weight()));

        Box<Fruit> mixedBox = new Box<>();
        mixedBox.addingFruit(new Apple(10));
        mixedBox.addingFruit(new Orange(15));
        mixedBox.addingFruit(new Apple(11));
        logger.log(System.Logger.Level.INFO,
                String.format("Смешанная коробка: %d шт., общий вес: %.2f",
                        mixedBox.getFruitCount(), mixedBox.weight()));
    }

    /**
     * Сценарий 2: Сравнение коробок с разными типами фруктов.
     */
    private static void scenario2CompareBoxes() {
        logger.log(System.Logger.Level.INFO, "\n--- Сценарий 2: Сравнение коробок ---");

        Box<Apple> box1 = new Box<>();
        box1.addingFruit(new Apple(10));
        box1.addingFruit(new Apple(10));
        box1.addingFruit(new Apple(10));

        Box<Orange> box2 = new Box<>();
        box2.addingFruit(new Orange(15));
        box2.addingFruit(new Orange(15));

        Box<Apple> box3 = new Box<>();
        box3.addingFruit(new Apple(15));
        box3.addingFruit(new Apple(15));

        logger.log(System.Logger.Level.INFO, String.format("box1 (3 яблока по 10): вес = %.2f", box1.weight()));
        logger.log(System.Logger.Level.INFO, String.format("box2 (2 апельсина по 15): вес = %.2f", box2.weight()));
        logger.log(System.Logger.Level.INFO, String.format("box3 (2 яблока по 15): вес = %.2f", box3.weight()));

        boolean result1 = box1.compare(box2);
        logger.log(System.Logger.Level.INFO,
                String.format("box1 == box2? %s (веса %s)",
                        result1, result1 ? "равны" : "не равны"));

        boolean result2 = box2.compare(box3);
        logger.log(System.Logger.Level.INFO,
                String.format("box2 == box3? %s (веса %s)",
                        result2, result2 ? "равны" : "не равны"));
    }

    /**
     * Сценарий 3: Пересыпание фруктов между коробками.
     */
    private static void scenario3TransferFruits() {
        logger.log(System.Logger.Level.INFO, "\n--- Сценарий 3: Пересыпание фруктов ---");

        Box<Apple> sourceBox = new Box<>();
        sourceBox.addingFruit(new Apple(10));
        sourceBox.addingFruit(new Apple(12));
        sourceBox.addingFruit(new Apple(11));

        Box<Fruit> targetBox = new Box<>();
        targetBox.addingFruit(new Orange(15));

        logger.log(System.Logger.Level.INFO,
                String.format("До пересыпания: sourceBox = %d шт. (%.2f), targetBox = %d шт. (%.2f)",
                        sourceBox.getFruitCount(), sourceBox.weight(),
                        targetBox.getFruitCount(), targetBox.weight()));

        sourceBox.sprinkleFruit(targetBox);

        logger.log(System.Logger.Level.INFO,
                String.format("После пересыпания: sourceBox = %d шт. (%.2f), targetBox = %d шт. (%.2f)",
                        sourceBox.getFruitCount(), sourceBox.weight(),
                        targetBox.getFruitCount(), targetBox.weight()));

        logger.log(System.Logger.Level.INFO,
                "\nПопытка пересыпать Box<Apple> в Box<Orange> не скомпилируется благодаря generics");
        // Box<Orange> orangeBox = new Box<>();
    }

    /**
     * Сценарий 4: Обработка исключительных ситуаций.
     */
    private static void scenario4ErrorHandling() {
        logger.log(System.Logger.Level.INFO, "\n--- Сценарий 4: Обработка ошибок ---");

        Box<Apple> box = new Box<>();
        box.addingFruit(new Apple(10));

        try {
            logger.log(System.Logger.Level.INFO, "Попытка добавить null фрукт...");
            box.addingFruit(null);
        } catch (IllegalArgumentException e) {
            logger.log(System.Logger.Level.WARNING, "Исключение: " + e.getMessage());
        }

        try {
            logger.log(System.Logger.Level.INFO, "Попытка сравнить с null коробкой...");
            box.compare(null);
        } catch (IllegalArgumentException e) {
            logger.log(System.Logger.Level.WARNING, "Исключение: " + e.getMessage());
        }

        try {
            logger.log(System.Logger.Level.INFO, "Попытка пересыпать из пустой коробки...");
            Box<Apple> emptyBox = new Box<>();
            Box<Fruit> targetBox = new Box<>();
            emptyBox.sprinkleFruit(targetBox);
        } catch (IllegalStateException e) {
            logger.log(System.Logger.Level.WARNING, "Исключение: " + e.getMessage());
        }

        try {
            logger.log(System.Logger.Level.INFO, "Попытка пересыпать в null коробку...");
            box.sprinkleFruit(null);
        } catch (IllegalArgumentException e) {
            logger.log(System.Logger.Level.WARNING, "Исключение: " + e.getMessage());
        }
    }
}
