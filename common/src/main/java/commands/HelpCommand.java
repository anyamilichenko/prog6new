package commands;


import dataTransmission.CommandResult;
import utilities.CollectionManager;
import utilities.HistoryManager;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("", "help");
    }

    @Override
    public CommandResult execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // stream api would not help
        return new CommandResult(
                "•\\thelp : output help for available commands\\n\" +\n" +
                        "\"•\\tinfo : output information about the collection (type, initialization date, number of elements, etc.) to the standard output stream\\n\" +\n" +
                        "\"•\\tshow : output to the standard output stream all the elements of the collection in the string representation\\n\" +\n" +
                        "\"•\\tadd {element} : add a new element to the collection\\n\" +\n" +
                        "\"•\\tupdate id {element} : update the value of the collection element whose id is equal to the specified\\n\" +\n" +
                        "                        \"•\\tremove_by_id id : delete an item from the collection by its id\\n\" +\n" +
                        "\"•\\tclear : clear the collection\\n\"+\n" +
                        "\"•\\tsave : save the collection to a file\\n\" +\n" +
                        "\"•\\texecute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.\\n\" +\n" +
                        "\"•\\texit : terminate the program (without saving to a file)\\n\" +\n" +
                        "                        \"•\\tremove_last : remove the last element from the collection\\n\" +\n" +
                        "\"•\\tremove_greater {element} : remove from the collection all elements exceeding the specified \\n\" +\n" +
                        "\"•\\treorder : sort the collection in the reverse order of the current\\n\" +\n" +
                        "\"•\\tremove_all_by_weight weight : remove from the collection all elements whose weight field value is equivalent to the specified \\n\" +\n" +
                        "\"•\\tcount_greater_than_age age : print the number of elements whose age field value is greater than the specified\\n\" +\n" +
                        "                        \"•\\tprint_field_descending_character : output the values of the character field of all elements in descending order \\n");
    }
}
