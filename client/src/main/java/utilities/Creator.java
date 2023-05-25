package utilities;

import data.Coordinates;
import data.Dragon;
import data.DragonCave;
import data.DragonCharacter;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;

public class Creator {
    private static final String ERROR_MESSAGE = "You vveli the wrong type. Try again";
    private final OutputManager outputManager;
    private final Asker asker;

    public Creator(InputManager inputManager, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.asker = new Asker(inputManager, outputManager);
    }

    public Dragon makeDragon() throws IOException {
        return askForDragon();
    }


    private Dragon askForDragon() throws IOException {
        outputManager.println("Enter data about the dragon");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter a name (String)", //Поле не может быть null, Строка не может быть пустой
                ERROR_MESSAGE, "The string must not be empty", x -> x, false);
        Coordinates coordinates = askForCoordinates(); //not null

        long age = asker.ask(arg -> (arg) > 0, "Enter the age of the dragon (long) (May be empty)",
                ERROR_MESSAGE, "The entered value must be greater than 0. Try again", Long::parseLong, true); // >0

        DragonCharacter dragonCharacter = asker.ask(arg -> ( arg.toString()).length() > 0, "Enter semesterEnum (CUNNING, EVIL, GOOD, CHAOTIC, CHAOTIC_EVIL) (can't be zero)",
                ERROR_MESSAGE, ERROR_MESSAGE, DragonCharacter::valueOf, true); // null-able
        DragonCave dragonCave = askForDragonCave();

        Integer wingspan = asker.ask(arg -> (arg) > 0 && (arg.toString()).length() > 0, "Enter the dragon's wingspan (Integer) (Cannot be empty)", ERROR_MESSAGE, "The string should not be empty, and the value should not" +
                "be less than zero", Integer::parseInt, false );

        Double weight = asker.ask(arg -> (arg) > 0 && (arg.toString()).length() > 0, "Enter the dragon's weight (Double) (Cannot be empty)", ERROR_MESSAGE, "The string should not be empty, and the value should not" +
                "be less than zero", Double::parseDouble, false);

        return new Dragon(name, coordinates, age, dragonCharacter, dragonCave, wingspan, weight);
    }

//true/false

    private  Coordinates askForCoordinates() throws IOException {
        outputManager.println("Enter coordinates data");
        final double yLimitation = -513;
        Double x = asker.ask(arg -> true, "Enter the x value (Double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Double::parseDouble, false);
        Double y = asker.ask(arg -> (arg) >= yLimitation, "Enter the y value (Double)",
                ERROR_MESSAGE, "The field value must be greater than -513. Try again", Double::parseDouble, false); //Значение поля должно быть больше -513, Поле не может быть null
        return new Coordinates(x, y);
    }


    private DragonCave askForDragonCave() throws IOException{
        outputManager.println("Enter information about the dragon cave");

        float numberOfTreasures = asker.ask(arg -> (arg) > 0.0f, "Enter the number of treasures in the dragon cave (float) (May be empty)",
                ERROR_MESSAGE, "The entered value must be greater than 0. Try again", Float::parseFloat, true); // >0

        Double depth = asker.ask((arg) -> true, "Enter the depth of the Dragon cave (Double) (May be empty)", ERROR_MESSAGE, "", Double::parseDouble, true);


        return new DragonCave(depth, numberOfTreasures);
    }

    public static class Asker {
        private final InputManager inputManager;
        private final OutputManager outputManager;


        public Asker(InputManager inputManager, OutputManager outputManager) {
            this.inputManager = inputManager;
            this.outputManager = outputManager;
        }

        public <T> T ask(Predicate<T> predicate,
                         String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         Function<String, T> converter,
                         boolean nullable) {
            outputManager.println(askMessage);
            String input;
            T value;
            do {
                try {
                    input = inputManager.nextLine();
                    if ("".equals(input) && nullable) {
                        return null;
                    }

                    value = converter.apply(input);

                } catch (IllegalArgumentException | IOException e) {
                    outputManager.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return value;
                } else {
                    outputManager.println(wrongValueMessage);
                }
            } while (true);
        }
    }
}