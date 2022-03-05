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

    private boolean scriptExit = false;
    
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
            String[] choice = mainScanner.nextLine().split(" ");

            String inputCommand = choice[0];
            String argument = "";
            boolean wasFound = false;

            if (inputCommand.equals("exit")) {
                wasFound = true;
                if (checkCommand(choice, "exit")) {
                    System.out.println("Программа успешно завершена");
                    break loop;
                }
            } else if (inputCommand.equals("help")) {
                wasFound = true;
                if (checkCommand(choice, "help")) {
                    System.out.println("help : вывести справку по доступным командам");
                    System.out.println("exit : завершить программу (без сохранения в файл)");
                    for (Command command : commands) {
                        System.out.println(command.getName() + command.getDescription());
                    }
                }
            } else if (inputCommand.equals("script")) {
                //TODO переделать
                wasFound = true;
                if (checkCommand(choice, "execute_script")) {
                    scriptMode("script");
                }
            } else {
                for (Command command : commands) {
                    if (inputCommand.equals(command.getName())) {
                        wasFound = true;
                        if (command.getName().equals("save")) {
                            if (choice.length > 2) {
                                System.out.println("У данной команды есть только один необязательный аргумент");
                            } else {
                                if (choice.length == 1) {
                                    argument = file;
                                } else if (choice.length == 2 && FileManager.checkFileExtension(choice[1])) {
                                    argument = choice[1];
                                }
                                command.execute(argument);
                            }
                        } else if (command.hasArgument() && checkCommandWithArgument(choice, command.getName())) {
                            argument = choice[1];
                            command.execute(argument);
                        } else if (!command.hasArgument() && checkCommand(choice, command.getName())) {
                            command.execute(argument);
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
        //String scriptLine = null;
        loop: while (scriptScanner.hasNextLine()) {
            
            String line = scriptScanner.nextLine();
            lineNumber++;
            
            //String[] s = (line + " ").split(" ");
            String[] s = line.split(" ");
            String inputCommand = s[0];
            String argument = "";
            boolean wasFound = false;

            if (inputCommand.equals("exit")) {
                wasFound = true;
                if (checkCommand(s, "exit")) {
                    scriptExit = true;
                    break loop;
                }
            } else if (inputCommand.equals("help")) {
                wasFound = true;
                if (checkCommand(s, "help")) {
                    System.out.println("help : вывести справку по доступным командам");
                    System.out.println("exit : завершить программу (без сохранения в файл)");
                    for (Command command : commands) {
                        System.out.println(command.getName() + command.getDescription());
                    }
                }
            } else {
                for (Command command : commands) {
                    if (inputCommand.equals(command.getName())) {
                        wasFound = true;
                        if (command.getName().equals("save")) {
                            if (s.length > 2) {
                                System.out.println("У команды save есть только один необязательный аргумент");
                            } else {
                                if (s.length == 1) {
                                    argument = file;
                                } else if (s.length == 2 && FileManager.checkFileExtension(s[1])) {
                                    argument = s[1];
                                }
                                command.execute(argument);
                            }
                        } else if (command.hasArgument() && checkCommandWithArgument(s, command.getName())) {
                            argument = s[1];
                            if (command.getName().equals("insert") || 
                                command.getName().equals("update") || 
                                command.getName().equals("replace_if_greater") ||
                                command.getName().equals("replace_if_lower"))  {
                                    try {
                                        consoleManager.setInScript(true);
                                        consoleManager.setScanner(scriptScanner);
                                        command.execute(argument);
                                    } catch (NoSuchElementException e) {
                                        //TODO описание ошибки
                                        System.out.println("Ошибка при чтении скрипта " + lineNumber);
                                    } finally {
                                        consoleManager.setInScript(false);
                                        consoleManager.setScanner(mainScanner);
                                    }
                                } else {
                                    command.execute(argument);
                                }
                        } else if (!command.hasArgument() && checkCommand(s, command.getName())) {
                            command.execute(argument);
                        }
                    }
                }
            }
            if (!wasFound) {
                System.out.println("Несуществующая команда");
            }
        }
        System.out.println("Скрипт завершён");
    }

    public static boolean checkCommand(String[] choice, String commandName) {
        if (choice.length != 1) {
            System.out.printf("У команды %s нет аргументов%n", commandName);
            return false;
        }
        return true;
    }

    public static boolean checkCommandWithArgument(String[] choice, String commandName) {
        if (choice.length != 2) {
            System.out.printf("У команды %s один обязательный аргумент%n", commandName);
            return false;
        }
        return true;
    }
}
