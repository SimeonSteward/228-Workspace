package cs228hw1.stats;

import java.util.ArrayList;

/**
 * Stat Object that finds the standard of deviation of the data in this stat object
 * @author Simeon Steward
 */
public class StdDeviation<T extends Number> extends AbstractStatObject {


    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        // uses formula sqrt((sum((deviation from mean)^2)/sum of data points))
        ArrayList<Double> data = super.GetDataAsDouble();
        double sumOfDeviationsSquared = 0;
        for (Double datum : data) {
            double mean = super.mean();//for testing
            double dataMinusMean = datum - mean;//for testing
            double deviationSquared = Math.pow(datum - mean, 2);
            sumOfDeviationsSquared += Math.pow(datum - super.mean(), 2);
        }
        ArrayList<Number> output = new ArrayList();
        output.add(Math.sqrt(sumOfDeviationsSquared/(super.size()-1)));
        return output;
    }
}
