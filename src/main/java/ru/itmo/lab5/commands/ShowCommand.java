package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;

/**
 * Команда, выводящая элементы на экран
 */

public class ShowCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            System.out.println("Все элементы коллекции: ");
            System.out.println(collectionManager.toString());
        }
    }
}
