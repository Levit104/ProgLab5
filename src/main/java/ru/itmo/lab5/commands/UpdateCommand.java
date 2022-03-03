package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.modes.ConsoleManager;

/**
 * Команда, заменяющая элемент по ID
 */

public class UpdateCommand implements Command {
    private CollectionControl collectionControl;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl коллекция (менеджер коллекции)
     * @see CollectionControl
     */

    public UpdateCommand(CollectionControl collectionControl, ConsoleManager consoleManager) {
        this.collectionControl = collectionControl;
        this.consoleManager = consoleManager;
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

            for (Ticket oldTicket : collectionControl.getCollection().values()) {
                if (ID.equals(oldTicket.getId())) {
                    wasFound = true;
                    Ticket newTicket = consoleManager.createTicket(oldTicket.getKey());
                    newTicket.setId(oldTicket.getId());
                    newTicket.setCreationDate(oldTicket.getCreationDate());
                    collectionControl.getCollection().replace(oldTicket.getKey(), oldTicket, newTicket);
                    System.out.printf("Элемент с ID %d был успешно заменён%n", ID);
                    break;
                }
            }

            if (!wasFound) {
                System.out.printf("Элемента с ID %d не существует%n", ID);
            }
            
        } catch (NumberFormatException e) {
            System.out.printf("Нельзя выполнить команду %s: значение ID должно быть целым числом%n", getName());
        }
    }
}
