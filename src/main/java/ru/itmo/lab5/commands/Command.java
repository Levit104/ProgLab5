package ru.itmo.lab5.commands;

/**
 * Интерфейс, описывающий поведение всех команд
 */

public interface Command {
    /**
     * Устанавливает есть ли команда аргумент
     * 
     * @return {@code true} если аргумент есть, иначе {@code false}
     */
    public boolean hasArgument();

    /**
     * Возвращает имя команды
     * 
     * @return имя команды
     */

    public String getName();

    /**
     * Возвращает описание команды
     * 
     * @return описание команды
     */
    
    public String getDescription();

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды (если есть)
     */
    
    public void execute(String argument);
}
