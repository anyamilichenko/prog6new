
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import utilities.*;

import java.io.IOException;



public class Server {

    private static int serverPort;
    private static String serverIp;
    private static String filename;
    private static final int MAX_PORT = 65535;

    private static final Logger LOGGER = (Logger) LogManager.getLogger(Server.class);

    //private static final Logger LOGGER
    //        = LoggerFactory.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is a cluzhebnii class, and its iczemplar cannot be created");
    }

    public static void main(String[] args) {
        try {
            setParameterValues(args);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            LOGGER.error("Found Invalid arguments. Please use this program as \"java -jar <name> <datafile name> <server port> <ip>\"");
            e.printStackTrace();
            return;
        }
        CollectionManager collectionManager = new CollectionManagerImpl();
        HistoryManager historyManager = new HistoryManagerImpl();
        FileManager fileManager = new FileManager(filename);
        ServerApp serverApp;
        try {
            serverApp = new ServerApp(historyManager, collectionManager, fileManager, LOGGER);
            serverApp.start(serverPort, serverIp);
        } catch (IOException e) {
            LOGGER.error("Proizoshla neozhidannaya mistake of vvod-vivod. Message: " + e.getMessage());
        }
    }

    private static void setParameterValues(String[] args) throws IllegalArgumentException, IndexOutOfBoundsException {
        filename = args[0];
        serverPort = Integer.parseInt(args[1]);
        if (serverPort > MAX_PORT) {
            throw new IllegalArgumentException("Port number out of range");
        }
        serverIp = args[2];
    }
}
