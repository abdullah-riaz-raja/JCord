package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import com.google.gson.Gson;

/**
 * This class parses the json communication.json file into directories
 */
public class Utils {

    /**
     * This static method takes in a json path and parses it
     *
     * @param jsonPath String value of the path
     * @return HashMap of the path
     * @throws FileNotFoundException
     * @see BufferedReader
     * @see FileReader
     * @see HashMap
     * @see Gson
     * @see Gson#fromJson()
     */
    public static HashMap<String, String> getServerInfo(String jsonPath) throws FileNotFoundException {
       
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/"+jsonPath));

        Gson gson = new Gson();
        HashMap<String, String> json = gson.fromJson(reader, HashMap.class);
        
        return json;
    }

    
}