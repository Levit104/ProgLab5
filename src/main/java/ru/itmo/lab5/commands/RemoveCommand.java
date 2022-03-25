package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда, удаляющая элемент из коллекции
 */

public class RemoveCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public RemoveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return " <key> : удалить элемент из коллекции по его ключу";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            try {
                Integer key = Integer.parseInt(argument);
                Ticket removedElement = collectionManager.getCollection().remove(key);
                StringBuilder keyList = new StringBuilder();

                for (Integer elemKey : collectionManager.getCollection().keySet()) {
                    keyList.append(elemKey).append(", ");
                }

                if (removedElement == null) {
                    System.out.printf("Элемента с ключом %d не существует. Все существующие ключи: %s%n",
                            key, keyList.substring(0, keyList.length() - 2));
                } else {
                    System.out.printf("Элемент с ключом %d успешно удалён%n", key);
                }
            } catch (NumberFormatException e) {
                System.out.printf("Нельзя выполнить команду %s: значение ключа должно быть целым числом%n", getName());
            }
        }
    }
}
