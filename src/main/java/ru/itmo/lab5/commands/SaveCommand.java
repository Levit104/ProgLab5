package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.file.FileManager;

/**
 * Команда, записывающая коллекцию в файл .csv
 */

public class SaveCommand implements Command {
    private CollectionManager collectionManager;
    private FileManager fileManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @param fileManager       менеджер файла
     * @see CollectionManager
     * @see FileManager
     */

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
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
            fileManager.saveCollection(argument, collectionManager);
        }
    }
}
