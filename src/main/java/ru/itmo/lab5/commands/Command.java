package ru.itmo.lab5.commands;

/**
 * Интерфейс, описывающий поведение всех команд
 */

public interface Command {
    /**
     * Устанавливает есть ли у команды аргумент
     * 
     * @return {@code true} если аргумент есть, иначе {@code false}
     */
    boolean hasArgument();

    /**
     * Возвращает имя команды
     * 
     * @return имя команды
     */

    String getName();

    /**
     * Возвращает описание команды
     * 
     * @return описание команды
     */

    String getDescription();

    /**
     * Запускает выполнение команды
     * 
     * @param argument аргумент команды (если есть)
     */

    void execute(String argument);
}
