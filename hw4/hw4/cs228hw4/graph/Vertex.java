package cs228hw4.graph;

import java.util.ArrayList;

/**
 * @author SimeonSteward
 * The vertex used to test both cs228DiGraph and CS228Dijkstra
 * Didn't delet in case I need it
 * @param <E> the type this vertex will contain
 */
public class Vertex<E> {
    private ArrayList<Vertex> edges;
    private ArrayList<Integer> edgeWeights;
    private E data;

    /**
     * Creates a new vertex with data of type E inside
     * @param data the data that will be stored in this vertex
     */
    public Vertex(E data) {
        edges = new ArrayList<>();
        edgeWeights=new ArrayList<>();
        this.data = data;
    }

    /**
     * @return the neighbors of this vertex
     */
    public ArrayList<Vertex> getNeighbors(){
        return edges;
    }

    /**
     * Gets the edge weight for the edge going to destination vertex v
     * @param v the destination of this edge
     * @return the edge weight
     */
    public int getEdgeWeight(Vertex v){
        return edgeWeights.get(edges.indexOf(v));
    }

    /**
     * @return the data stored in this vertex
     */
    public E getData(){
        return data;
    }

    /**
     * Adds an edge between this vertex and vertex v. If there is already an edge returns false
     * @param v the vertex that this edge is going to
     * @param edgeWeight the weight of this edge
     * @return true if added an edge, false otherwise
     */
    public boolean addEdge(Vertex v, int edgeWeight){
        int index=edges.indexOf(v);
        if(index!=-1){
            return false;
        }
        edges.add(v);
        edgeWeights.add(edgeWeight);
        return true;
    }

    /**
     * Removes the edge in this vertex going to Vertex v
     * @param v the edge's destination
     * @return has this found and deleted a vertex
     */
    public boolean removeEdge(Vertex v){
        int index = edges.indexOf(v);
        if (index == -1){
            return false;
        }
        edges.remove(index);
        edgeWeights.remove(index);
        return true;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * @returns the list of edge weights that corresponds to the getNeigbors function
     */
    public ArrayList<Integer> getEdgeWeights() {
        return edgeWeights;
    }

    /**
     * @returns the number of neighbors
     */
    public int numNeighbors(){
        return edges.size();
    }
}
