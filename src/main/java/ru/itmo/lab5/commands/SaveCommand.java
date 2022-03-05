package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;

/**
 * Команда, записывающая коллекцию в файл .csv
 */

public class SaveCommand implements Command {
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return " (опционально - <file_name>) : сохранить коллекцию в файл";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            collectionManager.getFileManager().saveCollection(argument, collectionManager);
        }
    }
}
