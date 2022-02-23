package ru.itmo.lab5.data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, описывающий спортивное событие
 */

 @Getter
 @Setter
public class Event {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime date; //Поле не может быть null
    private EventType eventType; //Поле не может быть null

    static private Long uniqueID = 1L;

    /**
     * Конструктор, задающий параметры объекта класса
     * @param name название
     * @param eventType тип события
     */

    public Event(String name, LocalDateTime date, EventType eventType) {
        this.id = uniqueID;
        uniqueID += 1L;
        this.name= name;
        this.date = date;
        this.eventType = eventType;
    }

    public Event(Long id, String name, LocalDateTime date, EventType eventType) {
        this.id = id;
        this.name= name;
        this.date = date;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", id, name, date.format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")), eventType);
    }
}