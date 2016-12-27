import javax.imageio.stream.ImageOutputStreamImpl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Кемко on 25.12.2016.
 */
public class SocketDispatcher extends Thread {
    private Socket socket;
    DataInputStream in = null;
    DataOutputStream out = null;
    boolean isOn;
    protected static List<SocketDispatcher> clients = Collections.synchronizedList(new ArrayList<SocketDispatcher>());

    public SocketDispatcher(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            isOn = true;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            clients.add(this);
            String line = null;
            while (isOn) {
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                broadcast(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast (String message){
        synchronized (clients) {
            Iterator<SocketDispatcher> it = clients.iterator();
            while (it.hasNext()) {
                SocketDispatcher c = it.next();
                try {
                    synchronized (c.out) {
                        c.out.writeUTF(message);
                    }
                    c.out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    c.isOn = false;
                }
            }
        }
    }




}
