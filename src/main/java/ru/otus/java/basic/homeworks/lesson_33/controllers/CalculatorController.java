package ru.otus.java.basic.homeworks.lesson_33.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для выполнения математических операций.
 * Предоставляет endpoints для сложения, вычитания и умножения чисел.
 */
@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    /**
     * Выполняет операцию сложения двух чисел.
     *
     * @param a первое число (по умолчанию 0)
     * @param b второе число (по умолчанию 0)
     * @return строка с результатом сложения
     */
    @GetMapping("/add")
    public String add(
            @RequestParam(required = false, defaultValue = "0") Integer a,
            @RequestParam(required = false, defaultValue = "0") Integer b
    ) {
        return a + " + " + b + " = " + (a + b);
    }

    /**
     * Выполняет операцию вычитания двух чисел.
     *
     * @param a уменьшаемое (по умолчанию 0)
     * @param b вычитаемое (по умолчанию 0)
     * @return строка с результатом вычитания
     */
    @GetMapping("/subtract")
    public String subtract(
            @RequestParam(required = false, defaultValue = "0") Integer a,
            @RequestParam(required = false, defaultValue = "0") Integer b
    ) {
        return a + " - " + b + " = " + (a - b);
    }

    /**
     * Выполняет операцию умножения двух чисел.
     *
     * @param a первый множитель (по умолчанию 0)
     * @param b второй множитель (по умолчанию 0)
     * @return строка с результатом умножения
     */
    @GetMapping("/mul")
    public String mul(
            @RequestParam(required = false, defaultValue = "0") Integer a,
            @RequestParam(required = false, defaultValue = "0") Integer b
    ) {
        return a + " * " + b + " = " + (a * b);
    }
}
