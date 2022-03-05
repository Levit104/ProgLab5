package ru.itmo.lab5.commands;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда-фильтр, выводит элементы, имя которых начинается с введённой пользователем подстроки
 */

public class FilterCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param collectionManager менеджер коллекции
     * @see CollectionManager
     */

    public FilterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " <name> : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else if (collectionManager.getCollection().size() == 1) {
            System.out.printf("Нельзя выполнить команду %s: в коллекции всего 1 элемент%n", getName());
        } else {
            String ticketList = "";
            
            for (Ticket ticket : collectionManager.getCollection().values()) {
                if (ticket.getName().startsWith(argument)) {
                    ticketList += ticket + "\n";
                }
            }
            
            if (ticketList.isEmpty()) {
                System.out.println("В коллекции нет элементов, название которых начинается на " + argument);
            } else {
                System.out.println("Элементы, название которых начинается на " + argument);
                System.out.println(ticketList.substring(0, ticketList.length() - 1));
            }
        }
    }
}
