package commands;


import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.io.Serializable;
import java.time.LocalDate;

public class InfoCommand extends AbstractCommand {

    public InfoCommand() {
        super("", "info");
    }

    @Override
    public CommandResult execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());

        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult(new InfoCommandResult(0,
                    collectionManager.getCreationDate())
            );
        }
        return new CommandResult(new InfoCommandResult(collectionManager.getMainData().size(),
                collectionManager.getCreationDate())
        );
        // Stream api would not help
    }

    private static final class InfoCommandResult implements Serializable {
        private final int numberOfElements;
        private final LocalDate creationDate;

        private InfoCommandResult(Integer numberOfElements, LocalDate creationDate) {
            this.numberOfElements = numberOfElements;
            this.creationDate = creationDate;
        }

        @Override
        public String toString() {
            return "InfoCommandResult{"
                    + "numberOfElements='" + numberOfElements + '\''
                    + ", creationDate=" + creationDate
                    + '}';
        }
    }
}
