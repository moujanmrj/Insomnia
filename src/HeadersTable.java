import javax.swing.*;
import java.awt.*;

public class HeadersTable extends JPanel{
    JPanel headerPanel;
    public HeadersTable()
    {
        super();
        setLayout(new GridLayout(1,1));
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);
        headerPanel = panel;
        Controller.responseHeaderPanel = this;
    }
    public void addHeader(String key , String value)
    {
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(100,50));
        jPanel.setMaximumSize(new Dimension(10000,50));
        jPanel.setLayout(new GridLayout(1,2));
        JTextField keyText = new JTextField(key);
        keyText.setEditable(false);
        JTextField valueText = new JTextField(value);
        valueText.setEditable(false);
        jPanel.add(keyText);
        jPanel.add(valueText);
        headerPanel.add(jPanel);
    }

    public void reset(){
        for(Component component : headerPanel.getComponents())
        {
            headerPanel.remove(component);
        }
    }

    public void setHeaders(String receivedHeaders) {
        for(String header : receivedHeaders.split(";;"))
        {
            String key = header.split("::")[0];
            String value = header.split("::")[1];
            addHeader(key,value);
        }
    }
}
