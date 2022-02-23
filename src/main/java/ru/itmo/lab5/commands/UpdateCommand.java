package ru.itmo.lab5.commands;

import java.util.Map.Entry;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class UpdateCommand implements Command {
    private CollectionControl collectionControl;

    public UpdateCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(String argument) {
        try {
            Integer ID = Integer.parseInt(argument);
            boolean wasFound = false;
            InsertCommand insert = new InsertCommand(collectionControl);
    
            for (Entry<Integer, Ticket> element : collectionControl.getCollection().entrySet()) {
                if (ID.equals(element.getValue().getId())) {
                    insert.createTicket(element.getKey());
                    insert.getTicket().setId(element.getValue().getId()); // возвращаем старый ID
                    insert.getTicket().setCreationDate(element.getValue().getCreationDate()); // возвращаем старую дату
                    collectionControl.getCollection().replace(element.getKey(), element.getValue(), insert.getTicket());
                    wasFound = true;
                    System.out.println("Элемент был успешно заменён");
                    break;
                }
            }
            if (!wasFound) {
                System.out.println("В коллекции нет элемента с таким ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("Значение ID элемента должно быть целым числом");
        }

    }
}