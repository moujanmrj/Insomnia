import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;


public class InsomniaGUI
{
    private JFrame frame;
    private JPanel request, requester, response;
    private CardLayout cardLayout;

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
        frame.setLocation(60,70);
        frame.setSize(1400,700);

        request = new JPanel();
        requester = new JPanel();
        response = new JPanel();

        request.setLayout(new BorderLayout());
        requester.setLayout(new BorderLayout());
        response.setLayout(new BorderLayout());

        request.setBackground(new Color(46,47,44));
        request.setOpaque(true);
        requester.setBackground(new Color(46,47,44));
        request.setOpaque(true);
        response.setBackground(new Color(46,47,44));
        request.setOpaque(true);

        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, request, requester);
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, response);
        split1.setResizeWeight(0.4);
        split2.setResizeWeight(0.6);

        frame.add(split2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        insomniaMenuBar();
        insomniaRequester();
        insomniaRequest();
        insomniaResponse();
    }

    public void insomniaMenuBar()
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
                ///////////////////////////////////////////////////////////////////////////
            }
        });
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        application.add(options);

        JMenuItem exit = new JMenuItem("Exit/Hide on system tray");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();////////////////////////////////////////////////////////////
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
                /////////////////////////////////////////////////////////////////////
//                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//                final int screen_Width = dim.width;
//                final int screen_Height = dim.height;
//                frame.setSize(screen_Width, screen_Height);
//                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//                GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                GraphicsDevice device = graphics.getDefaultScreenDevice();
//                frame.setUndecorated(true);
//                frame.setResizable(false);
//                device.setFullScreenWindow(frame);
//                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//                frame.setExtendedState(JFrame.NORMAL);
            }
        });
        toggleFullScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        view.add(toggleFullScreen);

        JMenuItem toggleSidebar = new JMenuItem("Toggle Sidebar");
        toggleSidebar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///////////////////////////////////////////////////////////////////////////////////////////////
            }
        });
        toggleSidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        view.add(toggleSidebar);

        JMenu help = new JMenu("Help");
        application.setMnemonic('H');
        menuBar.add(help);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame();
                aboutFrame.setLayout(new BorderLayout());
                aboutFrame.setVisible(true);
                aboutFrame.setLocation(600,350);
                aboutFrame.setSize(300,80);
                JPanel aboutPanel = new JPanel();
                JLabel aboutNameLabel = new JLabel("Author : Moujan Mirjalili");
                JLabel aboutIdLabel = new JLabel("Id : 9831140");
                JLabel aboutMailLabel = new JLabel("Contact via moujanirjalili@gamil.com");
                aboutNameLabel.setFont(new Font("Verdana", Font.BOLD,10));
                aboutIdLabel.setFont(new Font("Verdana", Font.BOLD,10));
                aboutMailLabel.setFont(new Font("Verdana", Font.BOLD,10));
                aboutPanel.add(aboutNameLabel);
                aboutPanel.add(aboutIdLabel);
                aboutPanel.add(aboutMailLabel);
                aboutPanel.setBackground(Color.gray);
                aboutFrame.add(aboutPanel, BorderLayout.CENTER);

            }
        });
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        help.add(about);

        JMenuItem helper = new JMenuItem("Help");
        helper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame helpFrame = new JFrame();
                helpFrame.setLayout(new BorderLayout());
                helpFrame.setVisible(true);
                helpFrame.setLocation(600,350);
                helpFrame.setSize(300,300);
                JPanel helpPanel = new JPanel();
                helpPanel.setBackground(Color.gray);
                helpFrame.add(helpPanel, BorderLayout.CENTER);
            }
        });
        helper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.ALT_MASK));
        help.add(helper);
    }

    public void insomniaRequester()
    {
        cardLayout = new CardLayout();
        JPanel requesterCenter = new JPanel();
        requesterCenter.setLayout(cardLayout);
        requester.add(requesterCenter,BorderLayout.CENTER);
        requesterCenter.setBackground(new Color(46,47,44));
        requesterCenter.setOpaque(true);

        JPanel formPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(formPanel, BoxLayout.Y_AXIS);
        formPanel.setLayout(boxlayout);
        makeFormPanel(formPanel);

        JPanel jsonPanel = new JPanel();
        makeJsonPanel(jsonPanel);

        JPanel binaryPanel = new JPanel();

        JPanel bearerPanel = new JPanel();
        makeBearerPanel(bearerPanel);

        JPanel queryPanel = new JPanel();

        JPanel headerPanel = new JPanel();
        BoxLayout boxlayout1 = new BoxLayout(headerPanel, BoxLayout.Y_AXIS);
        headerPanel.setLayout(boxlayout1);
        makeFormPanel(headerPanel);

        headerPanel.setBackground(new Color(46,47,44));
        queryPanel.setBackground(new Color(46,47,44));
        bearerPanel.setBackground(new Color(46,47,44));
        binaryPanel.setBackground(new Color(46,47,44));
        jsonPanel.setBackground(new Color(46,47,44));
        formPanel.setBackground(new Color(46,47,44));

        requesterCenter.add(formPanel,"1");
        requesterCenter.add(jsonPanel, "2");
        requesterCenter.add(binaryPanel, "3");
        requesterCenter.add(bearerPanel,"4");
        requesterCenter.add(queryPanel,"5");
        requesterCenter.add(headerPanel, "6");

        JPanel requesterTop = new JPanel();
        requesterTop.setLayout(new GridLayout(2,1));
        requester.add(requesterTop,BorderLayout.NORTH);

        JPanel getTextSend = new JPanel();
        getTextSend.setLayout(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboGet = new JComboBox<>();
        comboGet.addItem("GET");
        comboGet.addItem("POST");
        comboGet.addItem("PUT");
        comboGet.addItem("PATCH");
        comboGet.addItem("DELETE");
        comboGet.setPreferredSize(new Dimension(60,40));
        getTextSend.add(comboGet);

        JTextArea urlAddress = new JTextArea();
        urlAddress.setPreferredSize(new Dimension(270,40));
        getTextSend.add(urlAddress);

        JButton send = new JButton("Send");
        send.setPreferredSize(new Dimension(60,40));
        getTextSend.add(send);

        JButton save = new JButton("Save");
        save.setPreferredSize(new Dimension(60,40));
        getTextSend.add(save);

        requesterTop.add(getTextSend);

        JPanel tabs = new JPanel();
        tabs.setLayout(new FlowLayout(FlowLayout.LEFT));
        tabs.setBackground(new Color(46,47,44));
        tabs.setOpaque(true);
        tabs.setBorder(new LineBorder(Color.gray));
        tabs.setOpaque(true);

        JComboBox<String> comboBody = new JComboBox<>();
        comboBody.setBackground(new Color(46,47,44));
        comboBody.setOpaque(true);
        comboBody.addItem("Form URL Encoded");
        comboBody.addItem("JSON");
        comboBody.addItem("Binary File");
        comboBody.setPreferredSize(new Dimension(100,40));
        tabs.add(comboBody);

        JButton authBearer = new JButton("Auth Bearer Token");
        authBearer.setBackground(new Color(46,47,44));
        authBearer.setOpaque(true);
        authBearer.setForeground(Color.white);
        authBearer.setOpaque(true);
        authBearer.setPreferredSize(new Dimension(100,40));
        tabs.add(authBearer);

        JButton query = new JButton("Query");
        query.setBackground(new Color(46,47,44));
        query.setOpaque(true);
        query.setForeground(Color.white);
        query.setOpaque(true);
        query.setPreferredSize(new Dimension(70,40));
        tabs.add(query);

        JButton header = new JButton("Header");
        header.setBackground(new Color(46,47,44));
        header.setOpaque(true);
        header.setForeground(Color.white);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(70,40));
        tabs.add(header);

        requesterTop.add(tabs);

        header.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(requesterCenter,"6");
            }
        });

        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(requesterCenter,"5");
            }
        });

        authBearer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(requesterCenter,"4");
            }
        });

        comboBody.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(comboBody.getSelectedItem().equals("Binary File"))
                    cardLayout.show(requesterCenter,"3");
                else if(comboBody.getSelectedItem().equals("JSON"))
                    cardLayout.show(requesterCenter,"2");
                else if(comboBody.getSelectedItem().equals("Form URL Encoded"))
                    cardLayout.show(requesterCenter,"1");
            }
        });
    }

    public void makeFormPanel(JPanel panel)
    {
        JPanel make = new JPanel();
        make.setLayout(new FlowLayout(FlowLayout.LEFT));
        make.setBackground(new Color(46,47,44));
        make.setOpaque(true);
        panel.add(make, BorderLayout.CENTER);

        JTextArea name = new JTextArea("name");
        name.setPreferredSize(new Dimension(200,30));
        name.setBackground(new Color(46,47,44));
        name.setOpaque(true);
        name.setForeground(Color.GRAY);
        name.setOpaque(true);

        JTextArea value = new JTextArea("value");
        value.setPreferredSize(new Dimension(200,30));
        value.setBackground(new Color(46,47,44));
        value.setOpaque(true);
        value.setForeground(Color.GRAY);
        value.setOpaque(true);

        make.add(name);
        make.add(value);

        JRadioButton radioButton = new JRadioButton();
        make.add(radioButton);
        radioButton.setSelected(true);
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton.isSelected())
                {
                    name.setEnabled(true);
                    value.setEnabled(true);

                }
                else
                {
                    name.setEnabled(false);
                    value.setEnabled(false);
                }
            }
        });

        JButton waste = new JButton("\uD83D\uDDD1");
        waste.setPreferredSize(new Dimension(50,30));
        waste.setFont(new Font("SansSerif",Font.PLAIN,22));
        waste.setBackground(new Color(46,47,44));
        waste.setOpaque(true);
        waste.setForeground(Color.white);
        waste.setOpaque(true);
        make.add(waste);

        waste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(make);
                panel.revalidate();
                panel.repaint();
            }
        });

//        value.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent me) {
//                makeFormPanel(panel);
//            }
//        });
    }


    public void makeJsonPanel(JPanel panel)
    {
        panel.setLayout(new BorderLayout());

        JLabel dot = new JLabel("    \uD83D\uDD34");
        dot.setFont(new Font("SansSerif",Font.PLAIN,10));
        dot.setBackground(new Color(46,47,44));
        dot.setOpaque(true);
        dot.setForeground(Color.red);
        dot.setOpaque(true);
        panel.add(dot, BorderLayout.NORTH);

        JTextArea jsonText = new JTextArea();
        jsonText.setBackground(new Color(46,47,44));
        jsonText.setOpaque(true);
        jsonText.setForeground(Color.gray);
        jsonText.setOpaque(true);
        jsonText.setPreferredSize(new Dimension(400,20));
        panel.add(jsonText,BorderLayout.CENTER);
    }

    public void makeBearerPanel(JPanel panel)
    {
        JPanel maker = new JPanel();
        maker.setLayout(new FlowLayout(FlowLayout.LEFT));
        maker.setBackground(new Color(46,47,44));
        maker.setOpaque(true);
        panel.add(maker, BorderLayout.CENTER);
    }

    public void insomniaRequest()
    {
        JPanel requestTop = new JPanel();
        requestTop.setLayout(new BorderLayout());
        request.add(requestTop,BorderLayout.NORTH);

        JLabel purpleInsomnia = new JLabel();
        purpleInsomnia.setText("  Insomnia");
        purpleInsomnia.setMinimumSize(new Dimension(100,40));
        purpleInsomnia.setFont(new Font("SansSerif", Font.BOLD, 30));
        purpleInsomnia.setForeground(Color.white);
        purpleInsomnia.setOpaque(true);
        purpleInsomnia.setSize(new Dimension(100,30));
        purpleInsomnia.setBackground(new Color(102,96,178));
        purpleInsomnia.setOpaque(true);
        requestTop.add(purpleInsomnia,BorderLayout.NORTH);

        JPanel requestInsomnia = new JPanel();
        requestInsomnia.setLayout(new BorderLayout());
        requestTop.add(requestInsomnia, BorderLayout.CENTER);

        JPanel gridReq = new JPanel();
        gridReq.setLayout(new GridLayout(1,2));
        requestInsomnia.add(gridReq, BorderLayout.NORTH);
        requestInsomnia.setBorder(new LineBorder(Color.gray));
        requestInsomnia.setOpaque(true);

        JButton noEnvironment = new JButton("No Environment");
        noEnvironment.setBackground(new Color(46,47,44));
        noEnvironment.setOpaque(true);
        noEnvironment.setForeground(Color.white);
        noEnvironment.setOpaque(true);
        gridReq.add(noEnvironment);

        JButton cookies = new JButton("Cookies");
        cookies.setBackground(new Color(46,47,44));
        cookies.setOpaque(true);
        cookies.setForeground(Color.white);
        cookies.setOpaque(true);
        gridReq.add(cookies);

        JPanel newReq = new JPanel();
        newReq.setLayout(new FlowLayout(FlowLayout.LEFT));
        requestInsomnia.add(newReq,BorderLayout.CENTER);
        newReq.setBackground(new Color(46,47,44));
        newReq.setOpaque(true);

        JTextArea filter = new JTextArea();
        filter.setBackground(new Color(46,47,44));
        filter.setOpaque(true);
        filter.setForeground(Color.white);
        filter.setOpaque(true);
        filter.setPreferredSize(new Dimension(200,30));
        newReq.add(filter);

        JComboBox<String> newRequest = new JComboBox<>();
        newRequest.setBackground(new Color(46,47,44));
        newRequest.setOpaque(true);
        newRequest.addItem("New Request");
        newRequest.addItem("New Folder");
        newRequest.setPreferredSize(new Dimension(80,30));
        newReq.add(newRequest);


        newRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newRequest.getSelectedItem().equals("New Request"))
                {
                    JFrame newReqFrame = new JFrame("New Request");
                    newReqFrame.setLayout(new BorderLayout());
                    newReqFrame.setVisible(true);
                    newReqFrame.setLocation(450,70);
                    newReqFrame.setSize(750,160);

                    JPanel newReqPanel = new JPanel();
                    newReqPanel.setLayout(new FlowLayout());
                    newReqFrame.add(newReqPanel, BorderLayout.CENTER);

                    JTextArea newReqText = new JTextArea();
                    newReqText.setPreferredSize(new Dimension(600,40));
                    newReqPanel.add(newReqText);

                    JComboBox<String> comboGet = new JComboBox<>();
                    comboGet.addItem("GET");
                    comboGet.addItem("POST");
                    comboGet.addItem("PUT");
                    comboGet.addItem("PATCH");
                    comboGet.addItem("DELETE");
                    comboGet.setPreferredSize(new Dimension(100,40));
                    newReqPanel.add(comboGet);

                    JPanel tipCreate = new JPanel();
                    tipCreate.setLayout(new FlowLayout());
                    newReqFrame.add(tipCreate ,BorderLayout.SOUTH);

                    JLabel tip= new JLabel("*Tip: paste Curl command into URL afterwards to import it");
                    tipCreate.add(tip);

                    JButton create = new JButton("Create");
                    create.setPreferredSize(new Dimension(100,40));
                    tipCreate.add(create);

                    JLabel name = new JLabel("      Name");
                    newReqFrame.add(name, BorderLayout.NORTH);
                }
                else
                {
                    JFrame newFolderFrame = new JFrame("New Folder");
                    newFolderFrame.setLayout(new BorderLayout());
                    newFolderFrame.setVisible(true);
                    newFolderFrame.setLocation(450,70);
                    newFolderFrame.setSize(750,120);

                    JPanel newFolderPanel = new JPanel();
                    newFolderPanel.setLayout(new FlowLayout());
                    newFolderFrame.add(newFolderPanel, BorderLayout.CENTER);

                    JTextArea newFolderText = new JTextArea();
                    newFolderText.setPreferredSize(new Dimension(600,40));
                    newFolderPanel.add(newFolderText);

                    JButton createFol = new JButton("Create");
                    createFol.setPreferredSize(new Dimension(100,40));
                    newFolderPanel.add(createFol);

                    JLabel name = new JLabel("      Name");
                    newFolderFrame.add(name, BorderLayout.NORTH);
                }
            }
        });
    }

    public void insomniaResponse()
    {

    }
}
