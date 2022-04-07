package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class WebGUI extends JFrame implements ActionListener {

    //panels
    private final JPanel      mainPanel;
    private final JPanel      webPanel;
    private final JPanel      infoPanel;
    private final JPanel      buttonsPanel;
    private final JPanel      countryInputPanel;
    private final JPanel      cityInputPanel;
    private final JPanel      currencyInputPanel;
    private final JPanel      rightPanel;
    private final JFXPanel    jfxPanel;

    //country info are
    private JLabel      infoTextArea;

    //web view
    WebView             webView;

    //user country input
    private JLabel      countryTextArea;
    private JButton     countryConfirmButton;
    private JTextField  countryTextEntry;
    private JCheckBox   countryIsDataOk;

    //user city input
    private JLabel      cityTextArea;
    private JButton     cityConfirmButton;
    private JTextField  cityTextEntry;
    private JCheckBox   cityIsDataOk;

    //user currency input
    private JLabel              currencyTextArea;
    private JButton             currencyConfirmButton;
    private JComboBox<String>   currencyComboBox;
    private JCheckBox           currencyIsDataOk;

    //service objects
    Service             service;

    public WebGUI() throws HeadlessException {

        //window init
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Some weather app");


        //panels init
        mainPanel           = new JPanel();
        webPanel            = new JPanel();
        infoPanel           = new JPanel();
        buttonsPanel        = new JPanel();
        countryInputPanel   = new JPanel();
        cityInputPanel      = new JPanel();
        currencyInputPanel  = new JPanel();
        rightPanel          = new JPanel();


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
        buttonsPanel.setPreferredSize(new Dimension(490,150));


        //info to jPanel
        infoTextArea = new JLabel();
        infoTextArea.setPreferredSize(new Dimension(490,450));
        infoTextArea.setVerticalAlignment(JLabel.TOP);
        infoTextArea.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        infoTextArea.setText("Information");
        infoPanel.add(infoTextArea);


        //panels organization
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(buttonsPanel);
        rightPanel.add(Box.createGlue());
        rightPanel.add(infoPanel);


        //main panel organization
        mainPanel.add(webPanel);
        mainPanel.add(rightPanel);
        this.add(mainPanel);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void createJFXContent() {
        webPanel.setLayout(new GridBagLayout());
        webView = new WebView();
        webView.getEngine().load("https://pl.wikipedia.org/wiki");
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
        this.pack();
    }

    private void reloadPage(){
        webView.getEngine().load("https://pl.wikipedia.org/wiki/" + countryTextEntry.getText());
    }

    private void createButtons(){

        //user country input construction
        countryInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        countryIsDataOk = new JCheckBox();
        countryIsDataOk.setSelected(false);
        countryIsDataOk.setEnabled(false);
        countryInputPanel.add(countryIsDataOk);

        countryTextArea = new JLabel();
        countryTextArea.setText("Country: ");
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

        cityIsDataOk = new JCheckBox();
        cityIsDataOk.setSelected(false);
        cityIsDataOk.setEnabled(false);
        cityInputPanel.add(cityIsDataOk);

        cityTextArea = new JLabel();
        cityTextArea.setText("City: ");
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

        currencyIsDataOk = new JCheckBox();
        currencyIsDataOk.setSelected(false);
        currencyIsDataOk.setEnabled(false);
        currencyInputPanel.add(currencyIsDataOk);

        currencyTextArea = new JLabel();
        currencyTextArea.setText("Currency: ");
        currencyTextArea.setPreferredSize(new Dimension(100, 20));
        currencyInputPanel.add(currencyTextArea);

        String[] currencyList = new String[Currency.getAvailableCurrencies().size()];
        ArrayList<Currency> currencyArrayList = new ArrayList<>(Currency.getAvailableCurrencies());
        for (int i = 0; i < currencyList.length; i++)
            currencyList[i] = currencyArrayList.get(i).toString();
        currencyComboBox = new JComboBox<>(currencyList);
        currencyComboBox.setBackground(Color.WHITE);
        currencyComboBox.setPreferredSize(new Dimension(202,20));
        currencyInputPanel.add(currencyComboBox);

        currencyConfirmButton = new JButton("Confirm currency");
        currencyConfirmButton.addActionListener(this);
        currencyConfirmButton.setPreferredSize(new Dimension(150,20));
        currencyInputPanel.add(currencyConfirmButton);
    }

    private boolean isCountryOk(String country){
        for(Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry(Locale.ENGLISH).toLowerCase(Locale.ROOT).equals(country.toLowerCase(Locale.ROOT)))
                return true;
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebGUI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        infoTextArea.setText("Information");
        infoTextArea.setForeground(Color.BLACK);

        if (e.getSource() == countryConfirmButton){
            if (isCountryOk(countryTextEntry.getText())){
                service = new Service(countryTextEntry.getText());
                Platform.runLater(this::reloadPage);
                countryIsDataOk.setSelected(true);
                countryIsDataOk.setForeground(Color.black);
            } else {
                countryIsDataOk.setSelected(false);
                infoTextArea.setForeground(Color.RED);
                infoTextArea.setText("Wrong country name!");
            }

        } else if (e.getSource() == cityConfirmButton){
            try {
                String response     = service.getWeather(cityTextEntry.getText());
                JSONParser parser   = new JSONParser();

                if (response.equals("FileNotFoundException"))
                    throw new FileNotFoundException();

                JSONObject parse    = (JSONObject) parser.parse(response);

                JSONObject main     = (JSONObject) parse.get("main");
                double temp         = (double) main.get("temp");
                long pressure       = (long) main.get("pressure");

                JSONArray weather   = (JSONArray) parse.get("weather");
                JSONObject info     = (JSONObject) weather.get(0);
                String description  = (String) info.get("description");

                infoTextArea.setText("<html>" + "Temp: " + temp + "<br>"
                                    + "Pressure: " + pressure + "<br>"
                                    + "Description: " + description + "<br>");

                cityIsDataOk.setSelected(true);
                cityIsDataOk.setForeground(Color.BLACK);

            } catch (Exception exception){
                cityIsDataOk.setSelected(false);
                infoTextArea.setForeground(Color.RED);
                infoTextArea.setText("Wrong city name!");
            }

        } else if (e.getSource() == currencyConfirmButton){
            try {
                infoTextArea.setText("<html> Exchange rate for " + service.getCurrency()
                                        + " to " + currencyComboBox.getSelectedItem()
                                        + " is: <br>" + service.getRateFor(currencyComboBox.getSelectedItem().toString()));

                currencyIsDataOk.setSelected(true);
                currencyIsDataOk.setForeground(Color.BLACK);
            }catch (Exception exception){
                currencyIsDataOk.setSelected(false);
                infoTextArea.setForeground(Color.RED);
                infoTextArea.setText("<html> Wrong country name or we dont have <br> exchange rate for this country!");
            }
        }
    }
}
