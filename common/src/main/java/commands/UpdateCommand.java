package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class UpdateCommand extends AbstractCommand {

    private final String idArg;

    public UpdateCommand(Dragon dragon, String id) {
        super(dragon, "update");
        this.idArg = id;
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        Long longArg;
        try {
            longArg = Long.parseLong(idArg);
        }catch (NumberFormatException e){
            return new CommandResult("Your argument is incorrect. Command not executed");
        }
        if (collectionManager.getMainData().removeIf(x -> x.getID() == longArg)){
            Dragon dragon = (Dragon) arg;
            dragon.setId(longArg);
            collectionManager.getMainData().add(dragon);
            return new CommandResult("The item was successfully updated");
        }else {
            return new CommandResult("ID not found. The command was not executed");
        }
    }
}
