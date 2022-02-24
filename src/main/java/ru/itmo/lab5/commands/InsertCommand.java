package ru.itmo.lab5.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Coordinates;
import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.EventType;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.data.TicketType;
import ru.itmo.lab5.exception.WrongValueException;

public class InsertCommand implements Command {
    private Scanner scanner = new Scanner(System.in);
    private CollectionControl collectionControl;
    private Ticket ticket;

    public InsertCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return " null {element} : добавить новый элемент с заданным ключом";
    }

    @Override
    public void execute(String argument) {
        try {
            Integer key = Integer.parseInt(argument);
            if (!Ticket.checkTicketKey(key, collectionControl.getCollection())) throw new WrongValueException();
            createTicket(key);

            while (!Ticket.checkTicketID(ticket.getId(), collectionControl.getCollection())) {
                ticket.setId(ticket.getId() + 1);
            }
            
            if (ticket.getEvent() != null) {
                while (!Ticket.checkEventID(ticket.getEvent().getId(), collectionControl.getCollection())) {
                    ticket.setId(ticket.getId() + 1);
                }
            }
            
            collectionControl.getCollection().put(key, ticket);
            System.out.println("Элемент успешно добавлен");

        } catch (NumberFormatException e1) {
            System.out.println("Значение ключа должно быть целым числом");
        } catch (WrongValueException e2) {
            System.out.println("Элемент с таким ключом уже есть");
        }
    }

    public void createTicket(Integer key) {
        String name = askName();
        Double coordinateX = askCoordinateX();
        Double coordinateY = askCoordinateY();
        double price = askPrice();
        TicketType ticketType = askTicketType();
        Event event = null;
        if (askEvent()) {
            event = new Event(askEventName(), askEventTime(), askEventType());
        }
        ticket = new Ticket(key, name, new Coordinates(coordinateX, coordinateY), price, ticketType, event);
    }

    private String askName() {
        while (true) {
            System.out.print("Введите название билета: ");
            String ticketName = scanner.nextLine().trim();
            if (ticketName.isEmpty()) {
                System.out.println("Имя не может быть пустой строкой");
            } else {
                return ticketName;
            }
        }
    }

    private Double askCoordinateX() {
        while (true) {
            try {
                System.out.print("Введите координату X: ");
                Double ticketCoordinateX = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateX > 606) {
                    System.out.println("Максимальное значение координаты - 606");
                } else {
                    return ticketCoordinateX;
                }
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("empty String")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Необходимо ввести одно число");
                }
            }
        }
    }

    private Double askCoordinateY() {
        while (true) {
            try {
                System.out.print("Введите координату Y: ");
                Double ticketCoordinateY = Double.parseDouble(scanner.nextLine());
                if (ticketCoordinateY > 483) {
                    System.out.println("Максимальное значение координаты - 483");
                } else {
                    return ticketCoordinateY;
                }
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("empty String")) {
                    System.out.println("Значение поля не может быть пустым");
                } else {
                    System.out.println("Необходимо ввести одно число");
                }
            }
        }
    }

    private double askPrice() {
        while (true) {
            try {
                System.out.print("Введите цену билета: ");
                double ticketPrice = Double.parseDouble(scanner.nextLine());
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
                System.out.println("Возможные варианты: " + TicketType.valuesToString());
                System.out.print("Введите тип билета: ");
                TicketType ticketType = TicketType.valueOf(scanner.nextLine());
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
            System.out.print("Создать событие? (yes/no) ");
            String choice = scanner.nextLine();
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
            System.out.print("Введите название события: ");
            String eventName = scanner.nextLine().trim();
            if (eventName.isEmpty()) {
                System.out.println("Имя не может быть пустой строкой");
            } else {
                return eventName;
            }
        }
    }
  
    private LocalDateTime askEventTime() {
        while (true) {
            try {
                System.out.print("Введите дату в формате ЧЧ:мм:сс дд.ММ.гггг: ");
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
                System.out.println("Возможные варианты: " + EventType.valuesToString());
                System.out.print("Введите тип события: ");
                EventType eventType = EventType.valueOf(scanner.nextLine());
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