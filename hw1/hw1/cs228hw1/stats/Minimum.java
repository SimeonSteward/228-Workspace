package cs228hw1.stats;

import java.util.ArrayList;

/**
 * Stat Object that finds the minimum of the data in this stat object
 * @author Simeon Steward
 */
public class Minimum<T extends Number> extends AbstractStatObject<T> {

    /**
     * Default Constructor
     */
    public Minimum() {
        super();
    }

    /**
     * A specific constructor that performs a deep copy from the parameter to the data object.
     *
     * @param data The Data
     */
    public Minimum(ArrayList<T> data) {
        super(data);
    }

    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        ArrayList<T> data = super.GetData();
        int minIndex = 0;
        for (int i = 0; i <data.size(); i++) {
            if((data.get(i)!= null)&&(data.get(minIndex).doubleValue()>data.get(i).doubleValue())) minIndex=i;
        }
        ArrayList<Number> Output = new ArrayList<>();
        Output.add(data.get(minIndex));
        return Output;
    }
}
