package commands;


import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {
    protected final Serializable arg;
    private final String name;

    protected AbstractCommand(Serializable arg, String name) {
        this.arg = arg;
        this.name = name;
    }

    public abstract CommandResult execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    );

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", arg=" + arg
                + '}';
    }
}
