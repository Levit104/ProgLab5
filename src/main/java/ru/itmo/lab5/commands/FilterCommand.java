package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class FilterCommand implements Command {
    private CollectionControl collectionControl;

    public FilterCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " name : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public void execute(String argument) {
        if (collectionControl.getCollection().isEmpty()) {
            System.out.println("В коллекция нет элементов");
        } else {
            String ticketList = "";
            
            for (Ticket ticket : collectionControl.getCollection().values()) {
                if (ticket.getName().startsWith(argument.trim())) {
                    ticketList += ticket + "\n";
                }
            }
            
            if (ticketList.isEmpty()) {
                System.out.println("В коллекции нет элементов, имя которых начинается на " + argument.trim());
            } else {
                System.out.println(ticketList.substring(0, ticketList.length() - 1));
            }
        }
    }
}
