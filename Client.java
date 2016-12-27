import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Кемко on 27.12.2016.
 */
public class Client implements Runnable{
    Socket socket;
    DataInputStream in;

    public Client(Socket socket, DataInputStream in) {
        this.socket = socket;
        this.in = in;
        (new Thread(this)).start();
    }

    @Override
    public void run() {
        String line;
        try {
            while (true) {
                line = in.readUTF();
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] ar) {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name");
            String name = null;
            name = keyboard.readLine().trim();
            new Client(socket,in);
            String line = null;
            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();
            while (true) {
                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                out.writeUTF(name + ": " + line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}

