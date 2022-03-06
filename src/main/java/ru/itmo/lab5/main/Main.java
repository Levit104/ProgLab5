package ru.itmo.lab5.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ru.itmo.lab5.collection.*;
import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.console.*;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.file.*;

/**
 * Главный класс, запускающий программу
 */

public class Main {
    public static void main(String[] args) {
        String file = null;
        
        try {
            if (args.length > 1) {
                throw new ArrayIndexOutOfBoundsException();
            } else if (!FileManager.checkFileExtension(args[0])) {
                throw new FileFormatException();
            } else if (!new File(args[0]).isFile()) {
                throw new FileNotFoundException();
            }
            file = args[0];
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Неверно указан путь до файла\n");
        } catch (FileFormatException e2) {
            System.out.println("Неверное расширение файла\n");
        } catch (FileNotFoundException e3) {
            System.out.println("Указан несуществующий файл\n");
        }

        FileManager fileManager = new FileManager(file);
        CollectionManager collectionControl = new CollectionManager(fileManager);
        ConsoleManager consoleManager = new ConsoleManager(new Scanner(System.in));

        Command[] commands = {
                new InfoCommand(collectionControl),
                new ShowCommand(collectionControl),
                new InsertCommand(collectionControl, consoleManager),
                new UpdateCommand(collectionControl, consoleManager),
                new RemoveCommand(collectionControl),
                new ClearCommand(collectionControl),
                new SaveCommand(collectionControl, fileManager),
                new ReplaceIfGreaterCommand(collectionControl, consoleManager),
                new ReplaceIfLowerCommand(collectionControl, consoleManager),
                new RemoveLowerKeyCommand(collectionControl),
                new FilterCommand(collectionControl),
                new PrintAscendingCommand(collectionControl),
                new PrintFieldDescendingCommand(collectionControl)
        };

        CommandManager commandManager = new CommandManager(consoleManager, commands, file);
        commandManager.consoleMode();
    }
}
