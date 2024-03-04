public class Airport {
    public String airportCode;
    public String airfieldName;
    public double latitude;
    public double longitude;
    public int parkingCost;
    public int Node;
    public Airport parent;
    Airport(String code, String airfield, double latitude, double longitude, int parkingCost){
        airportCode = code;
        airfieldName = airfield;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingCost = parkingCost;
        Node = -2;
        parent = new Airport();
    }
    Airport(){
        airportCode = "";
        airfieldName = "";
        this.latitude = 0;
        this.longitude = 0;
        this.parkingCost = 0;
        Node = -2;
    }

    public void setNode(int node) {
        Node = node;
    }
    // gets the weather multiplier of the airport according to the binary code
    public double getWMultiplier(String binary){
        int w = Integer.parseInt(binary.charAt(0) + "");
        int r = Integer.parseInt(binary.charAt(1) + "");
        int s = Integer.parseInt(binary.charAt(2) + "");
        int h = Integer.parseInt(binary.charAt(3) + "");
        int b = Integer.parseInt(binary.charAt(4) + "");
        double WMultiplier = (w*1.05 + (1-w)) * (r*1.05  + (1-r)) * (s*1.10 + (1-s)) * (h*1.15 + (1-h)) * (b*1.20 + (1-b));
        return WMultiplier;
    }

}
