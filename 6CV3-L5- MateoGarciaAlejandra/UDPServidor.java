import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class UDPServidor {
    // Puerto en el que el servidor escuchará
    public final static int PUERTO = 7;
    // Tamaño máximo del buffer de recepción
    public final static int TAM_MAXIMO = 65507;

    public static void main(String[] args) {
        int port = PUERTO;
        try {
            // Abrir un canal DatagramChannel
            DatagramChannel canal = DatagramChannel.open();
            // Configurar el canal en modo no bloqueante
            canal.configureBlocking(false);
            // Obtener el socket asociado al canal
            DatagramSocket socket = canal.socket();
            // Crear una dirección de socket con el puerto especificado
            SocketAddress dir = new InetSocketAddress(port);
            // Vincular el socket a la dirección
            socket.bind(dir);
            // Abrir un selector para gestionar los eventos de E/S
            Selector selector = Selector.open();
            // Registrar el canal en el selector para operaciones de lectura
            canal.register(selector, SelectionKey.OP_READ);
            // Crear un buffer directo para almacenar datos recibidos
            ByteBuffer buffer = ByteBuffer.allocateDirect(TAM_MAXIMO);

            // Bucle principal del servidor
            while (true) {
                // Esperar a que haya eventos en el selector (tiempo de espera de 5000 ms)
                selector.select(5000);
                // Obtener las claves seleccionadas
                Set<SelectionKey> sk = selector.selectedKeys();
                Iterator<SelectionKey> it = sk.iterator();
                
                // Iterar sobre las claves seleccionadas
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // Eliminar la clave procesada del conjunto
                    it.remove();

                    // Verificar si la clave está lista para lectura
                    if (key.isReadable()) {
                        buffer.clear();
                        // Recibir datos del cliente
                        SocketAddress client = canal.receive(buffer);
                        buffer.flip();
                        // Leer un entero del buffer
                        int eco = buffer.getInt();
                        
                        if (eco == 1000) {
                            // Si el dato recibido es 1000, cerrar el canal y salir
                            canal.close();
                            System.exit(0);
                        } else {
                            // Imprimir el dato leído en la consola
                            System.out.println("Dato leido: " + eco);
                            buffer.flip();
                            // Enviar el dato de vuelta al cliente
                            canal.send(buffer, client);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
