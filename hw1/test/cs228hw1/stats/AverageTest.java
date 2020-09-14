package cs228hw1.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageTest {
    private ArrayList<Integer> arr = new ArrayList<Integer>();
    private Average average = new Average();
    private Object RuntimeException;
    private Average nullAverage = new Average();

    @BeforeEach
    void setUp() {
        nullAverage.SetData(arr);
        arr.add(1); arr.add(2); arr.add(4); arr.add(2); arr.add(9); arr.add(3);
        average.SetData(arr);

    }

    @Test
    void getResults() {
        assertEquals((1+2+4+2+9+3)/6.0,average.GetResult().get(0));
    }

    @Test
    void nullTest() {
        assertEquals(1,nullAverage.GetResult());
    }
}