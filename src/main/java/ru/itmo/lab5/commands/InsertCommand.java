package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Event;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.exceptions.NotUniqueValueException;
import ru.itmo.lab5.modes.ConsoleManager;

/**
 * Команда, добавляющая элемент в коллекцию
 */

public class InsertCommand implements Command {
    private CollectionControl collectionControl;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl менеджер коллекции
     * @see CollectionControl
     */

    public InsertCommand(CollectionControl collectionControl, ConsoleManager consoleManager) {
        this.collectionControl = collectionControl;
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
        return " null {element} : добавить новый элемент с заданным ключом";
    }

    @Override
    public void execute(String argument) {
        Integer key = null;
        try {
            key = Integer.parseInt(argument);

            if (!Ticket.checkKey(key, collectionControl.getCollection())) {
                throw new NotUniqueValueException();
            }

            Ticket ticket = consoleManager.createTicket(key);

            while (!Ticket.checkID(ticket.getId(), collectionControl.getCollection())) {
                ticket.setId(ticket.getId() + 1);
            }

            if (ticket.getEvent() != null) {
                while (!Event.checkID(ticket.getEvent().getId(), collectionControl.getCollection())) {
                    ticket.getEvent().setId(ticket.getEvent().getId() + 1);
                }
            }

            collectionControl.getCollection().put(key, ticket);
            System.out.printf("Элемент с ключом %d успешно добавлен%n", key);

        } catch (NumberFormatException e1) {
            System.out.printf("Нельзя выполнить команду %s: значение ключа должно быть целым числом%n", getName());
        } catch (NotUniqueValueException e2) {
            System.out.printf("Нельзя выполнить команду %s: элемент с ключом %d уже существует%n", getName(), key);
        }
    }
}