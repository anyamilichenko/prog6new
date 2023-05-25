package dataTransmission;

import commands.AbstractCommand;

import java.io.Serializable;
import java.util.Objects;

public class CommandFromClient implements Serializable {

    private final AbstractCommand abstractCommand;

    public CommandFromClient(AbstractCommand abstractCommand) {
        this.abstractCommand = abstractCommand;
    }

    public AbstractCommand getCommand() {
        return abstractCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandFromClient that = (CommandFromClient) o;
        return Objects.equals(abstractCommand, that.abstractCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abstractCommand);
    }
}
