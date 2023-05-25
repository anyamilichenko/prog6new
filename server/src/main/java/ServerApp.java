

import dataTransmission.CommandResult;
import org.apache.logging.log4j.Logger;
import commands.AbstractCommand;
import commands.SaveCommand;
import data.Dragon;

import exceptions.DataCantBeSentException;

import utilities.*;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;





@SuppressWarnings("FieldCanBeLocal")
public class ServerApp {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Logger logger;
    private static final int countOfBytesForSize = 4;

    private String stringData;
    private final int timeoutToSend = 10;

    public ServerApp(HistoryManager historyManager, CollectionManager collectionManager, FileManager fileManager, Logger logger) {
        this.logger = logger;
        this.collectionManager = collectionManager;
        this.historyManager = historyManager;
        this.fileManager = fileManager;
    }
    //Запускает сервер используя переданный порт и IP-адрес
    public void start(int serverPort, String serverIp) throws IOException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            initialise(datagramChannel, serverIp, serverPort);
            boolean isWorkingState = true;
            datagramChannel.configureBlocking(false);
            Scanner scanner = new Scanner(System.in);
            ResponseSender responseSender = new ResponseSender(logger);
            RequestCatcher requestCatcher = new RequestCatcher(logger);
            SaveCommand saveC = new SaveCommand(fileManager);
            while (isWorkingState) {
                if (System.in.available() > 0) {
                    final String inp = scanner.nextLine();
                    if ("exit".equals(inp)) {
                        isWorkingState = false;
                    }
                    if ("save".equals(inp)) {
                        System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
                    }
                }
                byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
                ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
                SocketAddress clientSocketAddress = datagramChannel.receive(amountOfBytesHeaderWrapper);
                if (Objects.nonNull(clientSocketAddress)) {
                    AbstractCommand command = requestCatcher.receive(amountOfBytesHeader, datagramChannel);
                    CommandResult commandResultDto = command.execute(collectionManager, historyManager);
                    saveC.execute(collectionManager, historyManager);
                    logger.info("the command with the result is executed: " + commandResultDto.toString());
                    responseSender.send(commandResultDto, datagramChannel, clientSocketAddress);
                }
            }
            System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
        } catch (DataCantBeSentException | InterruptedException e) {
            logger.info("Could not send data to client");
        } catch (BindException e) {
            logger.error("Failed to use these ports and ip, BindException. Please restart the server with other arguments");
        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
            logger.error("There was a problem with the data file. Please check if it is available.");
        }
    }

    //инициализирует серверную часть приложения. Он создает и привязывает к заданному адресу датаграммный канал,
// загружает коллекцию объектов Dragon из файла с помощью FileManager и JSON-парсера, и использует CollectionManager для инициализации данных коллекции
    private void initialise(DatagramChannel datagramChannel, String serverIp, int serverPort) throws IOException {
        datagramChannel.bind(new InetSocketAddress(serverIp, serverPort));
        logger.info("Creating a datagram channel with ip: " + serverIp);
        stringData = fileManager.read();
        LinkedList<Dragon> dragons = new JsonParser().deSerialize(stringData);
        collectionManager.initialiseData(dragons);
        logger.info("The collection is initialized. Ready to receive data.");
    }
}
