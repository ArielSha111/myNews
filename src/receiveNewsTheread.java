import javax.swing.*;
import java.net.*;
import java.io.*;
public class receiveNewsTheread extends Thread
{    
    private DatagramSocket socket;
    private JTextArea news_area;
    private int BUFFER_SIZE = 256;

    public receiveNewsTheread(DatagramSocket socket, JTextArea news_area)
    {        
        this.socket =  socket;
        this.news_area = news_area;
    }

    /**
     * A run method override for this thread 
     *
     */
    public void run()
    {
        while(true)
        {                                
            try
            {
                byte[] buf = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                socket.receive(packet);
                String news = new String(packet.getData(),0,packet.getLength());
                if(news_area.getText().equals(""))
                    news_area.setText(news);
                else
                    news_area.setText(news_area.getText() +"\n"+ news);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
