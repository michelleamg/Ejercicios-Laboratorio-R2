import java.net.*;
import java.io.*;

public class Servidor{
    public static void main(String[] args) {
        try {
            // Iniciamos el servidor en el puerto 7000
            ServerSocket s = new ServerSocket(7000);

            // Iniciamos el ciclo infinito y esperamos una conexión
            for (;;) {
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde " + cl.getInetAddress() + ":" + cl.getPort());

                // Definimos un flujo de nivel de bits de entrada ligado al socket
                DataInputStream dis = new DataInputStream(cl.getInputStream());

                // Leemos los datos principales del archivo y creamos un flujo para escribir el archivo de salida
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo: " + nombre);
                long tam = dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

                // Preparamos los datos para recibir los paquetes de datos del archivo
                long recibidos = 0;
                int n, porcentaje;

                // Definimos el ciclo donde estaremos recibiendo los datos enviados por el cliente
                while (recibidos < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    recibidos = recibidos + n;
                    porcentaje = (int) (recibidos * 100 / tam);
                    System.out.print("\n\nArchivo recibido.");
                }// While

                Deserializar des = new Deserializar();
                des.deserializar("objetoS.txt");

                // Cerramos los flujos, el socket y el resto del programa
                dos.close();
                dis.close();
                cl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }// catch
    }
}
