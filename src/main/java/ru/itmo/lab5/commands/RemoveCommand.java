package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class RemoveCommand implements Command {
    private CollectionControl collectionControl;
    
    public RemoveCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
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
        try {
            Integer key = Integer.parseInt(argument);
            Ticket removedElement = collectionControl.getCollection().remove(key);
            String keyList = "";

            for (Integer elemKey : collectionControl.getCollection().keySet()) {
                keyList += elemKey + ", ";
            }
            
            if (collectionControl.getCollection().isEmpty()) {
                System.out.println("Нельзя удалить элемент из пустой коллекции");
            }

            if (removedElement == null) {
                System.out.printf("Элемента с таким ключом не существует. Все существующие ключи: %s%n", 
                                   keyList.substring(0, keyList.length() - 2));                
            } else {
                System.out.printf("Элемент с ключом %d успешно удалён%n", key);
            }
        } catch (NumberFormatException e) {
            System.out.println("Значение ключа должно быть целым числом");
        }
    }
}
