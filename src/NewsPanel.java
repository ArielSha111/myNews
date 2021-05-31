import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
import java.net.*;
import java.io.*;
public class NewsPanel extends JPanel
{
    private JButton cmdLeaveNews; 
    private JButton cmdLeaveProgrem;
    private JButton cmdRegister;
    private JButton cmdUnmute;
    private JButton cmdMute;
    private JButton cmdClearNews;

    DatagramSocket socket;
    public receiveNewsTheread news_thread;
    JTextArea news_area;
    String computer_name;
       /**
     * a constructor for the news panel this constructor fill
     * the panel with all the needed panels and buttons
     *
     * @param  computer_name
     */
    public NewsPanel(String computer_name)
    {        
        this.computer_name = computer_name;
        news_area = new JTextArea("welcome to ariel's news");
        news_area.setBounds(getHeight(),getWidth(),getHeight(),getWidth()); 
        Color VERY_LIGHT_YELLOW = new Color(255,255,204);
        news_area.setBackground(VERY_LIGHT_YELLOW);
        try
        {           
            socket = new DatagramSocket(); 
            news_thread = new receiveNewsTheread(socket, news_area);
            news_thread.start();         
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        Listener lis = new Listener();

        cmdLeaveNews = new JButton("Leave news"); 
        cmdLeaveProgrem = new JButton("Leave program");
        cmdRegister = new JButton("Register");
        cmdUnmute = new JButton("unmute");
        cmdMute = new JButton("mute");
        cmdClearNews = new JButton("clear news");

        Color LIGHT_BLUE = new Color(51,153,255);
        cmdLeaveNews.setBackground(LIGHT_BLUE);
        cmdLeaveProgrem.setBackground(LIGHT_BLUE);
        cmdRegister.setBackground(LIGHT_BLUE);
        cmdUnmute.setBackground(LIGHT_BLUE);
        cmdMute.setBackground(LIGHT_BLUE);
        cmdClearNews.setBackground(LIGHT_BLUE);

        cmdLeaveNews.addActionListener(lis);
        cmdLeaveProgrem.addActionListener(lis);
        cmdRegister.addActionListener(lis);
        cmdUnmute.addActionListener(lis);
        cmdMute.addActionListener(lis);
        cmdClearNews.addActionListener(lis);

        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new GridLayout(2,3));

        buttons_panel.add(cmdLeaveNews);
        buttons_panel.add(cmdLeaveProgrem);
        buttons_panel.add(cmdRegister);
        buttons_panel.add(cmdUnmute);
        buttons_panel.add(cmdMute);
        buttons_panel.add(cmdClearNews);

        JPanel center_panel = new JPanel();
        center_panel.setLayout(new BorderLayout());
        center_panel.add(buttons_panel, BorderLayout.SOUTH);
        center_panel.add(news_area, BorderLayout.CENTER);

        Color MY_GRAY = new Color(204,204,204);
        BackPanel left_background = new BackPanel(MY_GRAY);
        BackPanel right_background = new BackPanel(MY_GRAY);
        BackPanel buttom_background = new BackPanel(MY_GRAY);
        BackPanel top_background = new BackPanel(MY_GRAY);

        this.setLayout(new BorderLayout());
        add(center_panel, BorderLayout.CENTER);
        add(left_background, BorderLayout.WEST);
        add(right_background, BorderLayout.EAST);
        add(buttom_background, BorderLayout.SOUTH);
        add(top_background, BorderLayout.NORTH);
    }

    private class Listener extends MouseAdapter implements ActionListener
    {
        private int BUFFER_SIZE = 256;
        private int PORT_NUM = 7777;
        public void actionPerformed(ActionEvent e)
        {                                                            
            if(e.getSource() == cmdClearNews)
            {
                news_area.setText("");
            }
            else if(e.getSource()==cmdLeaveNews)
            {
                try
                {
                    news_area.setText("your registration been canceled successfully");
                    byte[] buf = new byte[BUFFER_SIZE];
                    String answer = "leave request";
                    buf = answer.getBytes();
                    InetAddress address = InetAddress.getByName(computer_name);
                    DatagramPacket packet = new DatagramPacket(buf,buf.length,address,PORT_NUM);            
                    socket.send(packet);
                }
                catch(IOException ex)
                {
                    //e.printStackTrace();
                }
            }
            else if(e.getSource()==cmdRegister)
            {
                try
                {
                    news_area.setText("you've registered successfully");
                    byte[] buf = new byte[BUFFER_SIZE];
                    String answer = "join request";
                    buf = answer.getBytes();
                    InetAddress address = InetAddress.getByName(computer_name);
                    DatagramPacket packet = new DatagramPacket(buf,buf.length,address,PORT_NUM);            
                    socket.send(packet);
                }
                catch(IOException ex)
                {
                    //ex.printStackTrace();
                }
            }
            else if(e.getSource()==cmdUnmute)
            {  
                news_thread = new receiveNewsTheread(socket, news_area);
                news_thread.start();    
            }
            else if(e.getSource()==cmdMute)
            {
                news_thread.stop();
            }
            else if(e.getSource()==cmdLeaveProgrem)
            {
                System.exit(0);
            }
        }
    }
}
