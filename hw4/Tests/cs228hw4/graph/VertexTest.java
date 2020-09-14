package cs228hw4.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    @Test
    void getDataTest() {
        Integer data = (int)(Math.random()*1000);
        Vertex<Integer> d1 = new Vertex<>(data);
        assertEquals(data, d1.getData());
    }

    @Test
    void getNeighborsTest() {
        Vertex<Integer> d1 = new Vertex<>(1);
        Vertex<Integer> d2 = new Vertex<>(1);
        d1.addEdge(d2,1);
        ArrayList<Vertex<Integer>> bob = new ArrayList<Vertex<Integer>>();
        bob.add(d2);
        assertEquals(d1.getNeighbors(),bob);

        d1.getEdgeWeight(d2);
    }
}