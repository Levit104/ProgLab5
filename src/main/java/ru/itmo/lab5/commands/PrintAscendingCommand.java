package ru.itmo.lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда-фильтр, выводит элементы в порядке возрастания их цены
 */

public class PrintAscendingCommand implements Command {
    private CollectionControl collectionControl;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionControl коллекция (менеджер коллекции)
     * @see CollectionControl
     */

    public PrintAscendingCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "print_ascending";
    }

    @Override
    public String getDescription() {
        return " : вывести элементы коллекции в порядке возрастания (цены)";
    }

    @Override
    public void execute(String argument) {
        List<Ticket> sortedCollection = new ArrayList<>(collectionControl.getCollection().values());
        Collections.sort(sortedCollection);
        for (Ticket ticket : sortedCollection) {
            System.out.println(ticket);
        }
    }
}
