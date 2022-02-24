package ru.itmo.lab5.modes;

import java.util.Scanner;

import ru.itmo.lab5.commands.Command;

public class ConsoleMode {
    private Command[] commands;
    private String file;

    public ConsoleMode(Command[] commands, String file) {
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
                        if (checkCommandWithArgument(choice)) {
                            argument = choice[1];
                            command.execute(argument);   
                        }
                    } else {
                        wasFound = true;
                        if (checkCommand(choice)) {
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

    private boolean checkCommand(String[] choice) {
        if (choice.length != 1) {
            System.out.println("У данной команды нет аргументов");
            return false;
        }
        return true;
    }

    private boolean checkCommandWithArgument(String[] choice) {
        if (choice.length != 2) {
            System.out.println("У данной команды должен быть один аргумент");
            return false;
        }
        return true;
    }
    
}
