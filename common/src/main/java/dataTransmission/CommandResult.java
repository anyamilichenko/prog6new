package dataTransmission;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private final Serializable output;
    public CommandResult(Serializable output){
        this.output = output;
    }

    public Serializable getOutput(){
        return output;
    }

    @Override
    public  String toString(){
        return "CommandResult{"
                + "output='" + output + '\''
                + '}';
    }
}