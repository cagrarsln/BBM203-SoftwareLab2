import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]*)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);

    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([-+]?(\\d*\\.\\d+|\\d+))");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));

    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Point p = new Point(0, 0);
        // TODO: Your code goes here
        String regex = "[\\t ]*" +varName+"[\\t ]*=[\\t ]*\\(\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(fileContent);
        m.find();

        p.x = Integer.parseInt(m.group(1));
        p.y = Integer.parseInt(m.group(2));



        return p;
    }



    /**
     * Function to extract the train lines from the fileContent by reading train line names and their
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();

        Pattern linePattern = Pattern.compile("[\\t ]*train_line_name[\\t ]*=[\\t ]*\"([^\"]+)\"");
        Pattern stationPattern = Pattern.compile("[\\t ]*train_line_stations[\\t ]*=[\\t ]*((?:\\([^\\)]+\\)\\s*)+)");

        Matcher lineMatcher = linePattern.matcher(fileContent);
        Matcher stationMatcher = stationPattern.matcher(fileContent);

        while (lineMatcher.find() && stationMatcher.find()) {
            String lineName = lineMatcher.group(1);

            String stationsString = stationMatcher.group(1);

            List<Station> stations = new ArrayList<>();

            Pattern pointPattern = Pattern.compile("\\(([^,]+),\\s*([^\\)]+)\\)");
            Matcher pointMatcher = pointPattern.matcher(stationsString);

            int stationIndex = 1;
            while (pointMatcher.find()) {
                int x = Integer.parseInt(pointMatcher.group(1).trim());
                int y = Integer.parseInt(pointMatcher.group(2).trim());
                Point point = new Point(x,y);
                String stationName = lineName + " Line Station " + stationIndex;
                Station station = new Station(point, stationName);
                stations.add(station);
                stationIndex++;
            }

            TrainLine trainLine = new TrainLine(lineName, stations);
            trainLines.add(trainLine);

        }


        return trainLines;
    }


    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */


    public void readInput(String filename) {
        try {
            // Dosya içeriğini okuma
            String fileContent = new String(Files.readAllBytes(Paths.get(filename)));


            // numTrainLines değişkenini okuma
            numTrainLines = getIntVar("num_train_lines", fileContent);
            // numTrainLines kontrolü

            // starting_point değişkenini okuma
            Point startPointCoordinates = getPointVar("starting_point", fileContent);
            startPoint = new Station(startPointCoordinates, "Starting Point");

            // averageTrainSpeed değişkenini okuma
            averageTrainSpeed = getDoubleVar("average_train_speed", fileContent) *1000/60;

            // destination_point değişkenini okuma
            Point destinationPointCoordinates = getPointVar("destination_point", fileContent);
            destinationPoint = new Station(destinationPointCoordinates, "Final Destination");

            // Tren hatlarını okuma
            lines = getTrainLines(fileContent);



        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }


}