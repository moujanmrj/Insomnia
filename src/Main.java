import javax.swing.*;
/**
 * this class is the main and implements the whole code
 *
 * @author Moujan Mirjalili
 * @version  2020
 */
public class Main {
    public static void main(String[] args)
    {
        //the look and feel of the program
        try
        {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        InsomniaGUI calculatorGUI = new InsomniaGUI();
    }
}