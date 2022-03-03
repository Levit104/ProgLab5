package ru.itmo.lab5.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.parser.FileManager;

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

    @Override
    public String toString() {
        String ticketList = csvString();
        for (Ticket ticket : collection.values()) {
            ticketList += "\n" + ticket;
        }
        return ticketList;
    }
}