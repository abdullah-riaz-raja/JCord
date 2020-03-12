package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;

public class Utils {

    // takes in json path to parse through
    public static HashMap<String, String> getServerInfo(String jsonPath) throws FileNotFoundException {
       
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/"+jsonPath));

        Gson gson = new Gson();
        HashMap<String, String> json = gson.fromJson(reader, HashMap.class);
        
        return json;
    }

    
}