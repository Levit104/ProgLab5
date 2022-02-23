package ru.itmo.lab5.commands;

public interface Command {
    public String getName();
    public String getDescription();
    public void execute(String argument);
}
