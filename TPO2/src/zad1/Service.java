/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;

public class Service {
    
    private String country;
    private Locale locale;

    public Service(String country){
        this.country = country;

        for(Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry(Locale.ENGLISH).toLowerCase(Locale.ROOT).equals(country.toLowerCase(Locale.ROOT)))
                this.locale = l;
        }

        System.out.println(locale.getLanguage());
        System.out.println(Currency.getInstance(locale).toString());
    }

    public String getWeather(String city) {

        String openWeatherAddress = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=d25d1fc91da69a1211c1044143d394ad";

        try(BufferedReader bufferedReader =
                    new BufferedReader(
                        new InputStreamReader(
                            new URL(openWeatherAddress).openConnection().getInputStream()))) {

            return bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
            return "FileNotFoundException";
        }
    }

    public Double getRateFor(String currency) {
        String address = "https://api.exchangerate.host/latest";

        try(BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new URL(address).openConnection().getInputStream()))) {

            JSONParser parser               = new JSONParser();
            JSONObject parse                = (JSONObject) parser.parse(bufferedReader.readLine());
            JSONObject rates                = (JSONObject) parse.get("rates");
            double givenCountryRateToEUR    = Double.parseDouble(rates.get(currency).toString());
            double thisCountryRateToEUR     = Double.parseDouble(rates.get(Currency.getInstance(locale).toString()).toString());

            double exchangeRate = (1 / givenCountryRateToEUR) * thisCountryRateToEUR;

            return exchangeRate;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Double getNBPRate() {
        return null;
    }

    public String getCurrency(){
        return Currency.getInstance(locale).toString();
    }
}