package ru.itmo.lab5.main;

import java.util.Scanner;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.parser.CSVParser;

public class Main {
    public static void main(String[] args) {
        CollectionControl collectionControl = new CollectionControl();

        Command info = new InfoCommand(collectionControl);
        Command show = new ShowCommand(collectionControl);
        Command insert = new InsertCommand(collectionControl);
        Command update = new UpdateCommand(collectionControl);
        Command remove = new RemoveCommand(collectionControl);
        Command clear = new ClearCommand(collectionControl);
        Command save = new SaveCommand(collectionControl);
        // Command executeScriptCommand = new ExecuteScriptCommand(collectionControl);
        Command replaceIfGreater = new ReplaceIfGreaterCommand(collectionControl);
        Command replaceIfLower = new ReplaceIfLowerCommand(collectionControl);
        Command removeLowerKey = new RemoveLowerKeyCommand(collectionControl);
        Command filter = new FilterCommand(collectionControl);
        Command printAscending = new PrintAscendingCommand(collectionControl);
        Command printFieldDescending = new PrintFieldDescendingCommand(collectionControl);

        String file = "files\\InputFile.csv";
        
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
                        Command[] commands = {info, show, insert, update, remove, clear, save, replaceIfGreater, replaceIfLower,
                                              removeLowerKey, filter, printAscending, printFieldDescending};
                        for (Command myCommand : commands) {
                            System.out.println(myCommand.getName() + myCommand.getDescription() + "\n");
                        }
                    }
                    break;
                
                case "info":
                    if (collectionControl.checkCommand(choice)) {
                        info.execute(argument);
                    }
                    break;

                case "show":
                    if (collectionControl.checkCommand(choice)) {
                        show.execute(argument);
                    }
                    break;

                case "insert":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        insert.execute(argument);
                    }
                    break;

                case "update":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        update.execute(argument);
                    }
                    break;

                case "remove_key":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        remove.execute(argument);
                    }
                    break;

                case "clear":
                    if (collectionControl.checkCommand(choice)) {
                        clear.execute(argument);
                    }
                    break;

                case "save":
                    argument = file;
                    save.execute(argument);
                    break;
                
                case "replace_if_greater":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        replaceIfGreater.execute(argument);
                    }
                    break;
                
                case "replace_if_lower":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        replaceIfLower.execute(argument);
                    }
                    break;
                
                case "remove_lower_key":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        removeLowerKey.execute(argument);
                    }                       
                    break;

                case "filter_starts_with_name":
                    if (collectionControl.checkCommandWithArgument(choice)) {
                        argument = choice[1];
                        filter.execute(argument);
                    }                       
                    break;
                
                case "print_ascending":
                    if (collectionControl.checkCommand(choice)) {
                        printAscending.execute(argument);
                    }
                    break;
                
                case "print_field_descending_type":
                    if (collectionControl.checkCommand(choice)) {
                        printFieldDescending.execute(argument);   
                    }
                    break;

                default:
                    System.out.println("Вы ввели несуществующую команду");
            }   
        }
        scanner.close();
    }
}
