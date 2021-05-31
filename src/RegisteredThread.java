import java.net.*;
import java.io.*;  
import java.util.ArrayList;

public class RegisteredThread extends Thread
{
    private ArrayList<DatagramPacket> packets;
    private DatagramSocket socket;
    private int BUFFER_SIZE = 256;
    public RegisteredThread(ArrayList<DatagramPacket> packets, DatagramSocket socket)
    {        
        this.packets = packets;
        this.socket = socket;
    }

    /**
     * A run override method that make sure to
     * get the information about new registrations
     */
    public void run()
    {
        try
        {
            while(true)
            {
                byte[] buf = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                socket.receive(packet);
                String msg_from_user = new String(packet.getData(),0,packet.getLength());
                if(isJoingMsg(msg_from_user))
                {                    
                    if(!isRegestered(packet))
                        addUser(packet);
                }
                else if(isLeavingMsg(msg_from_user))
                {
                    if(isRegestered(packet))
                        removeUser(packet);
                }
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        } 
    }    

    /**
     * A method to check if a new registration is valid
     * and the client in not signd in already
     * 
     *@ param  DatagramPacket packet
     */
    private  boolean isRegestered(DatagramPacket packet)
    {
        for(int i =0;i<packets.size();i++)
        {            
            DatagramPacket temp_packet = packets.get(i);
            InetAddress address = temp_packet.getAddress();
            int port = temp_packet.getPort();
            if(packet.getAddress().equals(address) && packet.getPort() == port)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to checks if a msg from a client is a join message
     * 
     *@ param  String s
     */
    private boolean isJoingMsg(String s)
    {                        
        if(s.equals("join request"))
            return true;
        return false;
    }

    /**
     * A method to checks if a msg from a client is a leaving message
     * 
     *@ param  String s
     */
    private boolean isLeavingMsg(String s)
    {
        if(s.equals("leave request"))
            return true;
        return false;
    }

    /**
     * A method to add new client to the clients list
     * 
     *@ param  DatagramPacket packet
     */
    private void addUser(DatagramPacket packet)
    {
        packets.add(packet);
    }

    /**
     * A method to remove a client from the clients list
     * 
     *@ param  DatagramPacket packet
     */
    private void removeUser(DatagramPacket packet)
    {
        for(int i =0;i<packets.size();i++)
        {            
            DatagramPacket temp_packet = packets.get(i);
            InetAddress address = temp_packet.getAddress();
            int port = temp_packet.getPort();
            if(packet.getAddress().equals(address) && packet.getPort() == port)
            {
                packets.remove(i); 
                break;
            }
        }
    }
}
