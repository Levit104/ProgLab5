package ru.itmo.lab5.modes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import ru.itmo.lab5.data.Coordinates;
import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.EventType;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.data.TicketType;

public class ConsoleManager {
    private Scanner scanner;
    private boolean inScript;

    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isInScript() {
        return inScript;
    }

    public void setInScript(boolean inScript) {
        this.inScript = inScript;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Создает новый билет, не добавляя его в коллекцию
     * @param key ключ
     */

    public Ticket createTicket(Integer key) {
        String name = askName();
        Double coordinateX = askCoordinateX();
        Double coordinateY = askCoordinateY();
        double price = askPrice();
        TicketType ticketType = askTicketType();
        Event event = null;
        
        if (askEvent()) {
            event = new Event(askEventName(), askEventTime(), askEventType());
        }
        
        return new Ticket(key, name, new Coordinates(coordinateX, coordinateY), price, ticketType, event);
    }

    private String askName() {
        while (true) {
            if (!inScript) System.out.print("Введите название билета: ");
            String ticketName = scanner.nextLine().trim();
            if (ticketName.isEmpty()) {
                System.out.println("Значение поля не может быть пустым");
            } else {
                return ticketName;
            }
        }
    }

    private Double askCoordinateX() {
        while (true) {
            try {
                if (!inScript) System.out.print("Введите координату X (максимальное значение - 606): ");
                Double ticketCoordinateX = Double.parseDouble(scanner.nextLine().trim());
                if (ticketCoordinateX > 606) {
                    System.out.println("Максимальное значение координаты - 606");
                } else {
                    return ticketCoordinateX;
                }
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("empty String")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Необходимо ввести одно число не больше 606");
                }
            }
        }
    }

    private Double askCoordinateY() {
        while (true) {
            try {
                if (!inScript) System.out.print("Введите координату Y (максимальное значение - 483): ");
                Double ticketCoordinateY = Double.parseDouble(scanner.nextLine().trim());
                if (ticketCoordinateY > 483) {
                    System.out.println("Максимальное значение координаты - 483");
                } else {
                    return ticketCoordinateY;
                }
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("empty String")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Необходимо ввести одно число не больше 483");
                }
            }
        }
    }

    private double askPrice() {
        while (true) {
            try {
                if (!inScript) System.out.print("Введите цену билета (значение должно быть больше нуля): ");
                double ticketPrice = Double.parseDouble(scanner.nextLine().trim());
                if (ticketPrice <= 0) {
                    System.out.println("Значение поля должно быть больше нуля");
                } else {
                    return ticketPrice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Необходимо ввести одно число больше нуля");
            }
        }
    }

    private TicketType askTicketType() {
        while (true) {
            try {
                if (!inScript) System.out.println("Возможные варианты: " + TicketType.valuesToString());
                if (!inScript) System.out.print("Введите тип билета: ");
                TicketType ticketType = TicketType.valueOf(scanner.nextLine().trim().toUpperCase());
                return ticketType;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals("No enum constant TicketType.")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Вы ввели недопустимый тип");
                }
            }
        }
    }

    private boolean askEvent() {
        while (true) {
            if (!inScript) System.out.print("Создать событие? (yes/no) ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("yes")) {
                return true;
            } else if (choice.equals("no")) {
                return false;
            } else {
                System.out.println("Вы ввели недопустимый ответ");
            }
        }
    }

    private String askEventName() {
        while (true) {
            if (!inScript) System.out.print("Введите название события: ");
            String eventName = scanner.nextLine().trim();
            if (eventName.isEmpty()) {
                System.out.println("Значение поля не может быть пустым");
            } else {
                return eventName;
            }
        }
    }

    private LocalDateTime askEventTime() {
        while (true) {
            try {
                if (!inScript) System.out.print("Введите дату в формате ЧЧ:мм:сс дд.ММ.гггг: ");
                LocalDateTime date = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Вы ввели недопустимый формат даты");
            }
        }
    }

    private EventType askEventType() {
        while (true) {
            try {
                if (!inScript) System.out.println("Возможные варианты: " + EventType.valuesToString());
                if (!inScript) System.out.print("Введите тип события: ");
                EventType eventType = EventType.valueOf(scanner.nextLine().trim().toUpperCase());
                return eventType;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals("No enum constant EventType.")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Вы ввели недопустимый тип");
                }
            }
        }
    }
}
