package cs228hw1.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistogramTest {
    private Histogram histogramInt;
    private Histogram histogramDouble;

    public HistogramTest() {
    }
    @BeforeEach
    void setUp() {
        histogramInt = new Histogram();
        histogramDouble = new Histogram();
        ArrayList<Double> s = new ArrayList<>();
        Collections.addAll(s, 10.0,3.0,2.0,1.0,5.0,-1.0,3.0,4.0,7.0);
        ArrayList<Integer> t = new ArrayList<>();
        Collections.addAll(t, 10, 3, 2, 1, 5, -1, 3, 4, 7);

        histogramDouble.SetData(s);
        histogramInt.SetData(t);
        histogramDouble.SetNumberBins(3);
        histogramDouble.SetMaxRange(5);
        histogramDouble.SetMinRange(2);
    }


    @Test
    void DescriptionTests() {
        histogramDouble.SetDescription("Histogram Double");
        assertEquals("Histogram Double",histogramDouble.GetDescription());
    }

    @Test
    void getResult() {
        assertEquals(List.of(1,2,2).toString(),histogramDouble.GetResult().toString());

    }

    @Test
    void getResultNull() {
        ArrayList<Double> s = new ArrayList<>();
        Collections.addAll(s, 10.0,3.0,2.0,1.0,5.0,-1.0,3.0,4.0,7.0,null);
        histogramDouble.SetData(s);
        assertEquals(List.of(1,2,2),histogramDouble.GetResult());
    }

    @Test
    void getInfinityResult() {
        assertEquals(List.of(1,2,2).toString(),histogramInt.GetResult().toString());

    }


}