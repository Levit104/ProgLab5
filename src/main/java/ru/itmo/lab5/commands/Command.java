package ru.itmo.lab5.commands;

public interface Command {
    public boolean hasArgument();
    public String getName();
    public String getDescription();
    public void execute(String argument);
}
