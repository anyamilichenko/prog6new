package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.util.Collections;
import java.util.LinkedList;

public class Reorder extends AbstractCommand{
    public Reorder(){
        super("", "reorder");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        LinkedList<Dragon> dragons = collectionManager.getMainData();
        Collections.reverse(dragons);
        return new CommandResult("The collection has been successfully flipped");

    }
}
