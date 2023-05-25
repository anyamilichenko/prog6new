package utilities;

import dataTransmission.CommandFromClient;
import exceptions.DataCantBeSentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;

public class RequestSender {

    private static final int timeoutToSend = 10;
    private final String serverIp;
    private final int serverPort;
    private static final int countOfBytesForSize = 4;


    public RequestSender(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }


    //отправляет запрос серверу через созданный канал датаграм. Сначала он привязывает созданный канал датаграм к IP-адресу и порту клиента.
// Затем он создает объект типа InetSocketAddress с IP-адресом и портом сервера и использует его для отправки данных на сервер
    public void send(DatagramChannel datagramChannel, CommandFromClient commandFromClientDto) throws IOException, DataCantBeSentException {

        SocketAddress serverSocketAddress = new InetSocketAddress(serverIp, serverPort);

        Pair<byte[], byte[]> pair = serialize(commandFromClientDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();

        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = timeoutToSend;
            while (datagramChannel.send(sendDataAmountWrapper, serverSocketAddress) < sendDataAmountBytes.length) {
                limit -= 1;
                System.out.println("Failed to send, please try again");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            }
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, serverSocketAddress) < sendDataBytes.length) {
                limit -= 1;
                System.out.println("Failed to send, please try again");
                if (limit == 0) {
                    throw new DataCantBeSentException();
                }
            }
        } catch (IOException e) {
            System.out.println("The address you specified could not be resolved. Please check this and maybe restart the client.");
            e.printStackTrace();
        } catch (UnresolvedAddressException e) {
            System.out.println("The data could not be sent because it was too large");
        }
    }


    //принимает объект и возвращает пару байтовых массивов. Он используется для сериализации объекта в массив байтов,
    // который затем отправляется на удаленный сервер
    /**
     * @return первое - сами данные, второе - количество байт в данных
     */
    private Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(countOfBytesForSize).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes);
    }
}
