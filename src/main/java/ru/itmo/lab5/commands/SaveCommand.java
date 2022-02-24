package ru.itmo.lab5.commands;

import java.io.FileWriter;
import java.io.IOException;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.data.Ticket;

public class SaveCommand implements Command {
    private CollectionControl collectionControl;
    private FileWriter fileWriter;

    public SaveCommand(CollectionControl collectionControl) {
        this.collectionControl = collectionControl;
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
        return " : сохранить коллекцию в файл";
    }

    @Override
    public void execute(String argument) {
        String csvString = collectionControl.csvString() + "\n";
        for (Ticket ticket : collectionControl.getCollection().values()) {
            csvString += ticket + "\n";
        }
        try {
            fileWriter = new FileWriter(argument);
            fileWriter.write(csvString);
            fileWriter.close();
            System.out.println("Коллекция успешно сохранена");
        } catch (IOException e) {
            System.out.println("Не удалось записать данные в файл");
        }
    }
    
}
