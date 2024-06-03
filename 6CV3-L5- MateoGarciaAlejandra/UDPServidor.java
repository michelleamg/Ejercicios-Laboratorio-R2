import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class UDPServidor {
    public final static int PUERTO = 7;          // Puerto de escucha
    public final static int TAM_MAXIMO = 65507;  // Tamaño máximo del buffer

    public static void main(String[] args) {
        int port = PUERTO;
        try {
            DatagramChannel canal = DatagramChannel.open(); // Abrir canal DatagramChannel
            canal.configureBlocking(false);                 // Configurar canal no bloqueante
            DatagramSocket socket = canal.socket();
            socket.bind(new InetSocketAddress(port));       // Vincular socket al puerto

            Selector selector = Selector.open();            // Abrir selector
            canal.register(selector, SelectionKey.OP_READ); // Registrar canal para lectura

            ByteBuffer buffer = ByteBuffer.allocateDirect(TAM_MAXIMO); // Buffer para datos

            while (true) {
                selector.select(5000);                       // Esperar eventos (5000 ms)
                Set<SelectionKey> sk = selector.selectedKeys();
                Iterator<SelectionKey> it = sk.iterator();
                
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isReadable()) {
                        buffer.clear();
                        SocketAddress client = canal.receive(buffer); // Recibir datos
                        buffer.flip();
                        int eco = buffer.getInt();                    // Leer entero

                        if (eco == 1000) {                            // Cerrar si el eco es 1000
                            canal.close();
                            System.exit(0);
                        } else {
                            System.out.println("Dato leído: " + eco); // Imprimir dato
                            buffer.flip();
                            canal.send(buffer, client);               // Enviar dato de vuelta
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
