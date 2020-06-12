import javax.swing.*;

public class Controller implements Runnable{
    public static JComboBox<RequestMethods> methodsComboBox;
    public static JTextField urlAddress;
    public static JButton status;
    public static JButton time;
    public static JButton capacity;
    public static JPanel formPanel;
    public static JPanel jsonPanel;
    public static JPanel binaryPanel;
    public static JPanel headerPanel;
    public static JPanel queryPanel;

    public static void sendRequest()
    {

        Request request = new Request();
        request.setUrl(urlAddress.getText());
        request.setMethod(String.valueOf(methodsComboBox.getSelectedItem()));
        request.set(formPanel,"data");
        request.set(headerPanel,"headers");
        request.set(queryPanel,"query");
        request.send();
        time.setText(request.getTime());
    }

    @Override
    public void run() {

    }
}
