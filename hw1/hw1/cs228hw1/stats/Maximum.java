package cs228hw1.stats;

import java.util.ArrayList;

/**
 * Stat Object that finds the maximum of the data in this stat object
 * @author Simeon Steward
 */
public class Maximum<T extends Number> extends AbstractStatObject<T> {

    /**
     * Default Constructor
     */
    public Maximum() {
        super();
    }

    /**
     * A specific constructor that performs a deep copy from the parameter to the data object.
     *
     * @param data The Data
     */
    public Maximum(ArrayList<T> data) {
        super(data);
    }

    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        ArrayList<T> data = super.GetData();
        int maxIndex = 0;
        for (int i = 0; i <data.size(); i++) {
            if((data.get(i)!=null)&&(data.get(maxIndex).doubleValue()<data.get(i).doubleValue())) maxIndex=i;
        }
        ArrayList<Number> Output = new ArrayList<>();
        Output.add(data.get(maxIndex));
        return Output;
    }
}
