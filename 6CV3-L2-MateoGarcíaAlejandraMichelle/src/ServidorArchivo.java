import java.net.*;
import java.io.*;

public class ServidorArchivo {
    public static void main(String[] args) {
        try {
            // Iniciamos el servidor en el puerto 7000
            ServerSocket s = new ServerSocket(7000);
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                // Esperamos una conexión
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde " + cl.getInetAddress() + ":" + cl.getPort());

                // Definimos un flujo de nivel de bits de entrada ligado al socket
                DataInputStream dis = new DataInputStream(cl.getInputStream());

                // Leemos los datos principales del archivo
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo: " + nombre);
                long tam = dis.readLong();

                // Creamos un flujo para escribir el archivo de salida
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

                // Preparamos los datos para recibir los paquetes de datos del archivo
                byte[] b = new byte[1024];
                long recibidos = 0;
                int n;

                // Definimos el ciclo donde estaremos recibiendo los datos enviados por el cliente
                while (recibidos < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    recibidos += n;
                }

                // Cerramos los flujos
                dos.close();
                dis.close();
                cl.close();

                System.out.println("Archivo recibido y guardado: " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }// catch
    }
}
