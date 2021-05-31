import javax.swing.*;
import java.awt.*; 
public class BackPanel extends JPanel
{
    private Color my_color ;//declare global color to be create or get value later

    public BackPanel(Color color)
    {
        my_color = color;//coppy other_color to my_color
    }


    protected void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.setColor(my_color);
        g.fillRect(0,0,getWidth(),getHeight());//create a rect to fill all the frame if needed
    }
}
