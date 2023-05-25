package utilities;

import data.Dragon;

import java.time.LocalDate;
import java.util.LinkedList;

public interface CollectionManager {

    void initialiseData(LinkedList<Dragon> linkedList);

    LocalDate getCreationDate();

    LinkedList<Dragon> getMainData();

    static Long getMaxId() {
        return null;
    }
    void incrementMaxID(LinkedList<Dragon> linkedList);

}
