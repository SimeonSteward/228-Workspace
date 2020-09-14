package cs228hw4.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class cs228DiGraphTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void addVertex() {
        cs228DiGraph graph = new cs228DiGraph();
       // graph.addVertex(1,new ArrayList<Vertex>(),new ArrayList<Integer>());
        assert(graph.numVertices() == 1);
      //  assert (graph.getNeighbors().isEmpty());
    }

    @Test
    void getNeighbors() {
    }

    @Test
    void getEdgeCost() {
    }

    @Test
    void numVertices() {
    }

    @Test
    void iterator() {
    }
}