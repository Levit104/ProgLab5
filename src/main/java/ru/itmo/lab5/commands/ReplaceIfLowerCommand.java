package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;
import ru.itmo.lab5.modes.ConsoleManager;

/**
 * Команда, заменяющая элемент, если цена нового элемента меньше цены старого
 */

public class ReplaceIfLowerCommand implements Command {
    private CollectionControl collectionControl;
    private ConsoleManager consoleManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl коллекция (менеджер коллекции)
     * @see CollectionControl
     */

    public ReplaceIfLowerCommand(CollectionControl collectionControl, ConsoleManager consoleManager) {
        this.collectionControl = collectionControl;
        this.consoleManager = consoleManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "replace_if_lower";
    }

    @Override
    public String getDescription() {
        return " null {element} : заменить значение по ключу, если новое значение меньше старого";
    }

    @Override
    public void execute(String argument) {
        try {
            boolean wasFound = false;
            Integer key = Integer.parseInt(argument.trim());

            for (Ticket oldTicket : collectionControl.getCollection().values()) {
                if (key.equals(oldTicket.getKey())) {
                    wasFound = true;
                    Ticket newTicket = consoleManager.createTicket(key);
                    if (newTicket.compareTo(oldTicket) < 0) {
                        collectionControl.getCollection().replace(key, oldTicket, newTicket);
                        System.out.printf("Элемент с ключом %d был успешно заменён%n", key);
                    } else {
                        System.out.printf("Элемент с ключом %d не был заменён, т.к новое значение цены больше старого%n", key);
                    }
                    break;
                }
            }

            if (!wasFound) {
                System.out.printf("Элемента с ключом %d не существует%n", key);
            }

        } catch (NumberFormatException e) {
            System.out.printf("Нельзя выполнить команду %s: значение ключа должно быть целым числом%n", getName());
        }
    }
}
