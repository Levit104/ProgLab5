package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;

/**
 * Команда, очищающая коллекцию
 */

public class ClearCommand implements Command {
    CollectionControl collectionControl;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl менеджер коллекции
     * @see CollectionControl
     */

    public ClearCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
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
        if (collectionControl.getCollection().isEmpty()) {
            System.out.println("В коллекции нет элементов");
        } else {
            collectionControl.getCollection().clear();
            System.out.println("Коллекция успешно очищена");
        }
    }
}
