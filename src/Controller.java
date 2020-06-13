import javax.swing.*;
/**
 * this class connects gui to logic
 *
 * @author Moujan Mirjalili
 * @version  2020
 */
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
    public static JTextArea messageBodyText;
    public static HeadersTable responseHeaderPanel;
    public static JCheckBox followRedirect;
    public static InsomniaGUI insomniaGUI;
    public static String receivedHeaders;
    public static JLabel previewImage;

    /**
     * this method connects gui to logic
     */
    public static void sendRequest()
    {

        Request request = new Request();
        request.setUrl(urlAddress.getText());
        request.setMethod(String.valueOf(methodsComboBox.getSelectedItem()));
        request.set(formPanel,"data");
        request.set(headerPanel,"headers");
        request.set(queryPanel,"query");
        request.setFollow(insomniaGUI.following);
        request.send();
        time.setText(request.getTime());
        status.setText(request.getStatus());
        messageBodyText.setText(request.getAnswer());
        responseHeaderPanel.reset();
        responseHeaderPanel.setHeaders(request.getReceivedHeaders());
        receivedHeaders = request.getReceivedHeaders();
        if(request.getImg() != null)
        {
            previewImage.setIcon(new ImageIcon(request.getImg()));
        }
        else
        {
            previewImage.setIcon(null);
        }
        insomniaGUI.frame.setVisible(true);
        insomniaGUI.frame.repaint();
        insomniaGUI.frame.revalidate();
    }

    @Override
    public void run() {
        Controller.sendRequest();
    }
}
