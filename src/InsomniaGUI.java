import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * this class has all the gui and some actions but it's gonna change into multiple classes in the future
 *
 * @author Moujan Mirjalili
 * @version  2020
 */

public class InsomniaGUI
{
    //the main frame
    private JFrame frame;
    //the three main panels
    private JPanel request, requester, response;
    private CardLayout cardLayout, cardLayout1;
    //these show if the app options are on or off
    private boolean hiding = false, following = false;


    private ArrayList<JPanel> headerList = new ArrayList<>();
    private ArrayList<JPanel> queryList = new ArrayList<>();
    private ArrayList<JPanel> formList = new ArrayList<>();

    private ArrayList<Request> requestsOfInsomnia = new ArrayList<>();


    /**
     * gets list of request
     * @return list of request
     */
    public ArrayList<Request> getRequestsOfInsomnia() { return requestsOfInsomnia; }
    /**
     * gets list of header
     * @return list of header
     */
    public ArrayList<JPanel> getHeaderList() { return headerList; }
    /**
     * gets list of query
     * @return list of query
     */
    public ArrayList<JPanel> getQueryList() { return queryList; }
    /**
     * gets list of form
     * @return list of form
     */
    public ArrayList<JPanel> getFormList() { return formList; }

    /**
     * sets if the system is to hide in the tray
     * @param hiding tray system boolean
     */
    public void setHiding(boolean hiding) { this.hiding = hiding; }
    /**
     * returns the system tray option
     * @return  tray system boolean
     */
    public boolean isHiding() { return hiding; }

    /**
     * sets follow redirect option
     * @param following follow redirect option boolean
     */
    public void setFollowing(boolean following) { this.following = following; }
    /**
     * returns follow redirect option
     * @return  tray system boolean
     */
    public boolean isFollowing() { return following; }

    /**
     * this is the constructor of the gui and makes the main frames
     * and panels and sets them
     */
    public InsomniaGUI()
    {
        frame = new JFrame("Insomnia");
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocation(60,70);
        frame.setSize(1420,720);
        ImageIcon appIcon = new ImageIcon("insomniaIcon.png");
        frame.setIconImage(appIcon.getImage());

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

    /**
     * this is for making the menu of the program
     */
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
                JFrame optionFrame = new JFrame("Options");
                optionFrame.setLayout(new BorderLayout());
                optionFrame.setVisible(true);
                optionFrame.setLocation(600,350);
                optionFrame.setSize(300,75);
                JPanel optionPanel = new JPanel();
                optionPanel.setBackground(Color.gray);
                optionFrame.add(optionPanel, BorderLayout.CENTER);
                JCheckBox followRedirect = new JCheckBox("Follow Redirect");
                JCheckBox systemTray = new JCheckBox("Hide in System Tray");
                if (isFollowing())
                    followRedirect.setSelected(true);
                else
                    followRedirect.setSelected(false);

                if (isHiding())
                    systemTray.setSelected(true);
                else
                    systemTray.setSelected(false);
                optionPanel.add(followRedirect);
                optionPanel.add(systemTray);

                followRedirect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (followRedirect.isSelected())
                        {
                            followRedirect.setSelected(true);
                            setFollowing(true);
                        }
                        else
                        {
                            followRedirect.setSelected(false);
                            setFollowing(false);
                        }
                    }
                });
                systemTray.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (systemTray.isSelected())
                        {
                            systemTray.setSelected(true);
                            setHiding(true);
                        }
                        else
                        {
                            systemTray.setSelected(false);
                            setHiding(false);
                        }
                    }
                });
            }
        });
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        application.add(options);

        JMenuItem exit = new JMenuItem("Exit/Hide on system tray");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isHiding())
                  frame.dispose();
                else
                {
                    hideInTray();
                }
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
                if (e.getSource().equals(toggleFullScreen))
                {
                    frame.dispose();
                    if (!frame.isUndecorated())
                    {
                        frame.setUndecorated(true);
                        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }
                    else
                    {
                        frame.setUndecorated(false);
                        frame.setExtendedState(JFrame.NORMAL);
                    }
                    frame.setVisible(true);
                }
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
                JFrame aboutFrame = new JFrame("About");
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
                JFrame helpFrame = new JFrame("Help");
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

    /**
     * this handles the hide in tray option
     */
    public void hideInTray()
    {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("insomniaIcon.png");
        final TrayIcon trayIcon = new TrayIcon(image, "Insomnia", popup);
        trayIcon.setImageAutoSize(true);

        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    /**
     * this makes the middle requester part of the program
     */
    public void insomniaRequester()
    {
        cardLayout = new CardLayout();
        JPanel requesterCenter = new JPanel();
        requesterCenter.setLayout(cardLayout);
        requester.add(requesterCenter,BorderLayout.CENTER);
        requesterCenter.setBackground(new Color(46,47,44));
        requesterCenter.setOpaque(true);

        JPanel formPanel = new JPanel();
        makeFormPanel(formPanel,"FORM");

        JPanel jsonPanel = new JPanel();
        makeJsonPanel(jsonPanel);

        JPanel binaryPanel = new JPanel();
        ////////////////////////////////////////////////////////////////

        JPanel bearerPanel = new JPanel();
        makeBearerPanel(bearerPanel);

        JPanel queryPanel = new JPanel();
        makeFormPanel(queryPanel, "QUERY");

        JPanel headerPanel = new JPanel();
        makeFormPanel(headerPanel, "HEADER");

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

    /**
     * this makes the form/header/query panel in the (middle) requester area
     * @param panel form/header/query panel
     * @param nameOfPanel name of the panel (form/header/query)
     */
    public void makeFormPanel(JPanel panel, String nameOfPanel)
    {
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        JPanel make = new JPanel();
        make.setLayout(new FlowLayout(FlowLayout.LEFT));
        make.setBackground(new Color(46,47,44));
        make.setOpaque(true);
        panel.add(make, BorderLayout.CENTER);
        make.setMaximumSize(new Dimension(500,40));

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

        JButton waste = new JButton("\uD83D\uDDD1");
        waste.setPreferredSize(new Dimension(50,30));
        waste.setFont(new Font("SansSerif",Font.PLAIN,22));
        waste.setBackground(new Color(46,47,44));
        waste.setOpaque(true);
        waste.setForeground(Color.white);
        waste.setOpaque(true);
        make.add(waste);

        JRadioButton radioButton = new JRadioButton();
        make.add(radioButton);
        radioButton.setSelected(true);

        switch (nameOfPanel)
        {
            case "FORM":
                formList.add(make);
                break;
            case "HEADER":
                headerList.add(make);
                break;
            case "QUERY":
                queryList.add(make);
                break;
        }

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



        waste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (nameOfPanel)
                {
                    case "FORM":
                        if (getFormList().size() > 1)
                        {
                            delete(waste,panel,make);
                            getFormList().remove(make);
                        }
                        break;
                    case "HEADER":
                        if (getHeaderList().size() > 1)
                        {
                            delete(waste,panel,make);
                            getHeaderList().remove(make);
                        }
                        break;
                    case "QUERY":
                        if (getQueryList().size() > 1)
                        {
                            delete(waste,panel,make);
                            getQueryList().remove(make);
                        }
                        break;
                }
            }
        });


        value.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                adding(panel,make,nameOfPanel);
                switch (nameOfPanel)
                {
                    case "FORM":
                        if (getFormList().size()-1 == getFormList().indexOf(make))
                        {
                            getFormList().add(make);
                        }
                        break;
                    case "HEADER":
                        if (getHeaderList().size()-1 == getHeaderList().indexOf(make))
                        {
                            getHeaderList().add(make);
                        }
                        break;
                    case "QUERY":
                        if (getQueryList().size()-1 == getQueryList().indexOf(make))
                        {
                            getQueryList().add(make);
                        }
                        break;
                }
            }
        });

        name.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                adding(panel,make,nameOfPanel);
                switch (nameOfPanel)
                {
                    case "FORM":
                        if (getFormList().size()-1 == getFormList().indexOf(make))
                        {
                            getFormList().add(make);
                        }
                        break;
                    case "HEADER":
                        if (getHeaderList().size()-1 == getHeaderList().indexOf(make))
                        {
                            getHeaderList().add(make);
                        }
                        break;
                    case "QUERY":
                        if (getQueryList().size()-1 == getQueryList().indexOf(make))
                        {
                            getQueryList().add(make);
                        }
                        break;
                }
            }
        });
    }

    /**
     * this handles the deleting options in the form/header/query panel
     * @param waste the delete button
     * @param panel form/header/query panel
     * @param make the part(panel) which is going to be removed
     */
    public void delete(JButton waste, JPanel panel, JPanel make)
    {
        panel.remove(make);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * this handles the adding options in the form/header/query panel
     * @param panel form/header/query panel
     * @param make the part(panel) which is going to be added
     * @param nameOfPanel name of the panel (form/header/query)
     */
    public void adding( JPanel panel, JPanel make, String nameOfPanel)
    {
        makeFormPanel(panel, nameOfPanel);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * this makes the JSON panel in the (middle) requester part
     * @param panel the JSON panel
     */
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

    /**
     * this makes the bearer panel in the (middle) requester part
     * @param panel the bearer panel
     */
    public void makeBearerPanel(JPanel panel)
    {
        panel.setLayout(new GridLayout(15,1));

        JPanel maker = new JPanel();
        maker.setLayout(new FlowLayout(FlowLayout.LEFT));
        maker.setBackground(new Color(46,47,44));
        maker.setOpaque(true);

        JPanel make = new JPanel();
        make.setLayout(new FlowLayout(FlowLayout.LEFT));
        make.setBackground(new Color(46,47,44));
        make.setOpaque(true);

        JLabel token = new JLabel("  TOKEN  ");
        token.setFont(new Font("Arial",Font.PLAIN,13));
        token.setBackground(new Color(46,47,44));
        token.setOpaque(true);
        token.setForeground(Color.gray);
        token.setOpaque(true);
        maker.add(token);

        JTextArea tokenText = new JTextArea();
        tokenText.setBackground(new Color(46,47,44));
        tokenText.setOpaque(true);
        tokenText.setForeground(Color.gray);
        tokenText.setOpaque(true);
        tokenText.setPreferredSize(new Dimension(410,30));
        maker.add(tokenText);

        JLabel prefix = new JLabel("  PREFIX? ");
        prefix.setFont(new Font("Arial",Font.PLAIN,13));
        prefix.setBackground(new Color(46,47,44));
        prefix.setOpaque(true);
        prefix.setForeground(Color.gray);
        prefix.setOpaque(true);
        prefix.setToolTipText("Prefix to use when sending the Authorization header. Defaults to Bearer.");
        make.add(prefix);

        JTextArea prefixText = new JTextArea();
        prefixText.setBackground(new Color(46,47,44));
        prefixText.setOpaque(true);
        prefixText.setForeground(Color.gray);
        prefixText.setOpaque(true);
        prefixText.setPreferredSize(new Dimension(405,30));
        make.add(prefixText);

        JPanel making = new JPanel();
        making.setLayout(new FlowLayout(FlowLayout.LEFT));
        making.setBackground(new Color(46,47,44));
        making.setOpaque(true);

        JLabel enabled = new JLabel("  ENABLED  ");
        enabled.setFont(new Font("Arial",Font.PLAIN,13));
        enabled.setBackground(new Color(46,47,44));
        enabled.setOpaque(true);
        enabled.setForeground(Color.gray);
        enabled.setOpaque(true);
        making.add(enabled);

        JRadioButton radioButton = new JRadioButton();
        making.add(radioButton);
        radioButton.setSelected(true);
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton.isSelected())
                {
                    prefixText.setEnabled(true);
                    tokenText.setEnabled(true);

                }
                else
                {
                    prefixText.setEnabled(false);
                    tokenText.setEnabled(false);
                }
            }
        });

        panel.add(maker);
        panel.add(make);
        panel.add(making);
    }

    /**
     * this makes the insomnia request part
     */
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

                    create.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            createRequests(comboGet,newReqText);
                            newReqFrame.dispose();
                        }
                    });
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

    /**
     * this creates the requests
     * @param comboBox
     * @param textName
     */
    public void createRequests(JComboBox<String> comboBox, JTextArea textName)
    {
        JPanel requestsCenter = new JPanel();
        BoxLayout boxlayout = new BoxLayout(requestsCenter, BoxLayout.Y_AXIS);
        requestsCenter.setLayout(boxlayout);
        request.add(requestsCenter, BorderLayout.CENTER);

        String type = String.valueOf(comboBox.getSelectedItem());
        String name = textName.getText();
        Request theNewRequest = new Request(type, name);
        getRequestsOfInsomnia().add(theNewRequest);

        requestsCenter.setBackground(new Color(46,47,44));
        requestsCenter.setOpaque(true);
        requestsCenter.setForeground(Color.white);
        requestsCenter.setOpaque(true);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT));

        info.setBackground(new Color(46,47,44));
        info.setOpaque(true);
        info.setForeground(Color.white);
        info.setOpaque(true);


        JButton theName = new JButton(name);
        JButton theType = new JButton(type);

        theName.setBackground(new Color(46,47,44));
        theName.setOpaque(true);
        theName.setForeground(Color.white);
        theName.setOpaque(true);

        theType.setBackground(new Color(46,47,44));
        theType.setOpaque(true);

        switch (type)
        {
            case "GET":
                theType.setForeground(new Color(102,96,178));
                theType.setOpaque(true);
                break;
            case "POST":
                theType.setForeground(new Color(89,162,16));
                theType.setOpaque(true);
                break;
            case "PUT":
                theType.setForeground(new Color(208,117,2));
                theType.setOpaque(true);
                break;
            case "PATCH":
                theType.setForeground(new Color(185,164,37));
                theType.setOpaque(true);
                break;
            case "DELETE":
                theType.setForeground(new Color(208,68,68));
                theType.setOpaque(true);
                break;
        }

        theName.setPreferredSize(new Dimension(150,30));
        theType.setPreferredSize(new Dimension(100,30));

        info.add(theType);
        info.add(theName);

        requestsCenter.add(info);

        requestsCenter.revalidate();
        requestsCenter.repaint();
    }

    /**
     * this makes the insomnia response part
     */
    public void insomniaResponse()
    {
        cardLayout1 = new CardLayout();
        JPanel responseCenter = new JPanel();
        responseCenter.setLayout(cardLayout1);
        response.add(responseCenter,BorderLayout.CENTER);
        responseCenter.setBackground(new Color(46,47,44));
        responseCenter.setOpaque(true);

        JPanel messageBodyPanel = new JPanel();
        makeMessageBodyPanel(messageBodyPanel);

        JPanel headerPanel = new JPanel();
        makeHeaderPanel(headerPanel);

        messageBodyPanel.setBackground(new Color(46,47,44));
        headerPanel.setBackground(new Color(46,47,44));

        responseCenter.add(messageBodyPanel,"1");
        responseCenter.add(headerPanel, "2");

        JPanel responseTop = new JPanel();
        responseTop.setLayout(new GridLayout(2,1));
        response.add(responseTop,BorderLayout.NORTH);

        JPanel statuses = new JPanel();
        statuses.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton status = new JButton("OK");
        status.setPreferredSize(new Dimension(60,40));
        statuses.add(status);

        JButton time = new JButton("S");
        time.setPreferredSize(new Dimension(60,40));
        statuses.add(time);

        JButton capacity = new JButton("KB");
        capacity.setPreferredSize(new Dimension(60,40));
        statuses.add(capacity);

        responseTop.add(statuses);

        JPanel tabsRes = new JPanel();
        tabsRes.setLayout(new FlowLayout(FlowLayout.LEFT));
        tabsRes.setBackground(new Color(46,47,44));
        tabsRes.setOpaque(true);
        tabsRes.setBorder(new LineBorder(Color.gray));
        tabsRes.setOpaque(true);


        JButton messageBody = new JButton("Message Body");
        messageBody.setBackground(new Color(46,47,44));
        messageBody.setOpaque(true);
        messageBody.setForeground(Color.white);
        messageBody.setOpaque(true);
        messageBody.setPreferredSize(new Dimension(150,40));
        tabsRes.add(messageBody);

        JButton header = new JButton("Header");
        header.setBackground(new Color(46,47,44));
        header.setOpaque(true);
        header.setForeground(Color.white);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(100,40));
        tabsRes.add(header);

        responseTop.add(tabsRes);

        messageBody.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout1.show(responseCenter,"1");
            }
        });

        header.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout1.show(responseCenter,"2");
            }
        });
    }

    /**
     * this makes the header panel in the (right) response part
     * @param panel the header panel
     */
    public void makeHeaderPanel(JPanel panel)
    {
        panel.setLayout(new BorderLayout());
        JPanel headerTop = new JPanel();
        headerTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerTop.setBackground(new Color(46,47,44));
        headerTop.setOpaque(true);
        panel.add(headerTop, BorderLayout.NORTH);

        JLabel nameVal = new JLabel("  NAME                                                                        VALUE                                     ");
        nameVal.setFont(new Font("Arial",Font.PLAIN,13));
        nameVal.setBackground(new Color(46,47,44));
        nameVal.setOpaque(true);
        nameVal.setForeground(Color.gray);
        nameVal.setOpaque(true);
        headerTop.add(nameVal);

        JButton copy = new JButton("Copy to Clipboard");
        copy.setBackground(new Color(46,47,44));
        copy.setOpaque(true);
        copy.setForeground(Color.white);
        copy.setOpaque(true);
        headerTop.add(copy);

        JTable headerTable = new JTable();
        headerTable.setBackground(new Color(46,47,44));
        headerTable.setOpaque(true);
        headerTable.setForeground(Color.gray);
        headerTable.setOpaque(true);
        panel.add(headerTable,BorderLayout.CENTER);

        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //////////////////////////////////////////////////////////////////
            }
        });
    }

    /**
     * this makes the message Body in the (right) response part
     * @param panel the message Body panel
     */
    public void makeMessageBodyPanel(JPanel panel)
    {
        panel.setLayout(new BorderLayout());
        JPanel body = new JPanel();
        body.setLayout(new FlowLayout());
        body.setBackground(new Color(46,47,44));
        body.setOpaque(true);
        body.setForeground(Color.gray);
        body.setOpaque(true);
        panel.add(body,BorderLayout.NORTH);
    }
}
