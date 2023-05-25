package utilities;
import data.Dragon;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;


public class CollectionManagerImpl implements CollectionManager {

    private final LocalDate creationDate = LocalDate.now();
    private LinkedList<Dragon> mainData = new LinkedList<>();


    public void initialiseData(LinkedList<Dragon> linkedList) {
        this.mainData = linkedList;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LinkedList<Dragon> getMainData() {
        return mainData;
    }



    public Long getMaxId() {
        Long maxId = 0L;
        for (Dragon dragon : mainData) {
            if (dragon.getID() > maxId) {
                maxId = dragon.getID();
            }
        }
        return (maxId);
    }

    public void incrementMaxID(LinkedList<Dragon> mainData) {
        if (mainData.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        Dragon maxDragon = mainData.stream()
                .max(Comparator.comparing(Dragon::getID))
                .orElse(null);

        if (maxDragon != null) {
            int maxID = Math.toIntExact(maxDragon.getID());
            maxDragon.setId(maxID + 1);
        } else {
            System.out.println("Не удалось найти максимальный элемент.");
        }
    }

//    public void incrementMaxValue(LinkedList<Dragon> mainData) {
//        if (mainData.isEmpty()) {
//            System.out.println("Список пуст.");
//            return;
//        }
//
//        Dragon maxDragon = mainData.stream()
//                .max(Comparator.comparing(Dragon::getValue))
//                .orElse(null);
//
//        if (maxDragon != null) {
//            int maxValue = maxDragon.getValue();
//            maxDragon.setValue(maxValue + 1);
//            System.out.println("Максимальное значение увеличено на 1: " + maxDragon.getValue());
//        } else {
//            System.out.println("Не удалось найти максимальный элемент.");
//        }
//    }


//    public long newId() {
//        if (mainData.isEmpty()) {
//            return 1L;
//        }
//        Long lastId = Long.MIN_VALUE;
//        for (Iterator<Dragon> iter = mainData.iterator(); iter.hasNext();) {
//            Dragon d = iter.next();
//            lastId = Math.max(lastId, d.getID());
//        }
//        return lastId + 1;
//    }
}