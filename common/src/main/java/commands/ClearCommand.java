package commands;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class ClearCommand extends AbstractCommand {
    public ClearCommand() {
        super("", "clear");
    }

    @Override
    public CommandResult execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // stream api would not help
        collectionManager.getMainData().clear();
        return new CommandResult("The collection was successfully cleared.");
    }
}
