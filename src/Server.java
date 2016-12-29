/**
 * Created by Кемко on 25.12.2016.
 */
import java.net.*;
import java.io.*;
public class Server {

    public static void main(String[] args)    {
        boolean isON = true;
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)


        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");
            System.out.println();
            while (isON) {
                new SocketDispatcher(ss.accept()).start();
                System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            }
            System.out.println("Terminating server...");
            ss.close();
        } catch(Exception x) {
            x.printStackTrace();
        }
    }
}