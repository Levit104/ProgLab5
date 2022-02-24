package ru.itmo.lab5.main;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.modes.ConsoleMode;
import ru.itmo.lab5.parser.CSVParser;

public class Main {
    public static void main(String[] args) {
        CollectionControl collectionControl = new CollectionControl();
        String file = null;

        try {
            if (args.length > 1) throw new ArrayIndexOutOfBoundsException();
            else if (!CSVParser.checkFileExtension(args[0])) throw new IllegalArgumentException();
            file = args[0];
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Неверно указан путь до файла\n");
        } catch (IllegalArgumentException e2) {
            System.out.println("Файл должен иметь расширение .csv\n");
        }

        Command[] commands = {
            new InfoCommand(collectionControl), 
            new ShowCommand(collectionControl), 
            new InsertCommand(collectionControl), 
            new UpdateCommand(collectionControl), 
            new RemoveCommand(collectionControl), 
            new ClearCommand(collectionControl), 
            new SaveCommand(collectionControl), 
            // Command executeScriptCommand = new ExecuteScriptCommand(collectionControl),
            new ReplaceIfGreaterCommand(collectionControl), 
            new ReplaceIfLowerCommand(collectionControl),
            new RemoveLowerKeyCommand(collectionControl), 
            new FilterCommand(collectionControl), 
            new PrintAscendingCommand(collectionControl), 
            new PrintFieldDescendingCommand(collectionControl)
        };
        
        CSVParser csvParser = new CSVParser(file);
        ConsoleMode consoleMode = new ConsoleMode(commands, file);

        csvParser.parse();
        collectionControl.addValues(csvParser.getTicketMap());
        consoleMode.start();
    }
}
