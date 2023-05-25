package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.util.LinkedList;
import java.util.Objects;

public class CountGreater extends AbstractCommand{
    public CountGreater(String arg){
        super(arg, "count_greater_than_age");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote(this.getName());
        long age = Long.parseLong(((String) arg).trim());
        LinkedList<Dragon> dragons = collectionManager.getMainData();
        System.out.println("the number of elements whose age field value is greater than the specified one: ");

        return new CommandResult(dragons.stream()
                .filter(Objects::nonNull)
                .filter(s -> Long.compare(s.getAge(), age) <= -1)
                .map(Objects::toString)
                .count());
    }
}