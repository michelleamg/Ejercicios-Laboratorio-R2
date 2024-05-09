import java.io.*;
import java.net.*; 
public class UDPS{
    public static void main(String[] args){
        try{
            DatagramSocket s = new DatagramSocket(2000);
            System.out.println("Servidor iniciado, esperando cliente");
            for(;;){
            
               // Recepción del mensaje del cliente
               byte[] receiveData = new byte[1024];
               DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
               s.receive(receivePacket);
               int npaq = Integer.parseInt(new String(receivePacket.getData(), 0, receivePacket.getLength()).trim());
              
               //recibir los paquetes y ensamblarlos
              ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                for (int i = 0; i <npaq; i++) {
                    byte[] recbdato = new byte[1024];
                    DatagramPacket rcbpaq = new DatagramPacket(recbdato, recbdato.length);
                    s.receive(rcbpaq);
                    outputStream.write(rcbpaq.getData(), 0, rcbpaq.getLength());
                }

                String receivedMessage = new String(outputStream.toByteArray());
                System.out.println("Cliente: " + receivedMessage);

              
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