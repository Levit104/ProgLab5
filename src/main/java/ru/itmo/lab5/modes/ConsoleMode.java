package ru.itmo.lab5.modes;

import java.util.Scanner;

import ru.itmo.lab5.commands.Command;
import ru.itmo.lab5.parser.CSVParser;

/**
 * Класс для управления ввода команд пользователем
 */

public class ConsoleMode {
    private Command[] commands;
    private String file;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param commands массив команд
     * @param file     путь до файла
     * @see Command
     */

    public ConsoleMode(Command[] commands, String file) {
        this.commands = commands;
        this.file = file;
    }

    /**
     * Запускает консольный режим
     */

    public void start() {
        Scanner scanner = new Scanner(System.in);

        loop: while (true) {
            System.out.print("Введите команду (help - справка по всем командам): ");
            String[] choice = scanner.nextLine().split(" ");

            String inputCommand = choice[0];
            String argument = "";
            boolean wasFound = false;
            boolean firstHelpCommand = true;

            for (Command command : commands) {
                if (inputCommand.equals("exit")) {
                    System.out.println("Программа успешно завершена");
                    break loop;
                } else if (inputCommand.equals("help")) {
                    wasFound = true;
                    if (firstHelpCommand) {
                        System.out.println("help : вывести справку по доступным командам");
                        System.out.println("exit : завершить программу (без сохранения в файл)");
                        firstHelpCommand = false;
                    }
                    System.out.println(command.getName() + command.getDescription());
                } else if (inputCommand.equals(command.getName())) {
                    wasFound = true;
                    if (command.getName().equals("save")) {
                        if (choice.length > 2) {
                            System.out.println("У данной команды есть только один необязательный аргумент");
                        } else {
                            if (choice.length == 1) {
                                argument = file;
                            } else if (choice.length == 2 && CSVParser.checkFileExtension(choice[1])) {
                                argument = choice[1];
                            }
                            command.execute(argument);
                        }
                    } else if (command.hasArgument() && checkCommandWithArgument(choice, command)) {
                        argument = choice[1];
                        command.execute(argument);
                    } else if (!command.hasArgument() && checkCommand(choice, command)) {
                        command.execute(argument);
                    }
                }
            }
            if (!wasFound) {
                System.out.println("Вы ввели несуществующую команду");
            }
        }
        scanner.close();
    }

    public static boolean checkCommand(String[] choice, Command command) {
        if (choice.length != 1) {
            System.out.printf("У команды %s нет аргументов%n", command.getName());
            return false;
        }
        return true;
    }

    public static boolean checkCommandWithArgument(String[] choice, Command command) {
        if (choice.length != 2) {
            System.out.printf("У команды %s один обязательный аргумент%n", command.getName());
            return false;
        }
        return true;
    }
}
