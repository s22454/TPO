/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) throws Exception{
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))){

            //reading yaml file
            final String collect = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));

            //creating yaml obj
            Yaml yaml = new Yaml();
            Map<String, Object> load = yaml.load(collect);

            //crating options obj
            Map<String, List<String>> clientsMap = new HashMap<>();
            clientsMap.putAll((Map) load.get("clientsMap"));

            return new Options(
                        (String)    load.get("host"),
                        (int)       load.get("port"),
                        (boolean)   load.get("concurMode"),
                        (boolean)   load.get("showSendRes"),
                                    clientsMap
                    );

        } catch (Exception e){
            throw e;
        }
    }
}
