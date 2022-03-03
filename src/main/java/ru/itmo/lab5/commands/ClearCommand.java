package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;

/**
 * Команда, очищающая коллекцию
 */

public class ClearCommand implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return " : очистить коллекцию";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            collectionManager.getCollection().clear();
            System.out.println("Коллекция успешно очищена");
        }
    }
}
