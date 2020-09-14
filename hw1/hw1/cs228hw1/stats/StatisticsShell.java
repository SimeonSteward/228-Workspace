package cs228hw1.stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Implements the Statistics interface
 * @author Simeon Steward
 */
public class StatisticsShell<T extends Number> implements Statistics<T>{
    private ArrayList<StatObject<T>> statObjectList = new ArrayList<>();
    private ArrayList<T> data = new ArrayList<>();
    private IParser<T> parser;

    /**
     * Constructs a statistics shell object with the given parser of type T
     * @param parser used to parse the given data
     */
    public StatisticsShell(IParser<T> parser) {
        this.parser=parser;
    }

    @Override
    public boolean ReadFile(String path, DATA d) {
        File file = new File(path);
        try{
        Scanner scan = new Scanner(file);
        int columnNum = d.ordinal() ;
        scan.nextLine();
        while(scan.hasNextLine()){
            Scanner lineScan = new Scanner(scan.nextLine());
            for (int i = 0; i < columnNum; i++) {
                lineScan.next();
            }
            if (lineScan.hasNextDouble()){
            String str = lineScan.next();
            data.add(parser.parse(str));
            } else {
                data.add(null);
            }

            lineScan.close();
        }
        scan.close();
        updateStatObjectData();
        return true;
        }catch (FileNotFoundException e) {
            return false;
        }
    }


    /**
     * Updates the data set for all the stat objects in this Statistics Shell
     */
    private void updateStatObjectData() {
        for (StatObject<T> statObject : statObjectList) {
            statObject.SetData(data);
        }
    }

    @Override
    public void AddStatObject(StatObject<T> so) {
        statObjectList.add(so);
        so.SetData(data);
    }

    @Override
    public void AddStatObject(StatObject<T> so, int first, int last) {
        statObjectList.add(so);
        so.SetData((ArrayList<T>) data.subList(first,last));
    }

    @Override
    public StatObject<T> GetStatObject(int i) {
        try {
            return statObjectList.get(i);
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    @Override
    public StatObject<T> RemoveStatObject(int i) {
        try {
            StatObject<T> temp = statObjectList.get(i);
            statObjectList.remove(i);
            return temp;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    /**
     * Removes all stat objects from the statObjectList
     */
    public void removeAllStatObjects(){
        while (statObjectList.size()!=0){
            RemoveStatObject(0);
        }
    }

    @Override
    public int Count() {
        return statObjectList.size();
    }

    /**
     * Sets the DataSet from the given ArrayList of data
     */
    public void setDataSet(ArrayList<T> newDataSet) {
        data.clear();
        for (int i = 0; i < newDataSet.size(); i++) {
            data.add(i, newDataSet.get(i));
        }
        updateStatObjectData();
    }

    @Override
    public ArrayList<T> GetDataSet() {
        ArrayList<T> dataOut = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            dataOut.add(i, data.get(i));
        }
        return dataOut;
    }

    @Override
    public ArrayList<ArrayList<Number>> MapCar() {
        ArrayList<ArrayList<Number>> results = new ArrayList<>();
        for (StatObject<T> tStatObject : statObjectList) {
            results.add(tStatObject.GetResult());
        }
        return results;
    }
}
