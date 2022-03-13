package ru.itmo.lab5.main;

import java.util.Scanner;

import ru.itmo.lab5.collection.*;
import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.console.*;
import ru.itmo.lab5.file.*;

/**
 * Главный класс, запускающий программу
 */

public class Main {
    public static void main(String[] args) {
        String file = null;

        try {
            if (args.length > 1) throw new ArrayIndexOutOfBoundsException();
            file = args[0];
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("\nПуть до файла не указан или указан неверно");
        }

        FileManager fileManager = new FileManager(file);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        ConsoleManager consoleManager = new ConsoleManager(new Scanner(System.in));

        Command[] commands = {
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager, consoleManager),
                new UpdateCommand(collectionManager, consoleManager),
                new RemoveCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager, fileManager),
                new ReplaceIfGreaterCommand(collectionManager, consoleManager),
                new ReplaceIfLowerCommand(collectionManager, consoleManager),
                new RemoveLowerKeyCommand(collectionManager),
                new FilterCommand(collectionManager),
                new PrintAscendingCommand(collectionManager),
                new PrintFieldDescendingCommand(collectionManager)
        };

        CommandManager commandManager = new CommandManager(consoleManager, commands, file);
        commandManager.consoleMode();
    }
}
