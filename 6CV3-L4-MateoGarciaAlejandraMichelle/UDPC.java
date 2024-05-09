//package 6CV3-L4-MateoGarciaAlejandraMichelle;
import java.io.*;
import java.net.*;
public class UDPC {
    private static final  int max = 1024; 
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
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);

            //Dividdir el paqueete

                int tm= b.length;
                int npaquete = (int) Math.ceil((double) tm / 1024.0);
                // Enviar el número total de paquetes al servidor
                DatagramPacket npaquetea = new DatagramPacket(String.valueOf(npaquete).getBytes(), String.valueOf(npaquete).getBytes().length, InetAddress.getByName("127.0.0.1"), 2000);
                cl.send(npaquetea);

                //Enviar  cada paquete
                for (int i = 0; i < npaquete; i++) {
                    int tx = i * 1024;
                    int endIdx = Math.min((i + 1) * 1024, tm);
                    byte[] packetData = new byte[endIdx - tx];
                    System.arraycopy(b, tx, packetData, 0, endIdx - tx);
                    DatagramPacket sendPacket = new DatagramPacket(packetData, packetData.length, InetAddress.getByName("127.0.0.1"), 2000);
                    cl.send(sendPacket);
                }

            
            //recepcion de la respuesta del servidor 
            byte[] recive= new byte[1024];
            DatagramPacket rcv = new DatagramPacket(recive, recive.length);
            cl.receive (rcv);
            String respuesta = new String(recive, 0, rcv.getLength()); 
            System.out.println("Servidor : " + respuesta);
         }

           // cl.close();
        }catch(Exception e){ 
            e.printStackTrace();
        }//catch
    }//main
    
}
