package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.console.ConsoleManager;

/**
 * Команда, заменяющая элемент по ID
 */

public class UpdateCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @param consoleManager    менеджер консоли
     * @see CollectionManager
     * @see ConsoleManager
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
        return " <id> : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            try {
                boolean wasFound = false;
                Integer ID = Integer.parseInt(argument);
                StringBuilder idList = new StringBuilder();

                for (Ticket ticket : collectionManager.getCollection().values()) {
                    idList.append(ticket.getId()).append(", ");
                }

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
                    System.out.printf("Элемента с ID %d не существует. Все существующие ID: %s%n",
                            ID, idList.substring(0, idList.length() - 2));
                }

            } catch (NumberFormatException e) {
                System.out.printf("Нельзя выполнить команду %s: значение ID должно быть целым числом%n", getName());
            }
        }
    }
}
