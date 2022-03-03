package ru.itmo.lab5.commands;

import java.io.FileWriter;
import java.io.IOException;

import ru.itmo.lab5.collection.CollectionManager;
import ru.itmo.lab5.data.Ticket;

/**
 * Команда, записывающая коллекцию в файл .csv
 */

public class SaveCommand implements Command {
    private CollectionManager collectionManager;
    private FileWriter fileWriter;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean hasArgument() {
        return true;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return " (опционально - путь до файла) : сохранить коллекцию в файл";
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.printf("Нельзя выполнить команду %s: коллекция пустая%n", getName());
        } else {
            String csvString = CollectionManager.csvString() + "\n";
            
            for (Ticket ticket : collectionManager.getCollection().values()) {
                csvString += ticket + "\n";
            }

            try {
                fileWriter = new FileWriter(argument);
                fileWriter.write(csvString);
                System.out.println("Коллекция успешно сохранена");
            } catch (IOException e1) {
                System.out.println("Не удалось записать данные в файл");
            } catch (NullPointerException e2) {
                System.out.println("При запуске программы путь до файла не был указан или был указан неверно." +
                                    "Укажите его как аргумент команды save");
            } finally {
                try {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    System.out.println("Не удалось записать данные в файл");
                }
            }
        }
    }
}
