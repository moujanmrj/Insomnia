import javax.swing.*;

public class Controller {
    public static JComboBox<RequestMethods> methodsComboBox;
    public static JTextField urlAddress;
    public static JButton status;
    public static JButton time;
    public static JButton capacity;

    public static void sendRequest()
    {
        Request request = new Request();
        request.setUrl(urlAddress.getText());
        request.setMethod(String.valueOf(methodsComboBox.getSelectedItem()));
        request.send();
    }
}
