package ru.itmo.lab5.commands;

import java.util.Iterator;

import ru.itmo.lab5.collection.CollectionManager;

/**
 * Команда, удаляющая элемент из коллекции, если его ключ меньше того, что введет пользователь
 */

public class RemoveLowerKeyCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager коллекция (менеджер коллекции)
     * @see CollectionManager
     */

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_lower_key";
    }

    @Override
    public String getDescription() {
        return " <key> : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    @Override
    public void execute(String argument) {
        try {
            boolean wasFound = false;
            Integer key = Integer.parseInt(argument.trim());
            Iterator<Integer> element = collectionManager.getCollection().keySet().iterator();

            while (element.hasNext()) {
                Integer elemKey = element.next();
                if (elemKey < key) {
                    element.remove();
                    wasFound = true;
                }
            }

            if (wasFound) {
                System.out.printf("Элементы, ключ которых меньше %d успешно удалены%n", key);
            } else {
                System.out.printf("Элементов, ключ которых меньше %d не существует%n", key);
            }

        } catch (NumberFormatException e) {
            System.out.printf("Нельзя выполнить команду %s: значение ключа должно быть целым числом%n", getName());
        }
    }
}
