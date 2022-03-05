package ru.itmo.lab5.console;

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
    private boolean noScriptErrors;

    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;
        this.noScriptErrors = true;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean inScript() {
        return inScript;
    }

    public void setInScript(boolean inScript) {
        this.inScript = inScript;
    }

    public boolean noScriptErrors() {
        return noScriptErrors;
    }

    public void setNoScriptErrors(boolean noScriptErrors) {
        this.noScriptErrors = noScriptErrors;
    }

    /**
     * Создает новый билет, не добавляя его в коллекцию
     * @param key ключ
     * @return билет
     */

    public Ticket createTicket(Integer key) {
        String name = askName();
        Double coordinateX = askCoordinateX();
        Double coordinateY = askCoordinateY();
        double price = askPrice();
        TicketType ticketType = askTicketType();
        Event event = new Event(askEventName(), askEventTime(), askEventType());
        return new Ticket(key, name, new Coordinates(coordinateX, coordinateY), price, ticketType, event);
    }

    private String askName() {
        while (true) {
            if (!inScript) {
                System.out.print("Введите название билета: ");
            }
            String ticketName = scanner.nextLine();
            if (ticketName.trim().length() == 0) {
                System.out.println("Значение названия билета не может быть пустым и не должно содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            } else {
                return ticketName;
            }
        }
    }

    private Double askCoordinateX() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.print("Введите координату X (максимальное значение - 606): ");
                }
                Double ticketCoordinateX = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateX > 606) {
                    throw new NumberFormatException();
                } else {
                    return ticketCoordinateX;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение координаты X должно быть числом не больше 606 и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            }
        }
    }

    private Double askCoordinateY() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.print("Введите координату Y (максимальное значение - 483): ");
                }
                Double ticketCoordinateY = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateY > 483) {
                    throw new NumberFormatException();
                } else {
                    return ticketCoordinateY;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение координаты X должно быть числом не больше 483 и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            }
        }
    }

    private double askPrice() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.print("Введите цену билета (значение должно быть больше нуля): ");
                }
                double ticketPrice = Double.parseDouble(scanner.nextLine());
                if (ticketPrice <= 0) {
                    throw new NumberFormatException();
                } else {
                    return ticketPrice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение цены должно быть числом больше нуля и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return 0;
                }
            }
        }
    }

    private TicketType askTicketType() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.println("Возможные варианты: " + TicketType.valuesToString());
                    System.out.print("Введите тип билета: ");
                }
                TicketType ticketType = TicketType.valueOf(scanner.nextLine().toUpperCase());
                return ticketType;
            } catch (IllegalArgumentException e) {
                System.out.println("Значение \"тип билета\" должно соответствовать одному из допустимых типов и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            }
        }
    }

    private String askEventName() {
        while (true) {
            if (!inScript) {
                System.out.print("Введите название события: ");
            }
            String eventName = scanner.nextLine();
            if (eventName.isEmpty()) {
                System.out.println("Значение названия события не может быть пустым и не должно содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            } else {
                return eventName;
            }
        }
    }

    private LocalDateTime askEventTime() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.print("Введите дату в формате ЧЧ:мм:сс дд.ММ.гггг: ");
                }
                LocalDateTime date = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Значение даты должно строго соответствовать формату дату и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            }
        }
    }

    private EventType askEventType() {
        while (true) {
            try {
                if (!inScript) {
                    System.out.println("Возможные варианты: " + EventType.valuesToString());
                    System.out.print("Введите тип события: ");
                }
                EventType eventType = EventType.valueOf(scanner.nextLine().toUpperCase());
                return eventType;
            } catch (IllegalArgumentException e) {
                System.out.println("Значение \"тип события\" должно соответствовать одному из допустимых типов и не содержать пробелов");
                if (inScript) {
                    noScriptErrors = false;
                    return null;
                }
            }
        }
    }
}
