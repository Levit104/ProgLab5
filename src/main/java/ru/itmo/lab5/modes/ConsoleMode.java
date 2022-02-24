package ru.itmo.lab5.modes;

import java.util.Scanner;

import ru.itmo.lab5.collection.CollectionControl;
import ru.itmo.lab5.commands.Command;

public class ConsoleMode {
    private CollectionControl collectionControl;
    private Command[] commands;
    private String file;

    public ConsoleMode(CollectionControl collectionControl, Command[] commands, String file) {
        this.collectionControl = collectionControl;
        this.commands = commands;
        this.file = file;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        loop: while (true) {
            System.out.print("Введите команду (help - справка по всем командам): ");
            String[] choice = scanner.nextLine().split(" ");
            
            String inputCommand = choice[0];
            String argument = "";
            boolean wasFound = false;

            for (Command command : commands) {
                if (inputCommand.equals("exit")) {
                    System.out.println("Программа успешно завершена");
                    break loop;
                } else if (inputCommand.equals("help")) {
                    wasFound = true;
                    System.out.println(command.getName() + command.getDescription());
                } else if (inputCommand.equals(command.getName())) {
                    if (command.hasArgument()) {
                        wasFound = true;
                        if (collectionControl.checkCommandWithArgument(choice)) {
                            argument = choice[1];
                            command.execute(argument);   
                        }
                    } else {
                        wasFound = true;
                        if (collectionControl.checkCommand(choice)) {
                            if (command.getName().equals("save")) {
                                argument = file;
                                command.execute(argument);
                            }
                            command.execute(argument);
                        }
                    }
                }
            }
            if (!wasFound) System.out.println("Вы ввели несуществующую команду");
        }
        scanner.close();
    }
}
