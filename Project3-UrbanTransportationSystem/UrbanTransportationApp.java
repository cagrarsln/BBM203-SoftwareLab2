import java.io.Serializable;

import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;
    
    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function calculate the fastest route from the user's desired starting point to 
     * the desired destination point, taking into consideration the hyperloop train
     * network. 
     * @return List of RouteDirection instances
     */

    public static List<RouteDirection> removeDuplicates(List<RouteDirection> routeDirections) {
        List<RouteDirection> otherList = new ArrayList<>();
        for (int i = 0 ; i < routeDirections.size(); i++){
            if (i +1  == routeDirections.size()){
                otherList.add(routeDirections.get(i));
                break;
            }else {
                if (routeDirections.get(i).startStationName.equals( routeDirections.get(i+1).startStationName) && routeDirections.get(i).endStationName.equals( routeDirections.get(i+1).endStationName)){

                } else {
                    otherList.add(routeDirections.get(i));
                }
            }

        }
        return otherList;
    }


    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        Map<Station, Integer> stationToIndex = new HashMap<>();
        List<Station> allStations = new ArrayList<>();

        allStations.add(network.startPoint);
        stationToIndex.put(network.startPoint, 0);

        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                if (!stationToIndex.containsKey(station)) {
                    stationToIndex.put(station, allStations.size());
                    allStations.add(station);
                }
            }
        }

        allStations.add(network.destinationPoint);
        stationToIndex.put(network.destinationPoint, allStations.size()-1);

        EdgeWeightedGraph graph = new EdgeWeightedGraph(allStations.size());

        // Add edges for train lines
        for (TrainLine line : network.lines) {
            List<Station> stations = line.trainLineStations;
            for (int i = 0; i < stations.size() - 1; i++) {
                Station current = stations.get(i);
                Station next = stations.get(i + 1);
                int v = stationToIndex.get(current);
                int w = stationToIndex.get(next);
                double weight = current.coordinates.distance(next.coordinates) / network.averageTrainSpeed ;
                graph.addEdge(new Edge(v, w, weight));
            }
        }


        // Add edges for walking between starting point and stations
        addWalkingEdges(network.startPoint, network, stationToIndex, graph, 1);

        // Add edges for walking between destination point and stations
        addWalkingEdges(network.destinationPoint, network, stationToIndex, graph, 0);

        // Add edges for walking between stations in different lines
        addWalkingEdgesBetweenLines(network, stationToIndex, graph);


        // Run Dijkstra's algorithm to find the shortest path from startPoint to destinationPoint
        int source = stationToIndex.get(network.startPoint);
        int target = stationToIndex.get(network.destinationPoint);

        DijkstraSP sp = new DijkstraSP(graph, source);

        if (!sp.hasPathTo(target)) {
            return routeDirections; // No path found
        }
        double totalDuration = 0;
        double duration=0 ;
        // Convert the path to RouteDirection objects
        for (Edge e : sp.pathTo(target)) {

            int v = e.either();
            int w = e.other(v);

            Station startStation;
            Station endStation;
            if (sp.distTo(v) < sp.distTo(w)) {
                startStation = allStations.get(v);
                endStation = allStations.get(w);
            } else {
                startStation = allStations.get(w);
                endStation = allStations.get(v);
            }

            boolean isTrainRide = false;

            for (TrainLine line : network.lines) {
                if (line.trainLineStations.contains(startStation) && line.trainLineStations.contains(endStation)) {
                    isTrainRide = true;
                    break;
                }
            }


            String startStationName = startStation.description;
            String endStationName = endStation.description;
            duration = e.weight();
            totalDuration += duration;
            boolean trainRide = isTrainRide;

            RouteDirection direction = new RouteDirection(startStationName, endStationName, duration, trainRide);
            routeDirections.add(direction);
        }

        Collections.reverse(routeDirections);
        routeDirections = removeDuplicates(routeDirections);

        double start = network.startPoint.coordinates.distance(network.destinationPoint.coordinates) / network.averageWalkingSpeed;

        if (start < totalDuration/2) {
            routeDirections.clear();
            RouteDirection direction = new RouteDirection(network.startPoint.description, network.destinationPoint.description, start, false);
            routeDirections.add(direction);
        }

        return routeDirections;
    }

    private void addWalkingEdges(Station point, HyperloopTrainNetwork network, Map<Station, Integer> stationToIndex, EdgeWeightedGraph graph , int startOrNot) {
        if ( startOrNot == 1){
            for (TrainLine line : network.lines) {
                for (Station station : line.trainLineStations) {
                    int v = stationToIndex.get(point);
                    int w = stationToIndex.get(station);
                    double weight = point.coordinates.distance(station.coordinates) / network.averageWalkingSpeed;
                    graph.addEdge(new Edge(v, w, weight));

                }
            }
        } else {
            for (TrainLine line : network.lines) {
                for (Station station : line.trainLineStations) {
                    int v = stationToIndex.get(station);
                    int w = stationToIndex.get(point);
                    double weight = point.coordinates.distance(station.coordinates) / network.averageWalkingSpeed;
                    graph.addEdge(new Edge(v, w, weight));
                }
            }
        }

    }

    private void addWalkingEdgesBetweenLines(HyperloopTrainNetwork network, Map<Station, Integer> stationToIndex, EdgeWeightedGraph graph) {
        for (int i = 0; i < network.lines.size(); i++) {
            List<Station> lineAStations = network.lines.get(i).trainLineStations;
            for (int j = i + 1; j < network.lines.size(); j++) {
                List<Station> lineBStations = network.lines.get(j).trainLineStations;
                for (Station stationA : lineAStations) {
                    for (Station stationB : lineBStations) {
                        int v = stationToIndex.get(stationA);
                        int w = stationToIndex.get(stationB);
                        double weight = stationA.coordinates.distance(stationB.coordinates) / network.averageWalkingSpeed;
                        graph.addEdge(new Edge(v, w, weight));

                    }
                }
            }
        }
    }


    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        if (directions.isEmpty()) {
            System.out.println("No directions found.");
            return;
        }

        double totalTime = 0;
        for (RouteDirection direction : directions) {
            totalTime += direction.duration;
        }

        int roundedTotalTime = (int) Math.round(totalTime);

        System.out.println("The fastest route takes " + roundedTotalTime + " minute(s).");
        System.out.println("Directions");
        System.out.println("----------");

        int directionIndex = 1;
        for (RouteDirection direction : directions) {
            String type = direction.trainRide ? "Get on the train from" : "Walk from";
            String start = direction.startStationName;
            String end = direction.endStationName;
            System.out.println(directionIndex + ". " + type + " \"" + start + "\" to \"" + end + "\" for " + String.format("%.2f", direction.duration) + " minutes.");
            directionIndex++;
        }
    }
}

