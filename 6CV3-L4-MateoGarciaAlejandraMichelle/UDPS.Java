import java.io.*;
import java.net.*; 
public class UDPS{
    public static void main(String[] args){
        try{
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");
            for(;;){
            
               // Recepción del mensaje del cliente
               byte[] receiveData = new byte[2000];
               DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
               s.receive(receivePacket);
               System.out.println("Datagrama recibido desde " + receivePacket.getAddress() + ":" + receivePacket.getPort());
               String recive = new String(receivePacket.getData(), 0, receivePacket.getLength());
               System.out.println("Con el mensaje: " + recive);

               // Envío de la respuesta al cliente
               System.out.println("Escribe tu respuesta: ");
               BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
               String mensaje = br.readLine();
               byte[] b = mensaje.getBytes();
               DatagramPacket sendPacket = new DatagramPacket(b, b.length, receivePacket.getAddress(), receivePacket.getPort());
               s.send(sendPacket);
            }//for
            //s.close()
        }catch(Exception e){
                e.printStackTrace();
         }//catch
    }//main


}