package commands;

import data.Dragon;
import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.util.LinkedList;

public class PrintFieldDescendingCharacter extends AbstractCommand {
    public PrintFieldDescendingCharacter() {
        super("", "print_field_descending_character");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager) {
        historyManager.addNote(this.getName());
        LinkedList<Dragon> dragons = collectionManager.getMainData();

        return new CommandResult(dragons);
    }
}