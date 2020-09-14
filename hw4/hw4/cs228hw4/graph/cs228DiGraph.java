package cs228hw4.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author SimeonSteward
 *
 * A graph used to test CS228Dijkstra
 * Didn't delete just in case I needed it
 */
public class cs228DiGraph implements DiGraph<Vertex> {
    private ArrayList<Vertex> Vertices;

    /**
     * Default constructor
     */
    public cs228DiGraph() {
        Vertices = new ArrayList<>();
    }

    /**
     * Adds the vertex v to this graph
     * @param v the vertex that will be added
     */
    public void addVertex(Vertex v){
        if (Vertices.contains(v)){
            Vertices.add(v);
        }

    }

    @Override
    public Set<? extends Vertex> getNeighbors(Vertex vertex) {
        HashSet<Vertex> retVal = new HashSet<>();
        ArrayList<Vertex> neighbors = vertex.getNeighbors();
        for (int i = 0; i < neighbors.size(); i++) {
            retVal.add(neighbors.get(i));
        }

        return retVal;
    }

    @Override
    public int getEdgeCost(Vertex start, Vertex end) {
        return start.getEdgeWeight(end);
    }

    @Override
    public int numVertices() {
        return Vertices.size();
    }

    @Override
    public Iterator<Vertex> iterator() {
        return Vertices.iterator();
    }
}
