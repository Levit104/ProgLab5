package ru.itmo.lab5.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, описывающий спортивное событие
 */

public class Event {
    private static Long uniqueID = 1L;
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0,
                     // Значение этого поля должно быть уникальным,
                     // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private LocalDateTime date; // Поле не может быть null
    private EventType eventType; // Поле не может быть null

    /**
     * Конструктор, задающий параметры для создания события (ID устанавливается автоматически)
     * 
     * @param name название
     * @param date дата
     * @param eventType тип события
     * @see EventType
     */

    public Event(String name, LocalDateTime date, EventType eventType) {
        this.id = uniqueID;
        uniqueID += 1L;
        this.name = name;
        this.date = date;
        this.eventType = eventType;
    }

    /**
     * Конструктор, задающий параметры для создания события (ID устанавливается вручную)
     * 
     * @param id ID
     * @param name название
     * @param date дата
     * @param eventType тип события
     * @see EventType
     */

    public Event(Long id, String name, LocalDateTime date, EventType eventType) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", id, name, date.format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")), eventType);
    }
    
    /**
     * Возвращает ID события
     * 
     * @return ID
     */

    public Long getId() {
        return id;
    }

    /**
     * Устанавливает новый ID события
     * 
     * @param id новый ID
     */

    public void setId(Long id) {
        this.id = id;
    }
}