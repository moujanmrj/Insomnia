import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        InsomniaGUI calculatorGUI = new InsomniaGUI();
    }
}
