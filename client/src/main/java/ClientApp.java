import dataTransmission.CommandFromClient;
import dataTransmission.CommandResult;
import exceptions.DataCantBeSentException;
import exceptions.NoAnswerException;
import utilities.Pair;
import utilities.RequestSender;
import utilities.ResponseCatcher;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;

@SuppressWarnings("FieldCanBeLocal")
public class ClientApp {
    private final int clientPort;
    private final int serverPort;
    private final String clientIp;
    private final String serverIp;
    private final int waitingTime = 500;
    private final int countOfBytesForSize = 4;


    public ClientApp(int clientPort, int serverPort, String clientIp, String serverIp) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.clientIp = clientIp;
        this.serverIp = serverIp;
    }

    //отправляет команду от клиента на сервер и получает результат выполнения команды от сервера
//создает неблокирующий канал DatagramChannel, отправляет сериализованный объект команды на сервер с помощью метода send,
// а затем получает результат выполнения команды от сервера с помощью метода receiveCommandResult
    public CommandResult sendCommand(CommandFromClient commandFromClient) throws DataCantBeSentException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false);
            RequestSender requestSender = new RequestSender(serverIp, serverPort);
            requestSender.send(datagramChannel, commandFromClient);
            return receiveCommandResult(datagramChannel);
        } catch (BindException e) {

            return new CommandResult("Failed to send data to the address, binding exception. Please restart the client with other arguments");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult("Something went wrong when executing the command, message: " + e.getMessage());
        }
    }

    //ожидает ответ от сервера, принимая на вход DatagramChannel и используя его для чтения данных из сети,
// и возвращает объект типа CommandResultDto. Если ответ не был получен в течение заданного времени ожидания,
// или если полученные данные не могут быть десериализованы в CommandResultDto, метод возвращает объект CommandResultDto с сообщением об ошибке.
    private CommandResult receiveCommandResult(DatagramChannel datagramChannel) throws IOException {
        byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
        ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
        try {
            ResponseCatcher.receiveToBuffer(datagramChannel, amountOfBytesHeaderWrapper, waitingTime);
            byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];


            ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);

            ResponseCatcher.receiveToBuffer(datagramChannel, dataBytesWrapper, 1);

            return (CommandResult) deserialize(dataBytes);

        } catch (NoAnswerException e) {
            return new CommandResult("Could not get any otvet from the server");
        } catch (ClassNotFoundException e) {
            return new CommandResult("An incorrect otvet was received from the server");
        }
    }

    public static int bytesToInt(byte[] bytes) {
        final int vosem = 8;
        final int ff = 0xFF;

        int value = 0;
        for (byte b : bytes) {
            value = (value << vosem) + (b & ff);
        }
        return value;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
