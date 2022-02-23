package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionControl;

public class InfoCommand implements Command {
    private CollectionControl collectionControl;

    public InfoCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)";
    }

    @Override
    public void execute(String argument) {
        System.out.println("Коллекция типа HashMap, хранящая объекты класса Ticket" +
                           "\nДата инициализации: " + collectionControl.getInitDate() + 
                           "\nКол-во элементов: " + collectionControl.getCollection().size());
        
    }
    
}
