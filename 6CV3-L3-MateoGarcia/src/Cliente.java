import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Se define un flujo de entrada para obtener los datos del servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la dirección del servidor:");
            String host = br.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br.readLine());

            // Se define el socket
            Socket cl = new Socket(host, pto);

            // seleccionamos el archivo
            File file =  new File("objetoS.txt");
            // Obtener información sobre el archivo
            String nombre = file.getName(); // Nombre del archivo
            long tam = file.length(); // Tamaño del archivo/

            // / Se definen dos flujos orientados a bytes, uno se usa para leer el archivo y el otro para enviar los datos por el socket
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(file));

            // Enviamos los datos generales del archivo por el socket
            dos.writeUTF(nombre);
            dos.flush();
            dos.writeLong(tam);
            dos.flush();

            // Leemos los datos contenidos en el archivo en paquetes de 1024 y los enviamos por el socket
            byte[] b = new byte[1024];
            long enviados = 0;
            int leidos;

            while ((leidos = dis.read(b)) != -1) {
                dos.write(b, 0, leidos);
                dos.flush();
                enviados += leidos;
            }// While

            // Cerramos los flujos, el socket, terminamos bloques y cerramos la clase
            System.out.print("\n\nArchivo enviado");
            dos.close();
            dis.close();
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
