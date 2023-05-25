package commands;

import dataTransmission.CommandResult;

import utilities.CollectionManager;
import utilities.FileManager;
import utilities.HistoryManager;
import utilities.JsonParser;

import java.io.FileNotFoundException;

public class SaveCommand extends AbstractCommand {
    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager){
        super("", "save");
        this.fileManager = fileManager;
    }


    @Override
    public CommandResult execute(CollectionManager collectionManager, HistoryManager historyManager){
        try{
            fileManager.save(new JsonParser().serialize(collectionManager.getMainData()));
        }catch (FileNotFoundException e) {
            return new CommandResult("There was a problem with saving the file. Please restart the program again");
        }
        return new CommandResult("The data was successfully saved");
    }
}
