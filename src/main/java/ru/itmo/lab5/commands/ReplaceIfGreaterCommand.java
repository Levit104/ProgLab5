package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class ReplaceIfGreaterCommand implements Command {
    private CollectionControl collectionControl;

    public ReplaceIfGreaterCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "replace_if_greater";
    }

    @Override
    public String getDescription() {
        return " null {element} : заменить значение по ключу, если новое значение больше старого";
    }

    @Override
    public void execute(String argument) {
        try {
            boolean wasFound = false;
            Integer key = Integer.parseInt(argument.trim());
            InsertCommand insert = new InsertCommand(collectionControl);

            for (Ticket oldTicket : collectionControl.getCollection().values()) {
                if (key.equals(oldTicket.getKey())) {
                    wasFound = true;
                    insert.createTicket(key);
                    Ticket newTicket = insert.getTicket();
                    if (newTicket.compareTo(oldTicket) > 0) {
                        collectionControl.getCollection().replace(key, oldTicket, newTicket);
                        System.out.println("Элемент был успешно заменён");
                    } else {
                        System.out.println("Элемент не был заменён, т.к новое значение цены меньше старого");
                    }
                    break;
                }
            }

            if (!wasFound) {
                System.out.println("В коллекции нет элемента с заданным ключом");
            }

        } catch (NumberFormatException e) {
            System.out.println("Значение ключа должно быть целым числом");
        }
    }
}
