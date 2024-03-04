import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws Exception{
        File input = new File(args[0]);
        BufferedReader scan = new BufferedReader(new FileReader(input));
        HashMap<String,Airport> hashAirports = new HashMap<>();
        HashMap<String,String> weather = new HashMap<>();
        scan.readLine();
        HashMap<Integer,Airport> nodeAirport = new HashMap<>();
        HashMap<Airport,Integer> nodeAirport2 = new HashMap<>();
        LinkedList<LinkedList<Airport>> adj = new LinkedList<>();
        int n = 0;
        // read the airports from airports.csv
        // store the data in appropriate data structures
        while (scan.ready()){
            String line = scan.readLine();
            String[] airportInfo = line.split(",");
            double latitude = Double.parseDouble(airportInfo[2]);
            double longitude = Double.parseDouble(airportInfo[3]);
            int parkCost = Integer.parseInt(airportInfo[4]);
            Airport x = new Airport(airportInfo[0],airportInfo[1],latitude,longitude,parkCost);
            x.setNode(n);
            adj.add(new LinkedList<>());
            nodeAirport.put(n,x);
            nodeAirport2.put(x,n);
            n+=1;
            hashAirports.put(airportInfo[0],x);

        }
        // read the directions file
        File input2 = new File(args[1]);
        scan = new BufferedReader(new FileReader(input2));
        scan.readLine();
        // create an adjacent list to use in Dijkstra
        while (scan.ready()){
            String line = scan.readLine();
            String[] directionsInfo = line.split(",");
            String from = directionsInfo[0];
            String to = directionsInfo[1];
            Airport fromAirport = hashAirports.get(from);
            Airport toAirport = hashAirports.get(to);
            int listNum = nodeAirport2.get(fromAirport);
            adj.get(listNum).add(toAirport);


        }
        // read the weather file
        //AirfieldName,Time,WeatherCode  the order of information
        File input3 = new File(args[2]);
        scan = new BufferedReader(new FileReader(input3));
        scan.readLine();
        // turn them into binary and store them in order to find the costs of flight
        while (scan.ready()){
            String info = scan.readLine();
            String[] line = info.split(",");
            int num = Integer.parseInt(line[2]);
            String temp = "";
            while (num != 1 && num != 0 ){
                temp += num%2;
                num /=2;
            }
            temp+=num;
            String bin = "";
            for (int i = 0; i<temp.length(); i++){
                bin += temp.charAt(temp.length()-i-1);
            }
            if (bin.length() == 1)
                bin = "0000" + bin;
            else if (bin.length() == 2)
                bin = "000" + bin;
            else if (bin.length() == 3)
                bin = "00" + bin;
            else if (bin.length() == 4)
                bin = "0" + bin;
            weather.put(line[0]+line[1],bin);

        }
        // read the missions file
        // From To OriginTime Deadline   the order of information
        File missions = new File(args[3]);
        FileWriter out1 = new FileWriter(args[4]);
        scan = new BufferedReader(new FileReader(missions));
        String plane = scan.readLine();
        while (scan.ready()){
            String line = scan.readLine();
            String[] info = line.split(" ");
            Airport from = hashAirports.get(info[0]);
            Airport to = hashAirports.get(info[1]);
            long originTime = Long.parseLong(info[2]);
            out1.append(from.airportCode + " ");
            // find the path with the least cost with dijkstra
            Dijkstra path = new Dijkstra(nodeAirport.size(), nodeAirport,weather,plane,hashAirports,originTime);
            path.dijkstra(adj, from.Node);
            ArrayList<Airport> shortestPath = new ArrayList<>();
            Airport parent = to;
            while (parent != from){
                shortestPath.add(parent);
                parent = parent.parent;
            }
            while (!shortestPath.isEmpty()){
                out1.append(shortestPath.remove(shortestPath.size() - 1).airportCode + " ");
            }
            out1.append(String.format("%.5f", path.dist[to.Node]) + "\n");
        }
        out1.close();
        FileWriter out2 = new FileWriter(args[5]);

        scan.close();
        out2.close();

    }
}