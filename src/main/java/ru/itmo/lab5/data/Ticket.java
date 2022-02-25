package ru.itmo.lab5.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Класс, описывающий билет на спортивное событие.
 * Объекты класса являются элементами коллекции
 */

public class Ticket implements Comparable<Ticket> {
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0,
                        // Значение этого поля должно быть уникальным,
                        // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; // Значение поля должно быть больше 0
    private TicketType type; // Поле не может быть null
    private Event event; // Поле может быть null

    private Integer key;
    static private Integer uniqueID = 1;

    /**
     * Конструктор, задающий параметры объекта класса
     * 
     * @param id          ID
     * @param name        название
     * @param coordinates координаты
     * @param price       цена
     * @param type        тип билета
     * @param event       событие
     */

    public Ticket(Integer key, String name, Coordinates coordinates, double price, TicketType type, Event event) {
        this.key = key;
        this.id = uniqueID;
        uniqueID += 1;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date(System.currentTimeMillis());
        this.price = price;
        this.type = type;
        this.event = event;
    }

    public Ticket(Integer key, Integer id, String name, Coordinates coordinates, 
                  Date creationDate, double price, TicketType type, Event event) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.type = type;
        this.event = event;
    }

    @Override
    public String toString() {
        if (event == null) {
            return String.format("%s,%s,%s,%s,%s,%s,%s",
                    key, id, name, coordinates, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(creationDate), price, type);
        }

        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                key, id, name, coordinates, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(creationDate), price, type, event);
    }

    @Override
    public int compareTo(Ticket ticket) {
        return Double.compare(this.price, ticket.price);
    }

    public static boolean checkTicketID(Integer ID, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            if (ticket.getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkEventID(Long ID, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            if (ticket.getEvent().getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTicketKey(Integer key, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            if (ticket.getKey().equals(key)) {
                return false;
            }
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public TicketType getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }
}