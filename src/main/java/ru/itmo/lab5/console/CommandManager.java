package ru.itmo.lab5.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import ru.itmo.lab5.commands.Command;
import ru.itmo.lab5.file.FileManager;

public class CommandManager {
    private ConsoleManager consoleManager;
    private Command[] commands;
    private String file;
    private Scanner mainScanner;
    private Scanner scriptScanner;
    private boolean scriptExit;
    private Set<String> openedScripts;
    
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
        loop: while (true) {
            
            if (scriptExit) {
                System.out.println("Программа успешно завершена через скрипт");
                break loop;
            }

            System.out.print("Введите команду (help - справка по всем командам): ");
            String[] inputChoice = mainScanner.nextLine().split(" ");

            String inputCommand = inputChoice[0];
            String inputArgument = "";
            boolean wasFound = false;

            if (inputCommand.equals("exit")) {
                wasFound = true;
                if (checkCommand(inputChoice, "exit")) {
                    System.out.println("Программа успешно завершена");
                    break loop;
                }
            } else if (inputCommand.equals("help")) {
                wasFound = true;
                if (checkCommand(inputChoice, "help")) {
                    helpDescriptions();
                }
            } else if (inputCommand.equals("execute_script")) {
                wasFound = true;
                if (checkCommandWithArgument(inputChoice, "execute_script")) {
                    scriptMode(inputChoice[1], scriptScanner);
                    openedScripts.clear();
                }
            } else {
                for (Command command : commands) {
                    if (inputCommand.equals(command.getName())) {
                        wasFound = true;
                        if (command.getName().equals("save")) {
                            if (inputChoice.length > 2) {
                                System.out.println("У данной команды есть только один необязательный аргумент");
                            } else {
                                if (inputChoice.length == 1) {
                                    inputArgument = file;
                                } else if (inputChoice.length == 2 && FileManager.checkFileExtension(inputChoice[1])) {
                                    inputArgument = inputChoice[1];
                                }
                                command.execute(inputArgument);
                            }
                        } else if (command.hasArgument() && checkCommandWithArgument(inputChoice, command.getName())) {
                            inputArgument = inputChoice[1];
                            command.execute(inputArgument);
                        } else if (!command.hasArgument() && checkCommand(inputChoice, command.getName())) {
                            command.execute(inputArgument);
                        }
                    }
                }
            }
            if (!wasFound) {
                System.out.println("Вы ввели несуществующую команду");
            }
        }
    }

    public void scriptMode(String script, Scanner scriptScanner) {
        try {
            openedScripts.add(script);
            scriptScanner = new Scanner(new File(script));
            
            loop: while (scriptScanner.hasNextLine()) {
                String scriptLine = scriptScanner.nextLine();
                String[] scriptChoice = scriptLine.split(" ");
                String scriptCommand = scriptChoice[0];
                String scriptArgument = "";
                boolean wasFound = false;
    
                if (scriptCommand.equals("exit")) {
                    wasFound = true;
                    if (checkCommand(scriptChoice, "exit")) {
                        scriptExit = true;
                        break loop;
                    }
                } else if (scriptCommand.equals("help")) {
                    wasFound = true;
                    if (checkCommand(scriptChoice, "help")) {
                        helpDescriptions();
                    }
                } else if (scriptCommand.equals("execute_script")) {
                    wasFound = true;
                    if (checkCommandWithArgument(scriptChoice, "execute_script") && checkOpenedScript(scriptChoice[1])) {
                        scriptMode(scriptChoice[1], scriptScanner);
                    }
                } else {
                    for (Command command : commands) {
                        if (scriptCommand.equals(command.getName())) {
                            wasFound = true;
                            if (command.getName().equals("save")) {
                                if (scriptChoice.length > 2) {
                                    System.out.println("У команды save есть только один необязательный аргумент");
                                } else {
                                    if (scriptChoice.length == 1) {
                                        scriptArgument = file;
                                    } else if (scriptChoice.length == 2 && FileManager.checkFileExtension(scriptChoice[1])) {
                                        scriptArgument = scriptChoice[1];
                                    }
                                    command.execute(scriptArgument);
                                }
                            } else if (command.hasArgument() && checkCommandWithArgument(scriptChoice, command.getName())) {
                                scriptArgument = scriptChoice[1];
                                if (command.getName().equals("insert") || 
                                    command.getName().equals("update") || 
                                    command.getName().equals("replace_if_greater") ||
                                    command.getName().equals("replace_if_lower"))  {
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
