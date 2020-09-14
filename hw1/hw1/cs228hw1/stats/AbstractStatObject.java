package cs228hw1.stats;

import java.util.ArrayList;

/**
 * At abstract class for the stat object interface
 * @author Simeon Steward
 */
public abstract class AbstractStatObject<T extends Number> implements StatObject<T> {
    private String description;
    private ArrayList<T> data = new ArrayList<>();

    /**
     * Constructor for this Abstract Stat Object
     */
    public AbstractStatObject() {
    }

    /**
     * Constructor for this Abstract Stat Object with data parameter
     * @param data that will be used by this stat object
     */
    public AbstractStatObject(ArrayList<T> data){
        SetData(data);
    }


    @Override
    public void SetDescription(String d) {
        description = d;
    }

    @Override
    public String GetDescription() {
        return description;
    }

    @Override
    public void SetData(ArrayList<T> data) {
        this.data.clear();
        for (int i = 0; i < data.size(); i++) {
            this.data.add(i,data.get(i));
        }
    }

    @Override
    public ArrayList<T> GetData() {
        ArrayList<T> dataOut = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            dataOut.add(i, data.get(i));
        }
        return dataOut;
    }

    /**
     * Returns a deep copy of the data used in the calculations as ArrayList<Double> for this cs228hw1.stats.StatObject.
     */
    protected ArrayList<Double> GetDataAsDouble() {
        ArrayList<Double> dataOut = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i)!=null)  dataOut.add(data.get(i).doubleValue());
        }
        return dataOut;
    }

    /**
     * Returns the sum of the data in the arrayList data as a double
     */
    protected double sum(){
        double sum = 0;
        for (int i = 0; i <data.size(); i++) {
           if(data.get(i)!= null) sum = (sum + data.get(i).doubleValue());
        }
        return sum;
    }

    /**
     * Returns the sum of the data passed in as a double
     */
    protected static double sum(ArrayList<Number> inputData){
        double sum = 0;
        for (int i = 0; i <inputData.size(); i++) {
            sum = (sum + inputData.get(i).doubleValue());
        }
        return sum;
    }

    /**
     *Returns the mean of the data in the arrayList data as a double
     */
    protected double mean() {
        double sum = this.sum();
        return (sum / data.size());
    }

    /**
     *Returns the size of the data in the arrayList data as a int
     */
    protected int size() {
        return (data.size());
    }
}
