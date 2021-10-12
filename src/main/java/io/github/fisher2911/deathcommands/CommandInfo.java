package io.github.fisher2911.deathcommands;

public class CommandInfo {

    private final String command;
    private final Type type;

    public CommandInfo(final String command, final Type type) {
        this.command = command;
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

        CONSOLE,

        PLAYER;

    }
}
