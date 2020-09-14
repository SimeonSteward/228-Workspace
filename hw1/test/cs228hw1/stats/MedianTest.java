package cs228hw1.stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



class MedianTest {
    private Median<Integer> medianInt;
    private Median<Double> medianDouble;

    public MedianTest() {
    }
    @BeforeEach
    void setUp() {
        medianInt = new Median<Integer>();
        medianDouble = new Median<Double>();
        medianDouble.SetData(new ArrayList<>(List.of(1.2,2.3,4.2,4.5,5.6)));
    }

    @Test
    void DescriptionTests() {
        medianInt.SetDescription("Median Integer");
        assertEquals("Median Integer",medianInt.GetDescription());
    }


    @Test
    void DataTests() {
        medianDouble.SetData(new ArrayList<>(List.of(1.2,2.3,4.2,4.5,5.6)));
        assertEquals(List.of(1.2,2.3,4.2,4.5,5.6).toString(),medianDouble.GetData().toString());
        medianDouble.SetData(new ArrayList<Double>(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6)));
        assertEquals(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6).toString(),medianDouble.GetData().toString());

    }

    @Test
    void getResult() {
        medianDouble.SetData(new ArrayList<Double>(List.<Double>of(2.0,3.0,2.3,4.2,4.0,5.0,5.6)));
        assertEquals(4.2,medianDouble.GetResult().get(0));
    }
}