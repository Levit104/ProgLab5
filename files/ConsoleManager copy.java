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
    private boolean noErrors;

    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;
        this.noErrors = true;
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

    public boolean noErrors() {
        return noErrors;
    }

    public void setNoErrors(boolean noErrors) {
        this.noErrors = noErrors;
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
        Event event = null;
        
        if (askEvent()) {
            event = new Event(askEventName(), askEventTime(), askEventType());
        }
        
        return new Ticket(key, name, new Coordinates(coordinateX, coordinateY), price, ticketType, event);
    }

    private String askName() {
        while (true) {
            if (inScript && !noErrors) {
                return null;
            }
            if (!inScript) {
                System.out.print("Введите название билета: ");
            }
            String ticketName = scanner.nextLine();
            if (ticketName.isEmpty()) {
                System.out.println("Значение названия билета не может быть пустым и не должно содержать пробелов");
                noErrors = false;
            } else {
                noErrors = true;
                return ticketName;
            }
        }
    }

    private Double askCoordinateX() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return null;
                }
                if (!inScript) {
                    System.out.print("Введите координату X (максимальное значение - 606): ");
                }
                Double ticketCoordinateX = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateX > 606) {
                    throw new NumberFormatException();
                } else {
                    noErrors = true;
                    return ticketCoordinateX;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение координаты X должно быть числом не больше 606 и не содержать пробелов");
                noErrors = false;
            }
        }
    }

    private Double askCoordinateY() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return null;
                }
                if (!inScript) {
                    System.out.print("Введите координату Y (максимальное значение - 483): ");
                }
                Double ticketCoordinateY = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateY > 483) {
                    throw new NumberFormatException();
                } else {
                    noErrors = true;
                    return ticketCoordinateY;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение координаты X должно быть числом не больше 483 и не содержать пробелов");
                noErrors = false;
            }
        }
    }

    private double askPrice() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return 0;
                }
                if (!inScript) {
                    System.out.print("Введите цену билета (значение должно быть больше нуля): ");
                }
                double ticketPrice = Double.parseDouble(scanner.nextLine());
                if (ticketPrice <= 0) {
                    throw new NumberFormatException();
                } else {
                    noErrors = true;
                    return ticketPrice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Значение цены должно быть числом больше нуля и не содержать пробелов");
                noErrors = false;
            }
        }
    }

    private TicketType askTicketType() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return null;
                }
                if (!inScript) {
                    System.out.println("Возможные варианты: " + TicketType.valuesToString());
                    System.out.print("Введите тип билета: ");
                }
                TicketType ticketType = TicketType.valueOf(scanner.nextLine().toUpperCase());
                noErrors = true;
                return ticketType;
            } catch (IllegalArgumentException e) {
                System.out.println("Значение \"тип билета\" должно соответствовать одному из допустимых типов и не содержать пробелов");
                noErrors = false;
            }
        }
    }

    private boolean askEvent() {
        while (true) {
            if (inScript && !noErrors) {
                return false;
            }
            if (!inScript) {
                System.out.print("Создать событие? (yes/no) ");
            }
            String choice = scanner.nextLine();
            if (choice.equals("yes")) {
                noErrors = true;
                return true;
            } else if (choice.equals("no")) {
                noErrors = true;
                return false;
            } else {
                System.out.println("Возможны только варианты (yes/no) без пробелов");
                noErrors = false;
            }
        }
    }

    private String askEventName() {
        while (true) {
            if (inScript && !noErrors) {
                return null;
            }
            if (!inScript) {
                System.out.print("Введите название события: ");
            }
            String eventName = scanner.nextLine();
            if (eventName.isEmpty()) {
                System.out.println("Значение названия события не может быть пустым и не должно содержать пробелов");
                noErrors = false;
            } else {
                noErrors = true;
                return eventName;
            }
        }
    }

    private LocalDateTime askEventTime() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return null;
                }
                if (!inScript) {
                    System.out.print("Введите дату в формате ЧЧ:мм:сс дд.ММ.гггг: ");
                }
                LocalDateTime date = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
                noErrors = true;
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Значение даты должно строго соответствовать формату дату и не содержать пробелов");
                noErrors = false;
            }
        }
    }

    private EventType askEventType() {
        while (true) {
            try {
                if (inScript && !noErrors) {
                    return null;
                }
                if (!inScript) {
                    System.out.println("Возможные варианты: " + EventType.valuesToString());
                    System.out.print("Введите тип события: ");
                }
                EventType eventType = EventType.valueOf(scanner.nextLine().toUpperCase());
                noErrors = true;
                return eventType;
            } catch (IllegalArgumentException e) {
                System.out.println("Значение \"тип события\" должно соответствовать одному из допустимых типов и не содержать пробелов");
                noErrors = false;
            }
        }
    }
}
