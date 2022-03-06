package ru.itmo.lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда-фильтр, выводит значение поля "тип билета" элементов в порядке убывания
 */

public class PrintFieldDescendingCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public PrintFieldDescendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return false;
    }

    @Override
    public String getName() {
        return "print_field_descending_type";
    }

    @Override
    public String getDescription() {
        return " : вывести значения поля type всех элементов в порядке убывания";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else if (collectionManager.getCollection().size() == 1) {
            System.out.printf("Нельзя выполнить команду %s: в коллекции всего 1 элемент%n", getName());
        } else {
            List<String> typeList = new ArrayList<>();
            String typeString = "";
            
            for (Ticket ticket : collectionManager.getCollection().values()) {
                typeList.add(ticket.getType().toString());
            }
            
            Collections.sort(typeList);
            Collections.reverse(typeList);
            
            for (String type : typeList) {
                typeString += type + ", ";
            }
            
            System.out.println("Значения поля type всех элементов в порядке убывания: ");
            System.out.println(typeString.substring(0, typeString.length() - 2));
        }
    }
}
