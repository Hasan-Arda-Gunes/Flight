import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra {
    public double[] dist;
    public HashMap<Integer,Integer> settled;
    public LinkedList<Airport> pq;
    public LinkedList<LinkedList<Airport>> adj;
    public final int V;
    public HashMap<Integer,Airport> nodeAirport;
    HashMap<String,String> weather;
    String plane;
    HashMap<String,Airport> hash;
    public long origin;

    Dijkstra(int v, HashMap<Integer,Airport> nodeAirport, HashMap<String,String> weather, String plane, HashMap<String,Airport> hash, long origin){
        this.nodeAirport = nodeAirport;
        V = v;
        dist = new double[V];
        settled = new HashMap<>();
        pq = new LinkedList<>();
        this.weather = weather;
        this.plane = plane;
        this.hash = hash;
        this.origin = origin;
    }

    //finds the path with the least cost
    public void dijkstra(LinkedList<LinkedList<Airport>> adj, int src){
        this.adj = adj;
        for (int i = 0; i<V;i++)
            dist[i] = Integer.MAX_VALUE;
        pq.add(nodeAirport.get(src));
        dist[src] = 0;
        while (settled.size() != V){
            if (pq.isEmpty())
                return;
            Airport u = pq.poll();

            if (settled.containsValue(u.Node))
                continue;

            settled.put(u.Node,u.Node);
            e_Neighbours(u);
        }
    }

    // compares the cost of flying to an airport from origin
    public void e_Neighbours(Airport u){
        double edgeDistance = -1;
        double newDistance = -1;
        for (int i = 0; i < adj.get(u.Node).size(); i++) {
            Airport v = adj.get(u.Node).get(i);
            if (!settled.containsValue(v.Node)) {
                edgeDistance = cost(v,u,origin);
                newDistance = dist[u.Node] + edgeDistance;
                if (newDistance < dist[v.Node]){
                    dist[v.Node] = newDistance;
                    v.parent = u;
                }

                pq.add(v);
            }
        }
    }

    // distance between the airports
    public double distance(Airport x , Airport y){
        int r = 6371;
        double lat1 = Math.toRadians(x.latitude);
        double lat2 = Math.toRadians(y.latitude);
        double lon1 = Math.toRadians(x.longitude);
        double lon2 = Math.toRadians(y.longitude);
        double distance = 2*r*Math.asin(Math.sqrt(Math.pow(Math.sin((lat2-lat1)/2),2) + Math.cos(lat1)* Math.cos(lat2) * Math.pow(Math.sin((lon2-lon1)/2),2)));
        return distance;
    }
    // the cost of flying from an airport to another
    public double cost(Airport x , Airport y, long time){
        double distance = distance(x,y);
        String mult1 = weather.get(x.airfieldName + time);
        String mult2 = weather.get(y.airfieldName + time);
        if (mult1 == null ||mult2 == null)
            System.out.println(1);
        return 300 * x.getWMultiplier(mult1) * y.getWMultiplier(mult2) + distance;
    }

    public long flightDuration(String plane,double distance){
        if (plane.equals("Carreidas 160")){
            if (distance <= 175)
                return 6*3600;
            else if (distance<= 350)
                return 12 * 3600;
            else
                return 18*3600;
        }
        else if (plane.equals("Orion III")){
            if (distance <=1500)
                return 6 * 3600;
            else if (distance <= 3000)
                return 12*3600;
            else
                return 18*3600;
            }
        else if (plane.equals("Skyfleet S570")){
            if (distance <=500)
                return 6 * 3600;
            else if (distance <= 1000)
                return 12*3600;
            else
                return 18*3600;
        }
        else {
            if (distance <=2500)
                return 6 * 3600;
            else if (distance <= 5000)
                return 12*3600;
            else
                return 18*3600;
        }

    }

}
