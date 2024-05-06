import java.io.*;

public class Deserializar {

    public void deserializar(String direccion) {
        try {
            // Leer el contenido del archivo línea por línea
            BufferedReader br = new BufferedReader(new FileReader(direccion));
            String linea;
            StringBuilder contenido = new StringBuilder();
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
                contenido.append("\n"); // Agregar un salto de línea
            }
            br.close(); // Cerrar el BufferedReader cuando termine

            // Imprimir el contenido del archivo
            System.out.println("Contenido del archivo:");
            System.out.println(contenido.toString());

            // Deserializar el objeto Coche
            FileInputStream fis = new FileInputStream(direccion);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Coche coche = (Coche) ois.readObject();
            ois.close(); // Cerrar el ObjectInputStream cuando termine

            // Imprimir el objeto Coche
            System.out.println("Objeto Coche deserializado:");
            System.out.println(coche);

        } catch (IOException | ClassNotFoundException e) {
            // Manejar las excepciones de E/S y ClassNotFoundException
            e.printStackTrace();
        }
    }
}
