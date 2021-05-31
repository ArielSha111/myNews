import java.net.*;
import java.io.*;  
import java.util.ArrayList;
import javax.swing.*;  
import java.text.SimpleDateFormat; 
import java.util.Date;  
public class SendNewsThread extends Thread
{
    private ArrayList<DatagramPacket> packets;
    private DatagramSocket socket;
    private int BUFFER_SIZE = 256;

    public SendNewsThread(ArrayList<DatagramPacket> packets, DatagramSocket socket)
    {        
        this.packets = packets;
        this.socket = socket;
    }

    /**
     * A run override method that make sure
     * to send all the news to the clients
     */
    public void run()
    {
        try
        {
            String decision;
            boolean leave = false;
            while(leave==false)
            {
                String news = JOptionPane.showInputDialog(null,"Enter News"); 
                if(news != null && !news.equals(""))

                {
                    Date date = new Date();  
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm");  
                    String strDate= formatter.format(date);
                    news = "time: "+strDate+" - news are: - "+news;
                    byte[] buf = new byte[BUFFER_SIZE];
                    buf = news.getBytes();
                    for(int i=0;i<packets.size();i++)
                    {
                        DatagramPacket temp_packet = packets.get(i);
                        InetAddress address = temp_packet.getAddress();
                        int port = temp_packet.getPort();
                        temp_packet = new DatagramPacket(buf,buf.length,address,port);
                        socket.send(temp_packet);
                    }
                }

                else if(news==null)
                {
                    decision = JOptionPane.showInputDialog(null,"type 1 to leave or any thing else to stay typing news"); 
                    if(decision==null||decision.equals("")||(decision.length()==1 && decision.charAt(0)=='1'))
                    {
                        JOptionPane.showMessageDialog(null,"goodbye");
                        leave=true;
                    }
                }
                else if(news.equals(""))
                {
                    JOptionPane.showMessageDialog(null,"an empty news are invalid try again"); 
                }
            }
            System.exit(0);
        }

        catch(IOException e)
        {
            e.printStackTrace();
        } 
    }    
}

