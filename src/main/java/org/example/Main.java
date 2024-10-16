package org.example;

import org.example.model.InvestorModel;
import org.example.model.OrderModel;
import org.example.repository.InvestorRepo; // Замените на ваш путь к репозиторию
import org.example.repository.OrderRepo; // Замените на ваш путь к репозиторию
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private InvestorRepo investorRepo; // Репозиторий для инвесторов

    @Autowired
    private OrderRepo orderRepo; // Репозиторий для заказов

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {

//        Описание метода
//        Этот метод run является основной логикой консольного приложения для краудинвестинга. Он выполняет следующие задачи:
//
//        Бесконечный цикл: Метод работает в бесконечном цикле, что позволяет пользователю повторно выбирать действия, пока он не закроет программу.
//
//        Меню выбора действий: Пользователь может выбрать одно из двух действий: стать инвестором или привлечь капитал.
//
//                Регистрация инвестора: При выборе первого действия программа запрашивает у пользователя ФИО и сохраняет его как инвестора с предопределенными значениями для остальных полей (ИИН, номер телефона, почта и т.д.). Затем отображается список всех существующих ордеров.
//
//        Создание ордера: При выборе второго действия программа запрашивает у пользователя ФИО и целевую сумму для создания нового ордера с предопределенными значениями для остальных полей (ID компании, фактическая сумма, валюта и т.д.).
//        // Бесконечный цикл для выполнения действий пользователя
        while (true) {
            Scanner scanner = new Scanner(System.in);

            // Выводим меню выбора действий
            System.out.println("Выберите действие:");
            System.out.println("1. Стать инвестором");
            System.out.println("2. Привлечь капитал");

            // Проверяем, введено ли целое число
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt(); // Читаем выбор пользователя
                scanner.nextLine(); // Очищаем буфер

                if (choice == 1) {
                    // Регистрация инвестора
                    InvestorModel investor = new InvestorModel();

                    System.out.println("Введите ФИО:");
                    investor.setFio(scanner.nextLine());
                    investor.setIin("DEFAULT_IIN"); // Дефолтное значение для ИИН
                    investor.setPhoneNumber("DEFAULT_PHONE"); // Дефолтное значение для номера телефона
                    investor.setMail("default@mail.com"); // Дефолтное значение для почты
                    investor.setAddress("DEFAULT_ADDRESS"); // Дефолтное значение для адреса
                    investor.setInvestorType("DEFAULT"); // Дефолтное значение для типа инвестора
                    investor.setCreatedAt(OffsetDateTime.now()); // Время создания записи
                    investor.setUpdatedAt(OffsetDateTime.now()); // Время обновления записи

                    investorRepo.save(investor); // Сохранение инвестора в репозитории
                    System.out.println("Инвестор успешно зарегистрирован!");

                    // Показать список всех ордеров
                    List<OrderModel> orders = orderRepo.findAll(); // Получаем все ордера из репозитория
                    System.out.println("Существующие ордера:");
                    for (OrderModel order : orders) {
                        // Выводим информацию о каждом ордере
                        System.out.println("ID: " + order.getId() + ", Целевая сумма: " + order.getTargetAmount());
                    }

                } else if (choice == 2) {
                    // Создание ордера
                    OrderModel order = new OrderModel();

                    System.out.println("Введите ФИО:");
                    String investorName = scanner.nextLine(); // Читаем ФИО инвестора

                    order.setCompanyId(1L); // Дефолтное значение для ID компании
                    System.out.println("Введите целевую сумму:");
                    order.setTargetAmount(scanner.nextBigDecimal()); // Читаем целевую сумму
                    scanner.nextLine(); // Очищаем буфер
                    order.setActualAmount(BigDecimal.ZERO); // Дефолтное значение для фактической суммы
                    order.setCurrency("USD"); // Дефолтное значение для валюты
                    order.setDateOfOrder(OffsetDateTime.now()); // Дата создания ордера
                    order.setDueDate(OffsetDateTime.now().plusDays(30)); // Дефолтное значение для срока погашения (через 30 дней)
                    order.setStatus("PENDING"); // Дефолтное значение для статуса ордера
                    order.setPurpose("DEFAULT_PURPOSE"); // Дефолтное значение для цели
                    order.setDescription("DEFAULT_DESCRIPTION"); // Дефолтное значение для описания
                    order.setCollateral("DEFAULT_COLLATERAL"); // Дефолтное значение для залога

                    orderRepo.save(order); // Сохранение ордера в репозитории
                    System.out.println("Ордер успешно создан!");
                } else {
                    System.out.println("Некорректный выбор. Попробуйте еще раз."); // Обработка некорректного ввода
                }
            }
        }
    }

}
