package cs228hw4.graph;

import java.util.*;

/**
 * A class which implements Dijkstra<V>
 * @author Simeon Steward
 * @param <V> The vertex that is used in the graph that this dijkstra is performed on
 */
public class CS228Dijkstra<V> implements Dijkstra<V> {

    /**
     * A vertex that has a weight and can be compared
     * @param <V> the vertex type
     */
    public static class WeightedVertex<V> implements Comparable {
        private V vertex;
        private V previous;
        private int weight;

        /**
         * A public constructor that defines the vertex, the previous vertex and the weight of this vertex
         * @param vertex the vertex
         * @param previous the vertex that was used to get here
         * @param weight the weight it took to get here
         */
        public WeightedVertex(V vertex,V previous, int weight) {
            this.vertex = vertex;
            this.weight = weight;
            this.previous = previous;
        }

        @Override
        public int compareTo(Object o) {
            return this.getWeight()-((WeightedVertex) o).getWeight();
        }

        /**
         * @return the vertex contained in this vertex
         */
        public V getVertex() {
            return vertex;
        }

        /**
         * @return The weight of this vertex
         */
        public int getWeight() {
            return weight;
        }
    }

    private DiGraph<V> graph;
    private ArrayList<V> vertices;
    private ArrayList<V> previous;
    private Hashtable<V,Integer> timeToReach;

    /**
     * A dijkstra class which performs th dijkstra algorithm on the given graph
     * @param graph the graph this dijkstra will be performed on
     */
    public CS228Dijkstra(DiGraph<V> graph){
        this.graph = graph;
        timeToReach = new Hashtable<>();
        vertices = new ArrayList<>();
        previous = new ArrayList<>();

    }

    @Override
    public void run(V start) {

        timeToReach = new Hashtable<>();
        vertices = new ArrayList<>();
        previous = new ArrayList<>();

        PriorityQueue<WeightedVertex<V>> priorityQueue = new PriorityQueue<>();
        V vertex = start;
        int timeToGetHere = 0;
        priorityQueue.add(new WeightedVertex<>(vertex,null, 0));
        while(vertex!=null) {

            ArrayList<V> neighbors = new ArrayList<>(graph.getNeighbors(vertex));
            //Adds all the neighbors that aren't already done
            for (V neighbor : neighbors) {
                if(!vertices.contains(neighbor)) {
                    priorityQueue.add(new WeightedVertex<>(neighbor,vertex, graph.getEdgeCost(vertex, neighbor) + timeToGetHere));
                }
            }

            //Takes the next shortest vertex
            WeightedVertex<V> temp = priorityQueue.poll();
            //If priority queue is empty, breaks the loop
            if (temp==null) break;

            timeToGetHere=temp.getWeight();
            vertex=temp.getVertex();
            //If not in the list of the analyzed vertices, adds it
            if (!vertices.contains(vertex)) {
                vertices.add(temp.getVertex());
                timeToReach.put(temp.getVertex(),timeToGetHere);
                previous.add(temp.previous);
            }
            //If a shorter path is found, replaces the timeToReach with the timeToGetHere
            else if ((timeToReach.get(vertex)>temp.getWeight()+timeToGetHere)) {
                timeToReach.put(vertex,timeToGetHere);
                previous.set(vertices.indexOf(vertex),temp.previous);
            }
            //Priority Queue with elements containing a vertex and a weight

        }
    }

    @Override
    public List<V> getShortestPath(V vertex) {
        ArrayList<V> path = new ArrayList<>();
        //What is the expected value for when the path is not found for the vertex
        int index = vertices.indexOf(vertex);
        path.add(vertex);
        while(index>0){
            index=vertices.indexOf(previous.get(index));
            path.add(0,vertices.get(index));
        }
        return path;
    }

    @Override
    public int getShortestDistance(V vertex) {
        if (!timeToReach.containsKey(vertex)){
            return Integer.MAX_VALUE;
        }
        return timeToReach.get(vertex);
    }
}
