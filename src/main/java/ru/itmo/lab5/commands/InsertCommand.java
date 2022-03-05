package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.exceptions.NotUniqueValueException;
import ru.itmo.lab5.console.ConsoleManager;

/**
 * Команда, добавляющая элемент в коллекцию
 */

public class InsertCommand implements Command {
    private CollectionManager collectionManager;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public InsertCommand(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return " <key> : добавить новый элемент с заданным ключом";
    }

    @Override
    public void execute(String argument) {
        Integer key = null;
        try {
            key = Integer.parseInt(argument);

            if (!CollectionManager.checkTicketKey(key, collectionManager.getCollection())) {
                throw new NotUniqueValueException();
            }

            Ticket ticket = consoleManager.createTicket(key);

            while (!CollectionManager.checkTicketID(ticket.getId(), collectionManager.getCollection())) {
                ticket.setId(ticket.getId() + 1);
            }

            while (!CollectionManager.checkEventID(ticket.getEvent().getId(), collectionManager.getCollection())) {
                ticket.getEvent().setId(ticket.getEvent().getId() + 1);
            }

            if (!consoleManager.inScript() || consoleManager.noScriptErrors()) {
                collectionManager.getCollection().put(key, ticket);
                System.out.printf("Элемент с ключом %d успешно добавлен%n", key);
            } else {
                System.out.printf("Элемент с ключом %d не был добавлен%n", key);
                consoleManager.setNoScriptErrors(true);
            }

        } catch (NumberFormatException e1) {
            System.out.printf("Нельзя выполнить команду %s: значение ключа должно быть целым числом%n", getName());
        } catch (NotUniqueValueException e2) {
            System.out.printf("Нельзя выполнить команду %s: элемент с ключом %d уже существует%n", getName(), key);
        }
    }
}