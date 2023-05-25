package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowCommand extends AbstractCommand{
    public ShowCommand(){
        super("", "show");
    }

    @Override
    public CommandResult execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ){
        historyManager.addNote(this.getName());
        return new CommandResult((Serializable) collectionManager.getMainData().stream().sorted(Comparator.comparing(Dragon::getName)).collect(Collectors.toList()));
    }
}
