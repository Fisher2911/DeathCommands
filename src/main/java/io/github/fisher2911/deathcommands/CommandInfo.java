package io.github.fisher2911.deathcommands;

public class CommandInfo {

    private final String command;
    private final EventType eventType;
    private final Type type;

    public CommandInfo(final String command, final EventType eventType, final Type type) {
        this.command = command;
        this.eventType = eventType;
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

        CONSOLE,

        PLAYER;

    }

    public enum EventType {

        DEATH,

        RESPAWN

    }
}
