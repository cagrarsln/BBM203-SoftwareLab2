import java. lang.*;
import java.io.*;
import java.util.*;

public class FileFunctions {
    public static Integer[] getData(String fileName){
        try {
            BufferedReader reader = new BufferedReader (new FileReader(fileName));
            List<Integer> salaries = new ArrayList<>();
            reader.readLine();
            String line;
            while ( (line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                salaries.add(Integer.parseInt(splitted[6]));
            }
            Integer[] resultArray = new Integer [salaries. size()];
            return salaries.toArray(resultArray);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
