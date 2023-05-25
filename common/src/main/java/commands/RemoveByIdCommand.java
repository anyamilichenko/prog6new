package commands;


import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class RemoveByIdCommand extends AbstractCommand{
    public RemoveByIdCommand(String arg){
        super(arg, "remove_by_id");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        Long longArg;
        try {
            longArg = Long.parseLong((String) arg);
        }catch (NumberFormatException e){
            return new CommandResult("Your argument is incorrect. The command was not executed");
        }
        if (collectionManager.getMainData().removeIf(x-> x.getID() == longArg)){
            return new CommandResult("The item was successfully deleted");
        }else {
            return new CommandResult("The ID could not be found. The command was not executed");
        }
    }
}
