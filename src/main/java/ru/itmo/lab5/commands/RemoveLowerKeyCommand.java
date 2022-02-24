package ru.itmo.lab5.commands;

import java.util.Iterator;

import ru.itmo.lab5.collection.CollectionControl;

public class RemoveLowerKeyCommand implements Command {
    private CollectionControl collectionControl;

    public RemoveLowerKeyCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
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
        return " null : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    @Override
    public void execute(String argument) {
        try {
            boolean wasFound = false;
            Integer key = Integer.parseInt(argument.trim());
            Iterator<Integer> element = collectionControl.getCollection().keySet().iterator();
            
            while (element.hasNext()) {
                Integer elemKey = element.next();
                if (elemKey < key) {
                    element.remove();
                    wasFound = true;
                }
            }
            
            if (wasFound) {
                System.out.println("Элементы успешно удалены");
            } else {
                System.out.println("Элементов, ключ которых меньше заданного не существует");
            }

        } catch (NumberFormatException e) {
            System.out.println("Значение ключа должно быть целым числом");
        }
        

    }
    
}
