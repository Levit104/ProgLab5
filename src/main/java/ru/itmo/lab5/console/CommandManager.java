package ru.itmo.lab5.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import ru.itmo.lab5.commands.Command;
import ru.itmo.lab5.file.FileManager;

/**
 * Класс для работы с командами (менеджер команд)
 */

public class CommandManager {
    private ConsoleManager consoleManager;
    private Command[] commands;
    private String file;
    private Scanner mainScanner;
    private boolean scriptExit;
    private Set<String> openedScripts;

    /**
     * Конструктор, задающий параметры для создания объекта
     * 
     * @param consoleManager менеджер консоли
     * @param commands       команды (массив из команд)
     * @param file           путь до файла
     * @see ConsoleManager
     * @see Command
     */

    public CommandManager(ConsoleManager consoleManager, Command[] commands, String file) {
        this.consoleManager = consoleManager;
        this.commands = commands;
        this.file = file;
        this.mainScanner = consoleManager.getScanner();
        this.openedScripts = new HashSet<>();
    }

    /**
     * Запускает консольный режим
     */

    public void consoleMode() {
        while (true) {
            try {
                if (scriptExit) {
                    System.out.println("Программа успешно завершена через скрипт\n");
                    break;
                }

                System.out.print("Введите команду (help - справка по всем командам): ");
                String[] inputChoice = mainScanner.nextLine().split("\\s+");

                String inputCommand = inputChoice[0];
                String inputArgument = "";
                boolean wasFound = false;

                System.out.println(); // просто отступ

                if (inputCommand.isEmpty()) {
                    continue;
                } else if (inputCommand.equals("exit")) {
                    wasFound = true;
                    if (checkCommand(inputChoice, "exit")) {
                        System.out.println("Программа успешно завершена\n");
                        break;
                    }
                } else if (inputCommand.equals("help")) {
                    wasFound = true;
                    if (checkCommand(inputChoice, "help")) {
                        helpDescriptions();
                    }
                } else if (inputCommand.equals("execute_script")) {
                    wasFound = true;
                    if (checkCommandWithArgument(inputChoice, "execute_script")) {
                        scriptMode(inputChoice[1]);
                        openedScripts.clear();
                    }
                } else {
                    for (Command command : commands) {
                        if (inputCommand.equals(command.getName())) {
                            wasFound = true;
                            if (command.getName().equals("save")) {
                                executeSave(inputChoice, command);
                            } else if (command.hasArgument()
                                    && checkCommandWithArgument(inputChoice, command.getName())) {
                                inputArgument = inputChoice[1];
                                command.execute(inputArgument);
                            } else if (!command.hasArgument() && checkCommand(inputChoice, command.getName())) {
                                command.execute(inputArgument);
                            }
                        }
                    }
                }

                if (!wasFound) {
                    System.out.println("Несуществующая команда: " + inputCommand);
                }

                System.out.println(); // просто отступ

            } catch (NoSuchElementException e) {
                System.out.println();
                mainScanner = new Scanner(System.in);
                consoleManager.setScanner(mainScanner);
            }
        }
    }

    /**
     * Запускает скриптовый режим
     * 
     * @param script путь до скрипта
     */

    public void scriptMode(String script) {
        try {
            openedScripts.add(script);
            Scanner scriptScanner = new Scanner(new File(script));

            while (scriptScanner.hasNextLine()) {
                String scriptLine = scriptScanner.nextLine();
                String[] scriptChoice = scriptLine.split("\\s+");
                String scriptCommand = scriptChoice[0];
                String scriptArgument = "";
                boolean wasFound = false;

                if (scriptCommand.equals("exit")) {
                    wasFound = true;
                    if (checkCommand(scriptChoice, "exit")) {
                        scriptExit = true;
                        break;
                    }
                } else if (scriptCommand.equals("help")) {
                    wasFound = true;
                    if (checkCommand(scriptChoice, "help")) {
                        helpDescriptions();
                    }
                } else if (scriptCommand.equals("execute_script")) {
                    wasFound = true;
                    if (checkCommandWithArgument(scriptChoice, "execute_script")
                            && checkOpenedScript(scriptChoice[1])) {
                        scriptMode(scriptChoice[1]);
                    }
                } else {
                    for (Command command : commands) {
                        if (scriptCommand.equals(command.getName())) {
                            wasFound = true;
                            if (command.getName().equals("save")) {
                                executeSave(scriptChoice, command);
                            } else if (command.hasArgument()
                                    && checkCommandWithArgument(scriptChoice, command.getName())) {
                                scriptArgument = scriptChoice[1];
                                if (command.getName().equals("insert") ||
                                        command.getName().equals("update") ||
                                        command.getName().equals("replace_if_greater") ||
                                        command.getName().equals("replace_if_lower")) {
                                    try {
                                        consoleManager.setInScript(true);
                                        consoleManager.setScanner(scriptScanner);
                                        command.execute(scriptArgument);
                                    } catch (NoSuchElementException e) {
                                        System.out.println("Ошибка при чтении скрипта");
                                    } finally {
                                        consoleManager.setInScript(false);
                                        consoleManager.setScanner(mainScanner);
                                    }
                                } else {
                                    command.execute(scriptArgument);
                                }
                            } else if (!command.hasArgument() && checkCommand(scriptChoice, command.getName())) {
                                command.execute(scriptArgument);
                            }
                        }
                    }
                }

                if (!wasFound) {
                    System.out.println("Несуществующая команда: " + scriptCommand);
                }

                System.out.println(); // просто отступ
            }

            System.out.printf("Скрипт %s завершён%n", script);

        } catch (FileNotFoundException e) {
            System.out.printf("Скрипт %s не найден%n", script);
        }
    }

    private void helpDescriptions() {
        System.out.println("Все доступные команды: ");
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("exit : завершить программу (без сохранения в файл)");
        System.out.println("execute_script <file_name> : считать и исполнить скрипт из указанного файла");
        for (Command command : commands) {
            System.out.println(command.getName() + command.getDescription());
        }
    }

    private void executeSave(String[] input, Command save) {
        String argument;
        if (input.length > 2) {
            System.out.println("У команды save один необязательный аргумент");
        } else if (input.length == 1) {
            argument = file;
            save.execute(argument);
        } else if (FileManager.checkFileExtension(input[1])) {
            argument = input[1];
            save.execute(argument);
        }
    }

    private boolean checkCommand(String[] input, String commandName) {
        if (input.length != 1) {
            System.out.printf("У команды %s нет аргументов%n", commandName);
            return false;
        }
        return true;
    }

    private boolean checkCommandWithArgument(String[] input, String commandName) {
        if (input.length != 2) {
            System.out.printf("У команды %s один обязательный аргумент%n", commandName);
            return false;
        }
        return true;
    }

    private boolean checkOpenedScript(String newScript) {
        if (openedScripts.contains(newScript)) {
            System.out.println("Нельзя выполнить команду execute_script: попытка рекурсивно запустить скрипт");
            return false;
        }
        return true;
    }
}
