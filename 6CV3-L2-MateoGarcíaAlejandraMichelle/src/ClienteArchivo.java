import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class ClienteArchivo {
    public static void main(String[] args) {
        try {
            // Establecemos la conexión con el servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Escriba la dirección del servidor: ");
            String host = br.readLine();
            System.out.print("Escriba el puerto: ");
            int pto = Integer.parseInt(br.readLine());
            //Socket cl = new Socket(host, pto);

            // Creamos un JFileChooser para elegir los archivos a enviar
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true); // Permitimos seleccionar múltiples archivos
            int r = jf.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {
                File[] files = jf.getSelectedFiles(); // Obtenemos los archivos seleccionados

                // Iteramos sobre cada archivo seleccionado y lo enviamos al servidor
                for (File file : files) {
                    try {
                        // Establecemos la conexión con el servidor
                        Socket cl = new Socket(host, pto);

                        String nombre = file.getName();
                        long tam = file.length();

                        // Creamos flujos para enviar los datos al servidor
                        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                        DataInputStream dis = new DataInputStream(new FileInputStream(file));

                        // Enviamos los datos generales del archivo al servidor
                        dos.writeUTF(nombre);
                        dos.flush();
                        dos.writeLong(tam);
                        dos.flush();

                        // Leemos los datos del archivo y los enviamos al servidor
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = dis.read(buffer)) != -1) {
                            dos.write(buffer, 0, bytesRead);
                            dos.flush();
                        }

                        // Cerramos los flujos
                        dis.close();
                        dos.close();
                        cl.close();

                        System.out.println("Archivo enviado: " + nombre);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}