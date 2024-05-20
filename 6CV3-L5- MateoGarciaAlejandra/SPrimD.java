import java.io.*;
import java.net.*;

public class SPrimD {
    public static void main(String args[]){
        try{
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[2000],2000);
                s.receive(p);
                System.out.println("Datagrama recibido desde"+p.getAddress()+":"+p.getPort());
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(p.getData()));
                int x = dis.readInt();
                float f = dis.readFloat();
                long z = dis.readLong();
                System.out.println("\n\nEntero:"+ x + " Flotante:"+f+" Entero largo:"+z);
            }//for
        //s.close()
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}