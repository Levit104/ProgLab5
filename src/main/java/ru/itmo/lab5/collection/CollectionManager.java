package ru.itmo.lab5.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.file.FileManager;

/**
 * Управление коллекцией (менеджер коллекции)
 */

public class CollectionManager {
    private Map<Integer, Ticket> collection;
    private String initDate;
    private FileManager fileManager;

    /**
     * Конструктор по-умолчанию, в момент создания устанавливается дата инициализации коллекции
     */

    public CollectionManager(FileManager fileManager) {
        this.collection = new HashMap<>();
        this.initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
        this.fileManager = fileManager;
        collection.putAll(fileManager.parseFile());;
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

    public FileManager getFileManager() {
        return fileManager;
    }

    /**
     * Возвращает строку, содержащую все поля элемента коллекции в виде csv
     * 
     * @return строка в виде csv
     */

    public static String csvString() {
        return "key,id,name,coordinates/x,coordinates/y,creationDate,price,type,event/id,event/name,event/date,event/eventType";
    }

    /**
     * Проверка ключа билета на уникальность
     * 
     * @param key        ключ
     * @param collection коллекция, содержащая объекты класса Ticket
     * @return {@code true} если ключ уникальный, иначе {@code false} 
     * @see Ticket
     */

    public static boolean checkTicketKey(Integer key, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            if (ticket.getKey().equals(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверка ID билета на уникальность
     * 
     * @param ID         идентификатор
     * @param collection коллекция, содержащая объекты класса Ticket
     * @return {@code true} если ID уникальный, иначе {@code false} 
     * @see Ticket
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
     * @param collection коллекция, содержащая объекты класса Ticket
     * @return {@code true} если ключ уникальный, иначе {@code false} 
     */

    public static boolean checkEventID(Long ID, Map<Integer, Ticket> collection) {
        for (Ticket ticket : collection.values()) {
            Event event = ticket.getEvent();
            if (event != null && event.getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String ticketList = csvString();
        for (Ticket ticket : collection.values()) {
            ticketList += "\n" + ticket;
        }
        return ticketList;
    }
}