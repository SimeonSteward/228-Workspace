package cs228hw4.graph;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CS228DijkstraTest {

    @Test
    void run() {
        cs228DiGraph graph = new cs228DiGraph();
        Vertex d1 = new Vertex<>(1);
        Vertex d2 = new Vertex<>(1);
        graph.addVertex(d1);
        graph.addVertex(d2);
        d1.addEdge(d2,3);
        CS228Dijkstra<Vertex> dijkstra = new CS228Dijkstra<>(graph);
        dijkstra.run(d1);
        assertEquals(3,dijkstra.getShortestDistance(d2));

        Vertex d3 = new Vertex<>(1);
        graph.addVertex(d3);
        d2.addEdge(d3,1);
        d1.addEdge(d3,5);
        dijkstra.run(d1);
        assertEquals(4,dijkstra.getShortestDistance(d3));

        //Example shown on L32P1
        graph = new cs228DiGraph();
        d1 = new Vertex<>(1);
        d2 = new Vertex<>(2);
        d3 = new Vertex<>(3);
        Vertex d4 = new Vertex<>(4);
        Vertex d5 = new Vertex<>(5);
        graph.addVertex(d1);
        graph.addVertex(d2);
        graph.addVertex(d3);
        graph.addVertex(d4);
        graph.addVertex(d5);

        d1.addEdge(d2,1);
        d1.addEdge(d5,2);
        d1.addEdge(d3,3);

        d2.addEdge(d3,1);
        d3.addEdge(d4,1);
        d3.addEdge(d5,3);
        d4.addEdge(d5,5);

        dijkstra = new CS228Dijkstra<>(graph);

        dijkstra.run(d1);

        assertEquals(1,dijkstra.getShortestDistance(d2));
        assertEquals(2,dijkstra.getShortestDistance(d3));
        assertEquals(3,dijkstra.getShortestDistance(d4));
        assertEquals(2,dijkstra.getShortestDistance(d5));

        int[] arr = new int[4];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        arr[3] = 4;
        assertEquals(Arrays.toString(arr),Arrays.toString(dijkstra.getShortestPath(d4).toArray()));

        dijkstra.run(d2);

        assertEquals(Integer.MAX_VALUE,dijkstra.getShortestDistance(d1));
        assertEquals(0,dijkstra.getShortestDistance(d2));



    }

    @Test
    void getShortestPath() {
    }

    @Test
    void getShortestDistance() {
    }
}