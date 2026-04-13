package ru.otus.java.basic.homeworks.lesson_33;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс Spring Boot приложения.
 * Запускает веб-приложение с автоконфигурацией Spring.
 */
@SpringBootApplication
public class SpringAppApplication {
	/**
	 * Точка входа в приложение.
	 * Запускает Spring Boot приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}
}
