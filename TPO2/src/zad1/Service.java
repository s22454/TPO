/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Service {
    
    private String country;

    //addresses
    private final String openWeatherAddress = "https://api.openweathermap.org/data/2.5/weather?q=Warsaw&appid=d25d1fc91da69a1211c1044143d394ad";
    
    public Service(String country){
        this.country = country;
    }

    public String getWeather(String city) {
        try(BufferedReader bufferedReader =
                    new BufferedReader(
                        new InputStreamReader(
                            new URL(this.openWeatherAddress).openConnection().getInputStream()))) {

            return bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public Double getRateFor(String currency) {
//    }
//
//    public Double getNBPRate() {
//    }
}