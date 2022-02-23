package ru.itmo.lab5.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.data.Ticket;
import lombok.Getter;

@Getter
public class CollectionControl {
    private Map<Integer, Ticket> collection = new HashMap<>();
    private String initDate;

    public CollectionControl() {
        // initDate = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(System.currentTimeMillis()));
        // initDate = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy").format(LocalDateTime.now());
        initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public void addValues(Map<Integer, Ticket> map) {
        collection.putAll(map);
    }
    
    public String csvString() {
        return "key,id,name,coordinates/x,coordinates/y,creationDate,price,type,event/id,event/name,event/date,event/eventType";
    }

    public boolean checkCommand(String[] choice) {
        if (choice.length != 1) {
            System.out.println("У данной команды нет аргументов");
            return false;
        }
        return true;
    }

    public boolean checkCommandWithArgument(String[] choice) {
        if (choice.length != 2) {
            System.out.println("У данной команды должен быть один аргумент");
            return false;
        }
        return true;
    }
}