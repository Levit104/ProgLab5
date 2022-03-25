package ru.itmo.lab5.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.file.FileManager;

/**
 * Класс для работы с коллекцией (менеджер коллекции)
 */

public class CollectionManager {
    private Map<Integer, Ticket> collection;
    private String initDate;

    /**
     * Строка, содержащая все поля элемента коллекции (билета) в виде заголовка csv
     * файла
     */

    public static String csvString = "key,id,name,coordinates/x,coordinates/y,creationDate,price,type,event/id,event/name,event/date,event/eventType";

    /**
     * Конструктор, задающий параметры для создания объекта,
     * в момент создания устанавливается дата инициализации коллекции
     * 
     * @param fileManager менеджер файла
     * @see FileManager
     */

    public CollectionManager(FileManager fileManager) {
        collection = new HashMap<>();
        initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
        collection.putAll(fileManager.parseFile());
    }

    /**
     * Возвращает коллекцию, содержащую объекты класса {@link Ticket}
     * 
     * @return коллекция
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
     * Проверка ключа элемента (билета) на уникальность
     * 
     * @param key        ключ
     * @param collection коллекция, содержащая объекты класса {@link Ticket}
     * @return {@code true} если ключ уникальный, иначе {@code false}
     */

    public static boolean checkTicketKey(Integer key, Map<Integer, Ticket> collection) {
        if (collection.containsKey(key)) {
            return false;
        }
        return true;
    }

    /**
     * Проверка ID элемента (билета) на уникальность
     * 
     * @param ID         идентификатор
     * @param collection коллекция, содержащая объекты класса {@link Ticket}
     * @return {@code true} если ID уникальный, иначе {@code false}
     */

    public static boolean checkTicketID(Integer ID, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            if (ticket.getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверка ID события на уникальность
     * 
     * @param ID         идентификатор
     * @param collection коллекция, содержащая объекты класса {@link Ticket}
     * @return {@code true} если ID уникальный, иначе {@code false}
     * @see Event
     */

    public static boolean checkEventID(Long ID, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            Event event = ticket.getEvent();
            if (event.getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder ticketList = new StringBuilder(csvString);
        for (Ticket ticket : collection.values()) {
            ticketList.append("\n").append(ticket);
        }
        return ticketList.toString();
    }
}