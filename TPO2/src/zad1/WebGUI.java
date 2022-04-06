package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class WebGUI extends JFrame {

    //panels
    private JPanel      mainPanel;
    private JPanel      webPanel;
    private JPanel      infoPanel;
    private JPanel      buttonsPanel;
    private JFXPanel    jfxPanel;

    //country info are
    private JTextArea   infoTextArea;

    //web view
    WebView             webView;

    //user country input
    private JTextArea   countryTextArea;
    private JButton     countryConfirmButton;
    private JTextField  countryTextEntry;
    private JTextArea   countryIsDataOk;

    //user city input
    private JTextArea   cityTextArea;
    private JButton     cityConfirmButton;
    private JTextField  cityTextEntry;
    private JTextArea   cityIsDataOk;

    //user currency input
    private JTextArea   currencyTextAre;
    private JButton     currencyConfirmButton;
    private JTextField  currencyTextEntry;
    private JTextArea   currencyIsDataOk;


    public WebGUI() throws HeadlessException {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //panels init
        mainPanel       = new JPanel();
        webPanel        = new JPanel();
        infoPanel       = new JPanel();
        buttonsPanel    = new JPanel();

        //web view construction
        jfxPanel = new JFXPanel();
        Platform.runLater(this::createJFXContent);
        webPanel.add(jfxPanel);

        //user input area construction
        


        mainPanel.add(webPanel);
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

    private void reloadPage(){

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebGUI::new);
    }
}
