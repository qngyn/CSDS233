import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class Map {
    ArrayList<Building> buildings; //key is building and values are roads
    HashMap<String,Building>buildingNameMap ; //map to look for Building object, key is Building name, value is Building object
    int buildingNum;
    
    public Map(){
        buildings = new ArrayList<>();
        buildingNameMap = new HashMap<>();
    }
    public final boolean addBuilding(String name){//add building to map, if the building already exists, throw illegal argument exception
        
        if(buildingNameMap.containsKey(name)){
            throw new IllegalArgumentException(name+" already added to map");
        }else{
            Building b = new Building(name);
            b.id_index = buildingNum;
            buildingNameMap.put(name,b);
            System.out.println("Adding "+name+" to map");
            buildings.add(b);
            buildingNum++;
            return true;
        }       
    }
    
    public final boolean addRoad(String fromBuilding,String toBuilding, int length){//add single road to map,return false if road already exists in map
        //check if fromBuilding and toBuilding exist in the map. If not, add to Map

        if(!buildingNameMap.containsKey(fromBuilding)) {
            System.out.println("adding "+fromBuilding +" to map");
            addBuilding(fromBuilding);
        }
        if(!buildingNameMap.containsKey(toBuilding)){
            System.out.println("adding "+toBuilding+" to map");
            addBuilding(toBuilding);
        }
        if(fromBuilding.equals(toBuilding)){
            throw new IllegalArgumentException("fromBuilding and toBuilding cannot be the same");
        }
//        if(length==0){
//            throw new IllegalArgumentException("no distance between frombuilding and to building. Error");
//        }
        
        Building src = buildingNameMap.get(fromBuilding);
        Building dest = buildingNameMap.get(toBuilding);
        //add building dest to adjacentlist of srcBuilding
        if(src.hasRoad(toBuilding)||dest.hasRoad(fromBuilding)){//use hasRoad to check if the road exists
            //if road exists return false 
            return false;
        }else{           
            System.out.println("adding Road from "+fromBuilding+" to "+toBuilding+" with length "+length);
            src.roads.add(new Road(fromBuilding,toBuilding,length));
            dest.roads.add(new Road(toBuilding,fromBuilding,length));
            return true;
        }      
        
    }
    
    public boolean checkRoad(String src,String dest){//check if road exists between two Building
        if(buildingNameMap.get(src).hasRoad(dest)|| buildingNameMap.get(dest).hasRoad(src)){
            return true;
        }
        return false;
    }
    
    public final boolean addRoads(String fromBuilding, Collection<String> toBuildings,int length){
        boolean res=true;
        for(String toBuilding: toBuildings){
            res=addRoad(fromBuilding,toBuilding,length);
        }
        return false;
    }
    
    public final boolean removeBuilding(String name) throws Exception{
        //throw error if building to be removed does not exist on map
        if(!buildingNameMap.containsKey(name)){
            throw new Exception("map does not contain building "+name);
        }
        //remove all road connected to the input Building name
        //iterate all other Building except input Building, find Road that point to or input Building point to
        for (Building b:buildings){
            if(!b.buildingName.equals(name) && b.doesConnectTo(name)){
                b.removeRoad(name);
            }
        }
        buildings.remove(buildingNameMap.get(name));
        buildingNameMap.remove(name);
        buildingNum--;
        return true;
    }
    
    public final boolean removeRoad(String fromBuilding,String toBuilding) throws Exception{
        if(fromBuilding.equals(toBuilding)){
            throw new Exception("no road for same building. Check input ");
        }
        Building srcBuilding = buildingNameMap.get(fromBuilding);
        Building destBuilding = buildingNameMap.get(toBuilding);
        //check if road exists
        if(srcBuilding.hasRoad(toBuilding)){
            srcBuilding.removeRoad(toBuilding);
            destBuilding.removeRoad(fromBuilding);
            return true;
        }
        return false;
    }
    
    public List<List<Road>>getAllRoads(){
        List<List<Road>> roads_adjList = new ArrayList<List<Road>>();
        for(Building b: buildings){
            roads_adjList.add(b.roads);
        }
        return roads_adjList;
    }
    
    public final int shortestLength(String source,String destination) throws Exception{
        if(!buildingNameMap.containsKey(source))throw new Exception(source+" does not exist in the map");
        if(!buildingNameMap.containsKey(destination))throw new Exception(destination+" does not exist in the map");
        if(source==destination)return 0;
        dijkstra_map dmap = new dijkstra_map(source,buildings,buildingNameMap);
        dmap.dijkstra();
//        dmap.dijkstra();
        return dmap.getShortestLength(destination);
    }
    
    public final List<String> shortestPath(String source,String destination){
        if(!buildingNameMap.containsKey(source))throw new IllegalArgumentException(source+" does not exist in the map");
        if(!buildingNameMap.containsKey(destination))throw new IllegalArgumentException(destination+" does not exist in the map");
        dijkstra_map dmap = new dijkstra_map(source,buildings,buildingNameMap);
        dmap.dijkstra();
        return dmap.getBuildingsInSP(destination);
    }
    
    public final int minimumTotalLength(){
        prim_mst pmap = new prim_mst(this.buildings,buildingNameMap);
        pmap.mst();
        int mstSum = pmap.getMST_cost();
//        System.out.println(pmap.getSpanningTree());
        return mstSum;
    }
    
    public final int secondShortestPath(String source,String destination) throws Exception{
        if(!buildingNameMap.containsKey(source))throw new Exception(source+" does not exist in the map");
        if(!buildingNameMap.containsKey(destination))throw new Exception(destination+" does not exist in the map");
        if(source==destination) return 0;
        
        dijkstra_map dmap = new dijkstra_map(source,buildings,buildingNameMap);
        dmap.dijkstra();
        List<String> buildingsInSP = dmap.getBuildingsInSP(destination);
        Collections.reverse(buildingsInSP);//reverse because getBuildingInSP is looking buildings from dest to src
        
        int min2ndShortestLength=Integer.MAX_VALUE;
        //remove Road that is in shortest path. and try with every edge in the shortest path so the number of trial is totalBuilding number - 1
        for (int i=0;i<buildingsInSP.size()-1;i++){
            String src = buildingsInSP.get(i);
            String dest = buildingsInSP.get(i+1);
            int length = buildingNameMap.get(src).getDistance(dest);
            //remove road between src and dest building
            removeRoad(src,dest);
            //conduct dijkstra algorithm and find shortest path 
//            dijkstra_map dmap2 = new dijkstra_map(source,buildings,buildingNameMap);
//            dmap2.dijkstra();
//            int dijkstraShortestLength = dmap2.getShortestLength(destination);
            int dijkstraShortestLength = shortestLength(src,dest);
            //compare current shorted  path to min2ndShortestLength
            min2ndShortestLength=min2ndShortestLength>dijkstraShortestLength? dijkstraShortestLength:min2ndShortestLength;
            //add road back
            addRoad(src,dest,length);
        }        
        return min2ndShortestLength;
    }
    
    public void printMap(){
        System.out.println("******************************************************");
        System.out.println("total of Building "+buildingNum);    
        for(Building b: buildings){           
            if(b.roads.size()!=0){
                System.out.println("*"+b.buildingName+" has total "+ b.roads.size()+" roads ");
                System.out.println("\t"+b.getPath());
            }
        }
    }
    //Constructor
    private class Building {//a building containig properties: name and roads
        private String buildingName;    //name of building object
        private int id_index;           //order of building when it's added to map
        private LinkedList<Road> roads; // Adjacent list
        private Building prevBuilding;  // Pointer to previous Building     
        private Building(String buildingName) {
            this.buildingName = buildingName;
            roads = new LinkedList<>();
        }
        
        private String getBuildingName(){
            return this.buildingName;
        }
        
        private List<Road>getAdjacentBuildings(){
            List<Road> r = this.roads;
            return r;
        }
        
        private int getID_index(){
            return this.id_index;
        }
        
        private int getDistance(String nextBuilding){//get distance of road between current building and nextBuilding
            int length=0;
            for(Road r: roads){
                if (r.getNextBuildingName().equals(nextBuilding)){
                    return r.length;
                }
            }
            return length;
        }
        
        private String getPath(){//Get complete path of linkedlist .E.g A->B->C (means Building A connected to Building B, Building A connected to Building C)
            StringBuilder st = new StringBuilder();
            st.append(buildingName).append(":").append(buildingName).append("->");
            for(Road r: roads){
                st.append(r.getNextBuildingName()).append("->");
            }
            
            return st.substring(0,st.length()-2).toString();
        }
               
        private boolean hasRoad(String nextBuildingName){
            for(Road r: roads){
                if(r.getNextBuildingName().equals(nextBuildingName)){
                    return true;
                }
            }
            return false;
        }
        
//        private void addRoad(Building nextBuilding,int length){
//            roads.add(new Road(nextBuilding,length));
//        }
//        
//        private int getDistance(String nextBuilding){//get distance of road between current building and nextBuilding
//            int length=0;
//            for(Road r: roads){
//                if (r.toBuilding.getBuildingName().equals(nextBuilding)){
//                    return r.length;
//                }
//            }
//            return length;
//        }
        
        
        private boolean doesConnectTo(String buildingName){
            return roads.stream().anyMatch(r ->r.getNextBuildingName().equals(buildingName));            
        }
        private void removeRoad(String delBuildingName){//find index of road that lead to building needed to be and deleted, and delete that road
            //if there no road in that building,return
            if(roads.isEmpty())return;
            //find road of this.Building that connect to delBuilding           
            for(int i=0;i<roads.size();i++){
                //if this.BuildingName points to delBuilding, then delete that road 
                if(roads.get(i).getNextBuildingName().equals(delBuildingName)){
                    System.out.println("Removing "+roads.get(i).getPath());
                    roads.remove(i);
                    return;
                }
            }
        }
        
    }       

    private  class Road {
        private int length;
        private String fromBuildingName;
        private String toBuildingName;
        private int dToSrc;//for dijkstra
        String path;

        private Road(String fromBuildingName,String toBuildingName,int length) {
            this.fromBuildingName = fromBuildingName;
            this.length = length;
            this.dToSrc=length;//initalize dToSr equals length first, will update later in dihjktra algorithm
            this.toBuildingName = toBuildingName;
            addPath();
        }
        
        private String getNextBuildingName(){
            return toBuildingName;
        }
        
        private void updateDistanceToSource(int newLength){
            this.dToSrc = newLength;
        }
//        private void updateRoad(Building newBuilding, int newLength){
//            this.toBuilding= newBuilding;
//            this.length=newLength;
//        }
        
        private String getData(){
            return getNextBuildingName()+"_"+length;                 
        }
        
        private void addPath(){
            this.path = fromBuildingName+"->" +toBuildingName;
        }
        
        private String getPath(){
            return this.path;
        }
    }
    
    private class dijkstra_map{
        HashMap<String,Integer>distanceMap; //key is BuildingName, value is distance of that buildingto source Building
        HashMap<String,String>prevBuildingMap; //key is BuildingName, value is previous Building of shortest path
        
        int buildingNum;
        PriorityQueue<Road> pq; //priority queue containig all considered Road, with sorted distance from buildingA to srcBuilding from min to max
        String srcBuildingName;
        
        //initialize
        public dijkstra_map(String srcBuilding,ArrayList<Building> buildings, HashMap<String,Building>buildingNameMap ){           
            this.srcBuildingName=  srcBuilding;
             //initalize max value for all distances of other buildings to sourceBuilding, distance to srcBuilding itself is 0
            this.distanceMap = new HashMap<>();
            this.prevBuildingMap = new HashMap();
            //set the distance from each building to srcBuiling largest, and previousBuilding of those building is NULL
            for(String buildingName:buildingNameMap.keySet()){
                if(!distanceMap.containsKey(buildingName)){
                    if(buildingName.equals(srcBuilding)) distanceMap.put(buildingName,0);
                    else distanceMap.put(buildingName, Integer.MAX_VALUE);
                    prevBuildingMap.put(buildingName,null);
                }
            }
            this.buildingNum = buildingNameMap.size();
            //when Road is added to priority queue, it's added to the order of distance to source Building
            pq = new PriorityQueue<Road>(this.buildingNum,new Comparator<Road>(){
                public int compare(Road r1, Road r2){
                    if(r1.dToSrc<r2.dToSrc)return -1;
                    else if(r1.dToSrc > r2.dToSrc)return 1;
                    else return 0;
                }
            });
        } 
        
        private void dijkstra(){
            //initialize 
            HashSet<String>visited = new HashSet<>(); // list to keep track with building is already visited         
            
            int roadLength=-1;
            int distanceToSrc = -1;
            String  buildingU = this.srcBuildingName;
            String buildingV;
            //initialize priority check with sourceBuilding, the road has length 0 since it't to itself
            pq.add(new Road(buildingU,buildingU,0));
//            visited.add(buildingU);
            while(visited.size()!=buildingNum){
            
                //exit loop if there is no item in priorityQue
                if(pq.isEmpty() ){
                    break;
                }
                
                //remove  Road with lowest length  from priorityQueue                        
                buildingU=pq.remove().toBuildingName;//pick the road with smallest length
                //if BuildingU is already visited, skip
                if(!visited.contains(buildingU)){
                    System.out.println("removing "+buildingU+" from queue");
                    //add just removed Buidling to visited list 
                    visited.add(buildingU);
                }else {
                    continue;
                }                                       

                //calculate distance of adjacent buildings to building that is just visited
                List<Road> adjBuildings = buildingNameMap.get(buildingU).getAdjacentBuildings();
                for(int i=0;i<adjBuildings.size();i++){
                    Road r = adjBuildings.get(i);
                    buildingV = r.toBuildingName;
                    // only calculate distance of building that is not visited
                    if(!visited.contains(buildingV)){
                        roadLength = r.length;
                        distanceToSrc = distanceMap.get(buildingU) + roadLength; // distance from srcBuilding to current buidling
                        r.updateDistanceToSource(distanceToSrc);
                        //if new calculated distance is smaller than stored distance in distance list, then replace with new value
                        if(distanceToSrc<distanceMap.get(buildingV)){
                            distanceMap.put(buildingV,distanceToSrc);
                            prevBuildingMap.put(buildingV,buildingU);
                        }
                        //add current building to priority queue
                        pq.add(r);                      
                    }
                }
            }//end of while loop
 
        }
        
        private int getShortestLength(String destBuilding){//get shortest distance from src Building to dest Building
            return distanceMap.get(destBuilding);           
        }
        
        private List<String>getBuildingsInSP(String destBuilding){//return list of all buildings that within srcBuilding and DestBuilding
            List<String>buildingList = new ArrayList<>();
            buildingList.add(destBuilding);
            // if destBuilding is srcBuilding, then there is only 1 Building in the return list
            if(destBuilding == srcBuildingName) return buildingList;
            
            String curBuilding = destBuilding;
            String prevBuilding;
            
            while(true){
                prevBuilding = prevBuildingMap.get(curBuilding);
                buildingList.add(prevBuilding);
                if(prevBuilding.equals(this.srcBuildingName))break;
                curBuilding = prevBuilding;
            }
            return buildingList;
        }
        
   }
    
    private class prim_mst{
        private int buildingNum;
        ArrayList<Building> buildings;
        HashSet<String>visited;
        PriorityQueue<Road> minHeap;//priority queue containig all considered Road, sort Road length from min to max
        ArrayList<Road>mst; //list containing Road in minimum spanning tree
        private prim_mst(ArrayList<Building> buildings, HashMap<String,Building>buildingNameMap){
            this.buildingNum = buildings.size();
            this.buildings = buildings;
            mst = new ArrayList<>();
            visited = new HashSet<>();
            minHeap = new PriorityQueue<Road>(this.buildingNum,new Comparator<Road>(){              
                public int compare(Road r1, Road r2){
                    if(r1.length<r2.length)return -1;
                    else if(r1.length > r2.length)return 1;
                    else return 0;
                }
            });
        }
        
        private void mst(){
            //pick the first Building to be the starting point
            String buildingU=buildings.get(0).buildingName;
            while(this.visited.size()!=buildingNum){//while not all Building is added to minimum spanning tree
                if(minHeap.isEmpty() && visited.size()!=0) {
                    break;
                }
                if(visited.contains(buildingU)){
                    //extract min Road from minHeap and add to MST
                    Road minRoad=minHeap.remove();
                    System.out.println("removing "+minRoad.getPath());
                    //if min Road lead to unvisited Building, then add to MST
                    if(!visited.contains(minRoad.getNextBuildingName())) mst.add(minRoad);
                    //current Building is newly discovered Building
                    buildingU=minRoad.getNextBuildingName();
                    
                }else{//if not visited, add to visited list,calculate all Roads from Building u, extract min Road from heap
                    visited.add(buildingU);
                    addRoadsToHeap(buildingNameMap.get(buildingU));
                    //extract min Road from minHeap and add to MST
                    Road minRoad=minHeap.remove();
                    System.out.println("removing "+minRoad.getPath());
                    //if min Road lead to unvisited Building, then add to MST
                    if(!visited.contains(minRoad.getNextBuildingName())) mst.add(minRoad);
                    //current Building is newly discovered Building
                    buildingU=minRoad.getNextBuildingName();
                }
            }
        }
        
        private void addRoadsToHeap(Building current){// find all Road of current Building that lead to other building
            for(Road r: current.getAdjacentBuildings()){
                if(!visited.contains(r.getNextBuildingName())){
                    minHeap.add(r);
                }
            }
        }
        
        private int getMST_cost(){
            return mst.stream().mapToInt(m->m.length).sum();
        }
        
        private String getSpanningTree(){
            StringBuilder st =new StringBuilder();
            for(int i=0;i<mst.size();i++){
                if(i==0){
                    st.append(mst.get(i).fromBuildingName).append("->");
                }
                st.append(mst.get(i).toBuildingName).append("->");
            }
            return st.substring(0, st.length()-2).toString();
        }
    }
    
    public static void main(String[] args) throws Exception {
        Map m = new Map();
        m.addBuilding("A");
        m.addBuilding("C");
        m.addBuilding("B");

        //Add Roads
        m.addRoad("B", "C", 2);
        m.addRoad("B", "A", 4);
        m.addRoad("C", "D", 1);
        m.addRoad("C", "B", 2);
        m.addRoad("D", "A", 3);
        m.addRoad("D", "C", 1);
        m.addRoad("A","E",5);
     
        //testing addRoads()
        ArrayList<String> a_list = new ArrayList<String>();
        a_list.add("E");
        a_list.add("W");
        a_list.add("Z");
        m.addRoads("C", a_list, 15);
        m.printMap();
//        
//        m.removeBuilding("M");
//        m.removeRoad("C", "D");
        m.printMap();
        // find shortest length using dijstra
        int shortestLength = m.shortestLength("B", "E");       
        System.out.println("\n");
        List<String> buildingsInShortestLength = m.shortestPath("B", "E");
        
        int minTotalLength = m.minimumTotalLength();
        int secondShortestLength = m.secondShortestPath("C", "B");
        System.out.println();
    }
}
