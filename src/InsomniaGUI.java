import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class InsomniaGUI
{
    private JFrame frame;
    private JPanel request, requester, response;

    public JFrame getFrame() { return frame; }
    public void setFrame(JFrame frame) { this.frame = frame; }

    public JPanel getRequest() { return request; }
    public void setRequest(JPanel request) { this.request = request; }

    public JPanel getRequester() { return requester; }
    public void setRequester(JPanel requester) { this.requester = requester; }

    public JPanel getResponse() { return response; }

    public void setResponse(JPanel response) { this.response = response; }

    public InsomniaGUI()
    {
        frame = new JFrame("Insomnia");
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocation(100,100);
        frame.setSize(1200,500);


        request = new JPanel();
        requester = new JPanel();
        response = new JPanel();

        request.setLayout(new BorderLayout());
        requester.setLayout(new BorderLayout());
        response.setLayout(new BorderLayout());

        request.setBackground(Color.DARK_GRAY);
        request.setOpaque(true);
        requester.setBackground(Color.DARK_GRAY);
        request.setOpaque(true);
        response.setBackground(Color.DARK_GRAY);
        request.setOpaque(true);

        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, request, requester);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, response);
        split1.setResizeWeight(0.4);
        split2.setResizeWeight(0.6);

        frame.add(split2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InsomniaMenuBar();
    }

    public void InsomniaMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu application = new JMenu("Application");
        application.setMnemonic('A');
        menuBar.add(application);

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ////////////////////////////////////////////
            }
        });
        application.add(options);

        JMenuItem exit = new JMenuItem("Exit / Hide on system tray");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();/////////////////////////////////////////////////
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        application.add(exit);

        JMenu view = new JMenu("View");
        application.setMnemonic('V');
        menuBar.add(view);

        JMenuItem toggleFullScreen = new JMenuItem("Toggle Full Screen");
        toggleFullScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //////////////////////////////////////////
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                final int screen_Width = dim.width;
                final int screen_Height = dim.height;

                //Create a JFrame
                JFrame frame = new JFrame();

                frame.setSize(screen_Width, screen_Height);

                //set properties for the JFrame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.setLayout(null);

                frame.setVisible(true);
//                GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                GraphicsDevice device = graphics.getDefaultScreenDevice();
//                frame.setUndecorated(true);
//                frame.setResizable(false);
//                device.setFullScreenWindow(frame);
//                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//                frame.setExtendedState(JFrame.NORMAL);
            }
        });
        view.add(toggleFullScreen);

        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        toggleSidebar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        view.add(toggleSidebar);
    }
}
