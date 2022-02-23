package ru.itmo.lab5.main;

import java.util.Scanner;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.parser.CSVParser;

public class Main {
    public static void main(String[] args) {
        CollectionControl collectionControl = new CollectionControl();

        InfoCommand infoCommand = new InfoCommand(collectionControl);
        ShowCommand showCommand = new ShowCommand(collectionControl);
        InsertCommand insertCommand = new InsertCommand(collectionControl);
        UpdateCommand updateCommand = new UpdateCommand(collectionControl);
        RemoveCommand removeCommand = new RemoveCommand(collectionControl);
        ClearCommand clearCommand = new ClearCommand(collectionControl);
        SaveCommand saveCommand = new SaveCommand(collectionControl);
        //ExecuteScript
        //ReplaceIfGreater
        //ReplaceIfLower
        RemoveLowerKeyCommand removeLowerKeyCommand = new RemoveLowerKeyCommand(collectionControl);
        FilterCommand filterCommand = new FilterCommand(collectionControl);
        PrintAscendingCommand printAscendingCommand = new PrintAscendingCommand(collectionControl);
        PrintFieldDescendingCommand printFieldDescendingCommand = new PrintFieldDescendingCommand(collectionControl);

        String file = "files\\InputFil5e.csv";
        
        Scanner scanner = new Scanner(System.in);
        CSVParser csvParser = new CSVParser(file);
        csvParser.parse();
        collectionControl.addValues(csvParser.getTicketMap());

        loop: while (true) {
            System.out.print("Введите команду (help - справка по всем командам): ");
            String[] choice = scanner.nextLine().split(" ");
            
            String command = choice[0];
            String argument = "";

            switch (command.trim()) {
                case "exit":
                    System.out.println("Программа успешно завершена");
                    break loop;
                
                case "help":
                    if (collectionControl.checkCommand(choice)) {
                        System.out.println("help : вывести справку по доступным командам\n");
                        Command[] commands = {infoCommand, showCommand, insertCommand, updateCommand, removeCommand, clearCommand, saveCommand, 
                                              removeLowerKeyCommand, filterCommand, printAscendingCommand, printFieldDescendingCommand};
                        for (Command myCommand : commands) {
                            System.out.println(myCommand.getName() + myCommand.getDescription() + "\n");
                        }
                    }
                    break;
                
                case "info":
                    if (collectionControl.checkCommand(choice)) {
                        infoCommand.execute(argument);
                    }
                    break;

                case "show":
                    if (collectionControl.checkCommand(choice)) {
                        showCommand.execute(argument);
                    }
                    break;

                case "insert":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        insertCommand.execute(argument);
                    }
                    break;

                case "update":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        updateCommand.execute(argument);
                    }
                    break;

                case "remove_key":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        removeCommand.execute(argument);
                    }
                    break;

                case "clear":
                    if (collectionControl.checkCommand(choice)) {
                        clearCommand.execute(argument);
                    }
                    break;

                case "save":
                    argument = file;
                    saveCommand.execute(argument);
                    break;
                
                case "remove_lower_key":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        removeLowerKeyCommand.execute(argument);
                    }                       
                    break;

                case "filter_starts_with_name":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        filterCommand.execute(argument);
                    }                       
                    break;
                
                case "print_ascending":
                    if (collectionControl.checkCommand(choice)) {
                        printAscendingCommand.execute(argument);
                    }
                    break;
                
                case "print_field_descending_type":
                    if (collectionControl.checkCommand(choice)) {
                        printFieldDescendingCommand.execute(argument);   
                    }
                    break;

                default:
                    System.out.println("Вы ввели несуществующую команду");
            }   
        }
        scanner.close();
    }
}
