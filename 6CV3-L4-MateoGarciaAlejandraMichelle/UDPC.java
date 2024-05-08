//package 6CV3-L4-MateoGarciaAlejandraMichelle;
import java.io.*;
import java.net.*;
public class UDPC {
    public static void main(String[] args){
        try{
            //configuracion del socket
            DatagramSocket cl = new DatagramSocket();
            System.out.print("Cliente iniciado, escriba un mensaje de saludo:");

            for(;;){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = br.readLine();
            byte[] b = mensaje.getBytes();
            String dst = "127.0.0.1";
            int pto = 2000;

            //Envio del primer paquete
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
            cl.send(p);

            //recepcion de la respuesta del servidor 
            byte[] recive= new byte[2000];
            DatagramPacket rcv = new DatagramPacket(recive, recive.length);
            cl.receive (rcv);
            String respuesta = new String(recive, 0, rcv.getLength()); 
            System.out.println("Respuesta del servidor: " + respuesta);}

           // cl.close();
        }catch(Exception e){ 
            e.printStackTrace();
        }//catch
    }//main
    
}
