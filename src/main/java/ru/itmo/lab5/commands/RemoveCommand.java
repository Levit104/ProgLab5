package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда, удаляющая элемент из коллекции
 */

public class RemoveCommand implements Command {
    private CollectionControl collectionControl;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl коллекция (менеджер коллекции)
     * @see CollectionControl
     */

    public RemoveCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
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
        return " null : удалить элемент из коллекции по его ключу";
    }

    @Override
    public void execute(String argument) {
        if (collectionControl.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            try {
                Integer key = Integer.parseInt(argument);
                Ticket removedElement = collectionControl.getCollection().remove(key);
                String keyList = "";
    
                for (Integer elemKey : collectionControl.getCollection().keySet()) {
                    keyList += elemKey + ", ";
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
