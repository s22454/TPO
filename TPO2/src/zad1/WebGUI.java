package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebGUI extends JFrame implements ActionListener {

    //panels
    private JPanel      mainPanel;
    private JPanel      webPanel;
    private JPanel      infoPanel;
    private JPanel      buttonsPanel;
    private JPanel      countryInputPanel;
    private JPanel      cityInputPanel;
    private JPanel      currencyInputPanel;
    private JPanel      emptyPanel;
    private JFXPanel    jfxPanel;

    //country info are
    private JTextArea   infoTextArea;

    //web view
    WebView             webView;

    //user country input
    private JLabel      countryTextArea;
    private JButton     countryConfirmButton;
    private JTextField  countryTextEntry;
    private JLabel      countryIsDataOk;

    //user city input
    private JLabel      cityTextArea;
    private JButton     cityConfirmButton;
    private JTextField  cityTextEntry;
    private JLabel      cityIsDataOk;

    //user currency input
    private JLabel      currencyTextArea;
    private JButton     currencyConfirmButton;
    private JTextField  currencyTextEntry;
    private JLabel      currencyIsDataOk;


    public WebGUI() throws HeadlessException {

        //window init
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        //panels init
        mainPanel           = new JPanel();
        webPanel            = new JPanel();
        infoPanel           = new JPanel();
        buttonsPanel        = new JPanel();
        countryInputPanel   = new JPanel();
        cityInputPanel      = new JPanel();
        currencyInputPanel  = new JPanel();
        emptyPanel          = new JPanel();


        //web view construction
        jfxPanel = new JFXPanel();
        Platform.runLater(this::createJFXContent);
        webPanel.add(jfxPanel);

        //user input part creation
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        createButtons();
        buttonsPanel.add(countryInputPanel);
        buttonsPanel.add(cityInputPanel);
        buttonsPanel.add(currencyInputPanel);


        mainPanel.add(webPanel);
        mainPanel.add(buttonsPanel);
        this.add(mainPanel);

        this.pack();
    }

    private void createJFXContent() {
        webPanel.setLayout(new GridBagLayout());
        webView = new WebView();
        webView.getEngine().load("https://pl.wikipedia.org/wiki/Polska");
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
        this.pack();
    }

    private void createButtons(){

        //user country input construction
        countryInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        countryIsDataOk = new JLabel();
        countryIsDataOk.setText("-");
        countryInputPanel.add(countryIsDataOk);

        countryTextArea = new JLabel();
        countryTextArea.setText("Podaj kraj: ");
        countryTextArea.setPreferredSize(new Dimension(100, 20));
        countryInputPanel.add(countryTextArea);

        countryTextEntry = new JTextField(18);
        countryInputPanel.add(countryTextEntry);

        countryConfirmButton = new JButton("Confirm country");
        countryConfirmButton.addActionListener(this);
        countryConfirmButton.setPreferredSize(new Dimension(150,20));
        countryInputPanel.add(countryConfirmButton);


        //user city input construction
        cityInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        cityIsDataOk = new JLabel();
        cityIsDataOk.setText("-");
        cityInputPanel.add(cityIsDataOk);

        cityTextArea = new JLabel();
        cityTextArea.setText("Podaj miasto: ");
        cityTextArea.setPreferredSize(new Dimension(100, 20));
        cityInputPanel.add(cityTextArea);

        cityTextEntry = new JTextField(18);
        cityInputPanel.add(cityTextEntry);

        cityConfirmButton = new JButton("Confirm city");
        cityConfirmButton.addActionListener(this);
        cityConfirmButton.setPreferredSize(new Dimension(150,20));
        cityInputPanel.add(cityConfirmButton);


        //user currency input construction
        currencyInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        currencyIsDataOk = new JLabel();
        currencyIsDataOk.setText("-");
        currencyInputPanel.add(currencyIsDataOk);

        currencyTextArea = new JLabel();
        currencyTextArea.setText("Podaj walute: ");
        currencyTextArea.setPreferredSize(new Dimension(100, 20));
        currencyInputPanel.add(currencyTextArea);

        currencyTextEntry = new JTextField(18);
        currencyInputPanel.add(currencyTextEntry);

        currencyConfirmButton = new JButton("Confirm currency");
        currencyConfirmButton.addActionListener(this);
        currencyConfirmButton.setPreferredSize(new Dimension(150,20));
        currencyInputPanel.add(currencyConfirmButton);
    }

    private void reloadPage(){

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebGUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
