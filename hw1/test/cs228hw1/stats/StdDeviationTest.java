package cs228hw1.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StdDeviationTest {
    private StdDeviation<Integer> stdDevInt;
    private StdDeviation<Double> stdDevDouble;
    private StdDeviation stdDevNull;

    public StdDeviationTest() {

    }
    @BeforeEach
    void setUp() {
        stdDevInt = new StdDeviation<Integer>();
        stdDevDouble = new StdDeviation<Double>();
        stdDevDouble.SetData(new ArrayList<>(List.of(1.2,2.3,4.2,4.5,5.6)));
        stdDevNull = new StdDeviation<>();
    }

    @Test
    void DescriptionTests() {
        stdDevInt.SetDescription("Median Integer");
        assertEquals("Median Integer",stdDevInt.GetDescription());
    }


    @Test
    void DataTests() {
        stdDevDouble.SetData(new ArrayList<>(List.of(1.2,2.3,4.2,4.5,5.6)));
        assertEquals(List.of(1.2,2.3,4.2,4.5,5.6).toString(), stdDevDouble.GetData().toString());
        stdDevDouble.SetData(new ArrayList<Double>(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6)));
        assertEquals(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6).toString(), stdDevDouble.GetData().toString());

    }

    @Test
    void getResult() {
        stdDevDouble.SetData(new ArrayList<Double>(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6)));
        assertEquals(1.252100276352, (Double) stdDevDouble.GetResult().get(0), .01);
        String d = "Empty or null data";
        assertThrows(RuntimeException.class, () -> { stdDevNull.GetResult(); }, d);
    }
}