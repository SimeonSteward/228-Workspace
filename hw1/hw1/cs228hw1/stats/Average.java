package cs228hw1.stats;

import java.util.ArrayList;

/**
 * Stat Object that finds the average of the data in this stat object
 * @author Simeon Steward
 */
public class Average<T extends Number> extends AbstractStatObject<T> {

    /**
     * Default Constructor for the Average StatObject
     */
    public Average() {
    super();
    }

    /**
     *  A specific constructor that performs a deep copy from the parameter to the data object.
     * @param data The Data
     */
    public Average(ArrayList<T> data){
        super(data);
    }

    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        ArrayList Output = new ArrayList();
        Output.add(super.mean());
        return Output;
    }
}
