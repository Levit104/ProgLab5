package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class ShowCommand implements Command {
    private CollectionControl collectionControl;

    public ShowCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(String argument) {
        if (collectionControl.getCollection().isEmpty()) {
            System.out.println("В коллекции нет элементов");
        } else {
            System.out.println(CollectionControl.csvString());
            for (Ticket ticket : collectionControl.getCollection().values()) {
                System.out.println(ticket);
            }
        }
    }
}
