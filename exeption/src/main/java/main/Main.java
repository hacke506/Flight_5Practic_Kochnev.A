package main;

// Импорт необходимых классов исключений из пакета com.example.exception
import com.example.exception.BaggageLimitException; // Исключение: Превышение лимита багажа
import com.example.exception.FlightValidationExpection; // Исключение: Ошибка валидации рейса
import com.example.exception.OverbookingException; // Исключение: Переполнение рейса (овербукинг)

import java.time.LocalDate; // Класс для работы с датами (без времени)
import java.util.Scanner; // Класс для считывания ввода пользователя из консоли

// Импорт всех моделей (Aircraft, Airport, Passenger, Booking и т.д.)
import model.*;
// Импорт менеджера полетов для управления бизнес-логикой
import service.FlightManager;
// Импорт классов хранилищ данных
import storage.BookingStorage; // Хранилище бронирований
import storage.FlightStorage; // Хранилище рейсов
import storage.PassangerStorage; // Хранилище пассажиров

public class Main {
    public static void main(String[] args) {
        // --- INITIALIZATION OF STORAGES (Инициализация хранилищ данных) ---
        FlightStorage flightStorage = new FlightStorage();
        BookingStorage bookingStorage = new BookingStorage();
        PassangerStorage passengerStorage = new PassangerStorage();
        FlightManager flightManager = new FlightManager(flightStorage, bookingStorage);

        // --- POPULATING WITH TEST DATA (Наполнение системы тестовыми данными) ---
        Airport jfk = new Airport("JFK", "New York"); // Аэропорт Кеннеди в Нью-Йорке
        Airport lhr = new Airport("LHR", "London"); // Аэропорт Хитроу в Лондоне

        // Самолет с маленькой вместимостью (2 места) специально для проверки овербукинга
        Aircraft smallPlane = new Aircraft("N123AA", "Boeing 737", 2);

        // Создание тестового рейса AA100 (Нью-Йорк -> Лондон, вылет через 2 дня, базовая цена $500)
        Flight flight1 = new Flight("AA100", jfk, lhr, LocalDate.now().plusDays(2), smallPlane, 500.0);
        flightStorage.add(flight1); // Добавление рейса в базу данных

        // Создание трех тестовых пассажиров
        Passenger p1 = new Passenger("AB12345", "John Doe", 0); // Пассажир Джон Доу, 0 миль
        Passenger p2 = new Passenger("CD67890", "Alice Smith", 100); // Пассажирка Элис Смит, 100 миль
        Passenger p3 = new Passenger("EF55555", "Bob Johnson", 0); // Пассажир Боб Джонсон, 0 миль

        // Добавление пассажиров в базу данных авиакомпании
        passengerStorage.add(p1);
        passengerStorage.add(p2);
        passengerStorage.add(p3);

        Scanner scanner = new Scanner(System.in); // Создание сканера для чтения команд пользователя

        // Сообщение: "СИСТЕМА УПРАВЛЕНИЯ АВИАКОМПАНИЕЙ УСПЕШНО ЗАПУЩЕНА"
        System.out.println("=== AIRLINE MANAGEMENT SYSTEM INITIALIZED ===");

        // --- MAIN PROGRAM LOOP (Главный бесконечный цикл программы) ---
        while (true) {
            System.out.println("\n--- MENU ---"); // Вывод заголовка: "--- МЕНЮ ---"
            System.out.println("1. Search & View Flights"); // 1. Поиск и просмотр рейсов
            System.out.println("2. Book a Flight"); // 2. Забронировать рейс (купить билет)
            System.out.println("3. Passenger Check-in & Baggage Registration"); // 3. Регистрация пассажира и багажа
            System.out.println("4. Update Flight Status (Notifications)"); // 4. Обновить статус рейса (с отправкой уведомлений)
            System.out.println("5. Exit"); // 5. Выход из программы
            System.out.print("Choose an option: "); // Запрос: "Выберите пункт меню: "

            int choice = scanner.nextInt(); // Считывание выбора пользователя (число)
            scanner.nextLine(); // Очистка буфера строки после считывания числа

            switch (choice) {
                case 1: // --- VIEW FLIGHTS (ПРОСМОТР РЕЙСОВ) ---
                    System.out.println("\nAvailable Flights:"); // Вывод: "Доступные рейсы:"
                    for (int i = 0; i < flightStorage.getSize(); i++) {
                        Flight f = flightStorage.getByIndex(i);
                        // Вычисление динамического изменения цены (зависит от заполненности самолета)
                        double modifier = flightManager.calculateDynamicModifier(f);
                        // Вывод информации о рейсе и текущего множителя цены
                        System.out.println(f + " | Current Price Multiplier: x" + modifier);
                    }
                    break;

                case 2: // --- BOOK A FLIGHT (БРОНИРОВАНИЕ РЕЙСА) ---
                    System.out.print("Enter Flight Number (e.g., AA100): "); // Запрос: "Введите номер рейса (например, AA100): "
                    String fNum = scanner.nextLine();
                    Flight selectedFlight = flightStorage.getByNumber(fNum); // Поиск рейса в базе

                    if (selectedFlight == null) {
                        System.out.println("Flight not found!"); // Вывод: "Рейс не найден!"
                        break;
                    }

                    System.out.print("Enter Passenger Passport ID: "); // Запрос: "Введите серию и номер паспорта пассажира: "
                    String passport = scanner.nextLine();
                    Passenger passenger = passengerStorage.getByPassport(passport); // Поиск пассажира

                    if (passenger == null) {
                        System.out.println("Passenger not found in storage!"); // Вывод: "Пассажир не найден в базе данных!"
                        break;
                    }

                    System.out.print("Enter Class (Economy / Business / First): "); // Запрос: "Введите класс обслуживания (Economy / Business / First): "
                    String cls = scanner.nextLine();

                    try {
                        // Попытка забронировать место на рейсе
                        Booking b = flightManager.createBooking(selectedFlight, passenger, cls);
                        double modifier = flightManager.calculateDynamicModifier(selectedFlight);

                        System.out.println("Success! Booking Created ID: " + b.getId()); // Вывод: "Успешно! Создано бронирование с ID: ..."
                        System.out.printf("Final Price Paid: $%.2f\n", b.getTicket().calculateFinalPrice(modifier)); // Вывод: "Итоговая оплаченная стоимость: $..."
                        System.out.println("Updated Passenger Miles: " + passenger.getBonusMiles()); // Вывод: "Текущий баланс бонусных миль пассажира: ..."
                    } catch (OverbookingException e) {
                        // Перехват ошибки, если на рейс продано слишком много билетов (>105% от вместимости)
                        System.out.println("Error: " + e.getMessage()); // Вывод сообщения об ошибке овербукинга
                    }
                    break;

                case 3: // --- BAGGAGE REGISTRATION (РЕГИСТРАЦИЯ БАГАЖА) ---
                    System.out.print("Enter Booking ID (e.g., BKG-1001): "); // Запрос: "Введите ID бронирования (например, BKG-1001): "
                    String bId = scanner.nextLine();
                    System.out.print("Enter Baggage Weight (kg): "); // Запрос: "Введите вес багажа в килограммах: "
                    double weight = scanner.nextDouble();
                    System.out.print("Is it fragile? (true/false): "); // Запрос: "Багаж хрупкий? (введите true или false): "
                    boolean fragile = scanner.nextBoolean();

                    try {
                        // Регистрация багажа и расчет доплаты за перевес
                        double fee = flightManager.registerBaggage(bId, weight, fragile);
                        System.out.println("Check-in successful!"); // Вывод: "Регистрация на рейс успешно завершена!"
                        System.out.printf("Overweight Baggage Penalty Fee: $%.2f\n", fee); // Вывод: "Штраф/доплата за избыточный вес багажа: $..."
                    } catch (BaggageLimitException e) {
                        // Перехват ошибки, если вес критически превышает норму класса билета (+15 кг)
                        System.out.println("Error: " + e.getMessage()); // Вывод сообщения о нарушении лимитов веса
                    }
                    break;

                case 4: // --- UPDATE FLIGHT STATUS (ОБНОВЛЕНИЕ СТАТУСА РЕЙСА) ---
                    System.out.print("Enter Flight Number: "); // Запрос: "Введите номер рейса: "
                    String flightNo = scanner.nextLine();
                    System.out.print("Enter New Status (SCHEDULED / DELAYED / CANCELLED): "); // Запрос: "Введите новый статус (SCHEDULED - Запланирован / DELAYED - Задержан / CANCELLED - Отменен): "
                    String statusStr = scanner.nextLine();

                    try {
                        // Преобразование текстового ввода в системный элемент перечисления FlightStatus
                        FlightStatus status = FlightStatus.valueOf(statusStr.toUpperCase());
                        // Изменение статуса рейса с автоматическим выводом оповещений пассажирам в консоль
                        flightManager.changeFlightStatus(flightNo, status);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status entered!"); // Вывод: "Введен некорректный статус рейса!"
                    } catch (FlightValidationExpection e) {
                        // Перехват ошибки, если ввели номер несуществующего рейса
                        System.out.println("Error: " + e.getMessage()); // Вывод сообщения об ошибке валидации
                    }
                    break;

                case 5: // --- EXIT SYSTEM (ВЫХОД ИЗ СИСТЕМЫ) ---
                    System.out.println("Exiting System. Goodbye!"); // Вывод: "Выход из системы. До свидания!"
                    scanner.close(); // Закрытие потока считывания данных
                    return; // Завершение выполнения метода main и закрытие программы

                default: // --- INVALID CHOICE (НЕКОРРЕКТНЫЙ ВВОД ПУНКТА МЕНЮ) ---
                    System.out.println("Invalid choice. Try again."); // Вывод: "Неверный выбор. Пожалуйста, попробуйте снова."
            }
        }
    }
}
