package commands;


import dataTransmission.CommandResult;
import utilities.InputManager;

import java.io.File;
import java.io.IOException;

public class ExecuteScriptCommand {
    private final String arg;
    public ExecuteScriptCommand(String arg){
        this.arg = arg;
    }

    public void execute(InputManager inputManager){
        try{
            inputManager.connectToFile(new File(arg));
            new CommandResult("Starting the ispolnenie scripta");
        }catch (IOException e){
            new CommandResult("There was a problem with opening the file. Check, dostupen li on i pravilno li vi zapicali ego v commandnoi ctroke");
        }catch (UnsupportedOperationException e){
            new CommandResult(e.getMessage());
        }
    }
}