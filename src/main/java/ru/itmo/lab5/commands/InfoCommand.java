package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;

/**
 * Команда, выводящая информацию о коллекции
 */

public class InfoCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)";
    }

    @Override
    public void execute(String argument) {
        System.out.println("Коллекция типа HashMap, хранящая объекты класса Ticket" +
                           "\nДата инициализации: " + collectionManager.getInitDate() + 
                           "\nКол-во элементов: " + collectionManager.getCollection().size()
                           );
    }
}
