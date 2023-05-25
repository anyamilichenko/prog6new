package utilities;

import dataTransmission.CommandResult;
import exceptions.DataCantBeSentException;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseSender {
    private static final int countOfBytesForSize = 4;
    private final Logger logger;
    private static final int timeoutToSend = 10;

    public ResponseSender(Logger logger) {
        this.logger = logger;
    }

    //Отправляет результат выполненной команды клиенту через DatagramChannel
    public void send(CommandResult commandResultDto, DatagramChannel datagramChannel, SocketAddress clientSocketAddress) throws IOException, DataCantBeSentException {
        // Send
        Pair<byte[], byte[]> pair = serialize(commandResultDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();


        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = timeoutToSend;
            while (datagramChannel.send(sendDataAmountWrapper, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("failed to send, please try again");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            }
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("failed to send, please try again");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            }
            logger.info("sent the result of the command to the client");
        } catch (IOException e) {
            logger.error("failed to send data to the client because the message is too large");
        }
    }


    //сериализует объект в массив байтов и возвращает пару массивов байтов - первый массив содержит сериализованные данные объекта,
    // а второй массив содержит количество байтов в первом массиве, записанное в виде 4-байтового целого числа (используется для передачи данных по сети с помощью UDP протокола)
    /**
     * @return первое - сами данные, второе - количество байтов в данных
     */
    public Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(countOfBytesForSize).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes);
    }
}
