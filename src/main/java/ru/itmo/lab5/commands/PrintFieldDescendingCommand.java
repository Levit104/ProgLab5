package ru.itmo.lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class PrintFieldDescendingCommand implements Command {
    private CollectionControl collectionControl;

    public PrintFieldDescendingCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "print_field_descending_type";
    }

    @Override
    public String getDescription() {
        return " : вывести значения поля type всех элементов в порядке убывания";
    }

    @Override
    public void execute(String argument) {

        if (collectionControl.getCollection().isEmpty()) {
            System.out.println("В коллекции нет элементов");
        } else {
            List<String> typeList = new ArrayList<>();
            String typeString = "";
            
            for (Ticket ticket : collectionControl.getCollection().values()) {
                typeList.add(ticket.getType().toString());
            }
            
            Collections.sort(typeList);
            Collections.reverse(typeList);
            
            for (String type : typeList) {
                typeString += type + ", ";
            }
            
            System.out.println(typeString.substring(0, typeString.length() - 2));
        }
    }
}
