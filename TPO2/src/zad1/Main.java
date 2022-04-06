/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    System.out.println(rate1);

    // część uruchamiająca GUI

//    SwingUtilities.invokeLater(WebGUI::new);
  }
}
