package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда, заменяющая элемент по ID
 */

public class UpdateCommand implements Command {
    private CollectionControl collectionControl;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl коллекция (менеджер коллекции)
     * @see CollectionControl
     */

    public UpdateCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return true;
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
            boolean wasFound = false;
            Integer ID = Integer.parseInt(argument);
            InsertCommand insert = new InsertCommand(collectionControl);

            for (Ticket oldTicket : collectionControl.getCollection().values()) {
                if (ID.equals(oldTicket.getId())) {
                    insert.createTicket(oldTicket.getKey());
                    Ticket newTicket = insert.getTicket();
                    newTicket.setId(oldTicket.getId());
                    newTicket.setCreationDate(oldTicket.getCreationDate());
                    collectionControl.getCollection().replace(oldTicket.getKey(), oldTicket, newTicket);
                    wasFound = true;
                    System.out.println("Элемент был успешно заменён");
                    break;
                }
            }

            if (!wasFound) {
                System.out.println("В коллекции нет элемента с заданным ID");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Значение ID должно быть целым числом");
        }
    }
}
