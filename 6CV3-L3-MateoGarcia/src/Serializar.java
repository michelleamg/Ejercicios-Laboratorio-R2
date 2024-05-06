import java.io.*;

public class Serializar {
    public static void main(String[] args) {
        try{
            Coche coche1= new Coche();
            coche1.marca="Audi";
            coche1.color="Azul";
            coche1.Km= 125;

            //Serializamos el objeto
            FileOutputStream fos = new FileOutputStream("objetoS.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(coche1);
            oos.flush();
            oos.close();
            FileInputStream fis = new FileInputStream("objetoS.txt");

        } catch (IOException e){
            e.printStackTrace();
        }
}
}
