package commands;


import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class RemoveLast extends AbstractCommand{
    public RemoveLast(){
        super("", "remove_last");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());

        collectionManager.getMainData().removeLast();
        return new CommandResult("The last element has been successfully deleted");
    }
}
