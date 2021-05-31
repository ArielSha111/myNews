import java.net.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;  
public class MainNewsServer
{
    private static ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();
    private static  int PORT_NUM = 7777;
    public static void main(String[]args)
    {
        ServerSocket srv = null;
        try
        {
            DatagramSocket socket = new DatagramSocket(PORT_NUM);
            JOptionPane.showMessageDialog(null,"server is ready");          
            SendNewsThread news_thread = new SendNewsThread(packets,socket);
            RegisteredThread registers_thread = new RegisteredThread(packets,socket);
            news_thread.start();
            registers_thread.start();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null,"cant open the program since it been closed uncorrectly ");
        }
    }
}
