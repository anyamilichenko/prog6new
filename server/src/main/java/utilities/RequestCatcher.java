package utilities;

import commands.AbstractCommand;
import commands.HelpCommand;
import dataTransmission.CommandFromClient;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestCatcher {
    private static final int serverWaitingPeriod = 50;
    private final Logger logger;

    public RequestCatcher (Logger logger) {
        this.logger = logger;
    }


    //получает команду от клиента по сети, декодирует ее из массива байтов, и возвращает объект типа AbstractCommand, который содержит данные команды.
    public AbstractCommand receive(byte[] amountOfBytesHeader, DatagramChannel datagramChannel) throws IOException, InterruptedException {
        // Получение
        byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];

        ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);

        Thread.sleep(serverWaitingPeriod);

        SocketAddress checkAddress = datagramChannel.receive(dataBytesWrapper);
        while (checkAddress == null) {
            checkAddress = datagramChannel.receive(dataBytesWrapper);
        }

        CommandFromClient commandFromClientDto;
        try {
            commandFromClientDto = (CommandFromClient) deserialize(dataBytes);
        } catch (ClassNotFoundException e) {
            return new HelpCommand();
        }
        logger.info("Data object received: " + commandFromClientDto.getCommand().toString());
        return (commandFromClientDto).getCommand();
    }

    //выполняет десериализацию байтового массива data в объект Java
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    //преобразует массив байтов в целое число типа int
    public static int bytesToInt(byte[] bytes) {
        final int vosem = 8;
        final int ff = 0xFF;

        int value = 0;
        for (byte b : bytes) {
            value = (value << vosem) + (b & ff);
        }
        return value;
    }

}
