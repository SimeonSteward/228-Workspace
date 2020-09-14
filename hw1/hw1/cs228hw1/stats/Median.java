package cs228hw1.stats;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stat Object that finds the median of the data in this stat object
 * @author Simeon Steward
 */
public class Median<T extends Number> extends AbstractStatObject<T> {

    /**
     * Default Constructor
     */
    public Median() {
        super();
    }

    /**
     * A specific constructor that performs a deep copy from the parameter to the data object.
     *
     * @param data The Data
     */
    public Median(ArrayList<T> data) {
        super(data);
    }

    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        ArrayList<Double> data = super.GetDataAsDouble();

        Collections.sort(data);
        ArrayList<Number> Output = new ArrayList<>();
        if (Math.floorMod(data.size(), 2) == 0) {
            //if even adds the average of the two center values
            Output.add(( data.get(data.size() / 2) + data.get(data.size() / 2 - 1) )/ 2);
        } else {
            //if odd adds the center value
            Output.add(data.get((data.size() / 2)));
        }
        return Output;
    }
}
