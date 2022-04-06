import javax.swing.*;
import java.awt.*;

public class WebGUI extends JFrame {

    //panels
    private JPanel      mainPanel;
    private JPanel      mainPanel;

    //country info are
    private JTextArea   infoTextArea;

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

        this.pack();
    }
}
