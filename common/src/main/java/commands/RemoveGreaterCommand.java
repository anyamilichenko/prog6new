package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class RemoveGreaterCommand extends AbstractCommand{
    public RemoveGreaterCommand(Dragon arg){
        super(arg, "remove_greater");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        Dragon dragon = (Dragon) arg;
        collectionManager.getMainData().removeIf(x->x.compareTo(dragon) > 0);
        return new CommandResult("Greater elements have been successfully removed");
    }
}
