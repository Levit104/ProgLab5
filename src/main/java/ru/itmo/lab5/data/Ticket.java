package ru.itmo.lab5.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, описывающий билет на спортивное событие.
 * Объекты класса являются элементами коллекции
 */

public class Ticket implements Comparable<Ticket> {
    private static Integer uniqueID = 1;
    private Integer key;
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0,
                        // Значение этого поля должно быть уникальным,
                        // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; // Значение поля должно быть больше 0
    private TicketType type; // Поле не может быть null
    private Event event; // Поле может быть null

    /**
    * Конструктор, задающий параметры для создания билета (ID и дата создания устанавливаются автоматически)
    * 
    * @param key         ключ
    * @param name        название
    * @param coordinates координаты
    * @param price       цена
    * @param type        тип билета
    * @param event       событие
    * @see Coordinates
    * @see TicketType
    * @see Event
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

    /**
     * Конструктор, задающий параметры для создания билета (ID и дата создания устанавливаются вручную)
     * 
     * @param key          ключ
     * @param id           ID
     * @param name         название
     * @param coordinates  координаты
     * @param creationDate дата создания
     * @param price        цена
     * @param type         тип билета
     * @param event        событие
     * @see Coordinates
     * @see TicketType
     * @see Event
     */

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

    /**
     * Возвращает ID билета
     * 
     * @return ID
     */

    public Integer getId() {
        return id;
    }

    /**
     * Устанавливает новый ID билета
     * 
     * @param id новый ID
     */

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Возвращает дату создания билета
     * 
     * @return дата создания
     */

    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания билета
     * 
     * @param creationDate новая дата создания
     */

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает ключ билета
     * 
     * @return ключ
     */

    public Integer getKey() {
        return key;
    }

    /**
     * Возвращает название билета
     * 
     * @return название
     */

    public String getName() {
        return name;
    }

    /**
     * Возвращает тип билета
     * 
     * @return тип билета
     * @see TicketType
     */

    public TicketType getType() {
        return type;
    }

    /**
     * Возвращает событие, связанное с билетом
     * 
     * @return событие
     * @see Event
     */

    public Event getEvent() {
        return event;
    }
}