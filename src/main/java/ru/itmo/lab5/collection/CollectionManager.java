package ru.itmo.lab5.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.data.Ticket;

/**
 * Управление коллекцией (менеджер коллекции)
 */

public class CollectionControl {
    private Map<Integer, Ticket> collection = new HashMap<>();
    private String initDate;

    /**
     * Конструктор по-умолчанию, в момент создания устанавливается дата инициализации коллекции
     */

    public CollectionControl() {
        initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    /**
     * Возвращает коллекцию, содержащую объекты класса Ticket
     * 
     * @return коллекция
     * @see Ticket
     */

    public Map<Integer, Ticket> getCollection() {
        return collection;
    }

    /**
     * Возвращает дату инициализации коллекции в строковом представлении
     * 
     * @return дата инициализации
     */

    public String getInitDate() {
        return initDate;
    }

    /**
     * Добавляет элементы из другой коллекции того же типа
     * 
     * @param map коллекция
     */

    public void addFromMap(Map<Integer, Ticket> map) {
        collection.putAll(map);
    }

    /**
     * Возвращает строку, содержащую все поля элемента коллекции в виде csv
     * 
     * @return строка в виде csv
     */

    public static String csvString() {
        return "key,id,name,coordinates/x,coordinates/y,creationDate,price,type,event/id,event/name,event/date,event/eventType";
    }
}