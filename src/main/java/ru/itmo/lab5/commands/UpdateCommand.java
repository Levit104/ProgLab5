package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.modes.ConsoleManager;

/**
 * Команда, заменяющая элемент по ID
 */

public class UpdateCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager коллекция (менеджер коллекции)
     * @see CollectionManager
     */

    public UpdateCommand(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
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

            for (Ticket oldTicket : collectionManager.getCollection().values()) {
                if (ID.equals(oldTicket.getId())) {
                    wasFound = true;
                    Ticket newTicket = consoleManager.createTicket(oldTicket.getKey());
                    newTicket.setId(oldTicket.getId());
                    newTicket.setCreationDate(oldTicket.getCreationDate());
                    
                    if (!consoleManager.inScript() || consoleManager.noScriptErrors()) {
                        collectionManager.getCollection().replace(oldTicket.getKey(), oldTicket, newTicket);
                        System.out.printf("Элемент с ID %d был успешно заменён%n", ID);
                    } else {
                        System.out.printf("Элемент с ID %d не был заменён%n", ID);
                        consoleManager.setNoScriptErrors(true);
                    }

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
