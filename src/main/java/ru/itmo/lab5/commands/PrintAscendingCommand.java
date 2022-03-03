package ru.itmo.lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда-фильтр, выводит элементы в порядке возрастания их цены
 */

public class PrintAscendingCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager коллекция (менеджер коллекции)
     * @see CollectionManager
     */

    public PrintAscendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
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
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else if (collectionManager.getCollection().size() == 1) {
            System.out.printf("Нельзя выполнить команду %s: в коллекции всего 1 элемент%n", getName());
        } else {
            System.out.println("Элементы коллекции в порядке возрастания цены: ");
            List<Ticket> sortedCollection = new ArrayList<>(collectionManager.getCollection().values());
            Collections.sort(sortedCollection);
            for (Ticket ticket : sortedCollection) {
                System.out.println(ticket);
            }
        }
    }
}
