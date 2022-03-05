package ru.itmo.lab5.modes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.itmo.lab5.commands.Command;
import ru.itmo.lab5.file.FileManager;

public class CommandManager {
    private ConsoleManager consoleManager;
    private Command[] commands;
    private String file;
    private Scanner mainScanner;
    private Scanner scriptScanner;
    private boolean scriptExit;
    
    public CommandManager(ConsoleManager consoleManager, Command[] commands, String file) {
        this.consoleManager = consoleManager;
        this.commands = commands;
        this.file = file;
        this.mainScanner = consoleManager.getScanner();
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
                    System.out.println("help : вывести справку по доступным командам");
                    System.out.println("exit : завершить программу (без сохранения в файл)");
                    for (Command command : commands) {
                        System.out.println(command.getName() + command.getDescription());
                    }
                }
            } else if (inputCommand.equals("script")) {
                //TODO переделать
                wasFound = true;
                if (checkCommand(inputChoice, "execute_script")) {
                    scriptMode("script");
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

    public void scriptMode(String script) {
        int lineNumber = 1;
        try {
            scriptScanner = new Scanner(new File(script));
        } catch (FileNotFoundException e) {
            System.out.println("Скрипт не найден");
        }

        loop: while (scriptScanner.hasNextLine()) {
            
            String scriptLine = scriptScanner.nextLine();
            String[] scriptChoice = scriptLine.split(" ");
            String scriptCommand = scriptChoice[0];
            String scriptArgument = "";
            boolean wasFound = false;
            lineNumber++;

            if (scriptCommand.equals("exit")) {
                wasFound = true;
                if (checkCommand(scriptChoice, "exit")) {
                    scriptExit = true;
                    break loop;
                }
            } else if (scriptCommand.equals("help")) {
                wasFound = true;
                if (checkCommand(scriptChoice, "help")) {
                    System.out.println("help : вывести справку по доступным командам");
                    System.out.println("exit : завершить программу (без сохранения в файл)");
                    for (Command command : commands) {
                        System.out.println(command.getName() + command.getDescription());
                    }
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
                                        //TODO описание ошибки
                                        System.out.println("Ошибка при чтении скрипта " + lineNumber);
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
                //TODO описание ошибки
                System.out.println("Несуществующая команда " + lineNumber);
            }
        }
        System.out.println("Скрипт завершён");
    }

    private boolean checkCommand(String[] choice, String commandName) {
        if (choice.length != 1) {
            System.out.printf("У команды %s нет аргументов%n", commandName);
            return false;
        }
        return true;
    }

    private boolean checkCommandWithArgument(String[] choice, String commandName) {
        if (choice.length != 2) {
            System.out.printf("У команды %s один обязательный аргумент%n", commandName);
            return false;
        }
        return true;
    }
}
