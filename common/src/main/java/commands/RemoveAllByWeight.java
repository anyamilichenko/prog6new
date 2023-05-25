package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.util.LinkedList;

public class RemoveAllByWeight extends AbstractCommand{
    public RemoveAllByWeight(String arg){
        super(arg, "remove_all_by_weight");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        LinkedList<Dragon> dragons = collectionManager.getMainData();
        if (arg.equals("null")) dragons.removeIf(dragon -> dragon.getWeight() == null);
        else dragons.removeIf(dragonn -> dragonn.getWeight() != null &&
                dragonn.getWeight().toString().equals(arg.toString().toUpperCase()));

        return new CommandResult("Collection items have been successfully deleted!");
    }
}
