package commands;

import data.Dragon;

import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

import java.util.Comparator;
import java.util.Optional;

public class AddCommand extends AbstractCommand{
    public AddCommand(Dragon arg){
        super(arg, "add");
    }


    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        historyManager.addNote((this.getName()));
        Dragon dragon = (Dragon) arg;
        Dragon maxDragon = (collectionManager.getMainData()).stream()
                .max(Comparator.comparing(Dragon::getID))
                .orElse(null);
        //collectionManager.getMainData().incrementMaxID(collectionManager.getMainData());
        //Optional<Dragon> a = (collectionManager.getMainData().stream().max()) + 1L;
        //dragon.setId((CollectionManager.mainData).stream());
        //dragon.setId(maxDragon + 1L); //С getmaxid проблемы, возможно будут проблемы с добавлением элементов

        if (maxDragon != null) {
            long newID = maxDragon.getID() + 1L;
            dragon.setId(newID);
        }


        collectionManager.getMainData().add(dragon);

        return new CommandResult("item successfully added");
    }
}

