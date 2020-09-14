package cs228hw1.stats;

import java.util.ArrayList;

/**
 * Stat Object that finds the histogram of the data in this stat object
 * @author Simeon Steward
 */
public class Histogram<T extends Number> extends AbstractStatObject<T> {

    private int numberBins;
    private double minRange;
    private double maxRange;

    /**
     * Default Constructor for histogram
     */
    public Histogram() {
        super();
        this.numberBins = 10;
        this.minRange = Double.NEGATIVE_INFINITY;
        this.maxRange = Double.POSITIVE_INFINITY;
    }

    /**
     * Specific Constructor for histogram, sets data inside this histogram object
     */
    public Histogram(ArrayList<T> data) {
        super(data);
        this.numberBins = 10;
        this.minRange = Double.NEGATIVE_INFINITY;
        this.maxRange = Double.POSITIVE_INFINITY;
    }

    @Override
    public ArrayList<Number> GetResult() throws RuntimeException {
        ArrayList<T> data = super.GetData();
        ArrayList<Number> bins = new ArrayList<Number>();

        // calculates the what the various bins are based off of the data an the number of bins
        for (int i = 0; i < numberBins; i++) {
            bins.add(0);
        }

        for (int i = 0; i < data.size(); i++) {
            if((data.get(i)!=null)&&(data.get(i).doubleValue()<maxRange&&data.get(i).doubleValue()>=minRange)) {
                //Finds where the given data input should go
                int binIndex = (int) ((data.get(i).doubleValue() - minRange) / ((maxRange-minRange)/numberBins));
                //Adds 1 to the bin it belongs to
                bins.set(binIndex, bins.get(binIndex).intValue() + 1);
            }else if((data.get(i)!=null)&&data.get(i).doubleValue()==maxRange){
                bins.set(bins.size()-1,bins.get(bins.size()-1).intValue()+1);
            }
        }

        return bins;
    }

    /**
     * Returns the minimum range of the histogram
     */
    public double GetMinRange() {
        return minRange;
    }

    /**
     * Sets the minimum range of the histogram
     */
    public void SetMinRange(Number minRange) {
        this.minRange = minRange.doubleValue();
    }

    /**
     * Returns the maximum range of the histogram
     */
    public double GetMaxRange() {
        return maxRange;
    }

    /**
     *Returns the maximum range of the histogram
     */
    public void SetMaxRange(Number maxRange) {
        this.maxRange = maxRange.doubleValue();
    }

    /**
     * Returns the current number of bins.
     */
    public int GetNumberBins() {
        return numberBins;
    }

    /**
     *Sets the number of equal size bins in the histogram
     */
    public void SetNumberBins(int numberBins) {
        this.numberBins = numberBins;
    }


}
