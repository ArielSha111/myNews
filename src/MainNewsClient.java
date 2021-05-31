import javax.swing.*;
public class MainNewsClient extends JPanel

{
    private static final int SIZE_OF_FRAME = 450;
    private static final int HEIGHT =700;
    private static final int WIDTH=200;
    public static void main(String [] args)
    {
        JFrame frame = new JFrame("Ariels news");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE_OF_FRAME,SIZE_OF_FRAME); 
        frame.setLocation(HEIGHT,WIDTH);
        String computer_name;
        while(true)
        {
            computer_name = JOptionPane.showInputDialog(null,"Enter your computer in order to start please Name\nempty String will count as \"localhost\""); 
            if(computer_name!=null)
            {
                if(computer_name.equals(""))
                    computer_name = "localhost";
                break;
            }
            JOptionPane.showMessageDialog(null,"error, try again");  
        }
        NewsPanel NP = new NewsPanel(computer_name);                 
        frame.setVisible(true); 
        frame.add(NP);
    }
}
