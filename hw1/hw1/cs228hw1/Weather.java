package cs228hw1;

import cs228hw1.stats.*;
import cs228hw1.stats.parsers.*;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *Reads the imputed file and gives the
 * The monthly high and low temp
 * Average temperature for each day
 * Median temperature for each day
 * Histogram for each day with 15 bins ranging from -40 to 110 degrees
 * @author Simeon Steward
 */
public class Weather {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        doubleParser parser= new doubleParser();
        monthParser monParser = new monthParser();
        dayParser dayParser = new dayParser();
        yearParser yearParser = new yearParser();
        StatisticsShell<Double> myShell = new StatisticsShell<>(parser);
        // StatisticsShell<Integer> myMonthShell = new StatisticsShell<>(monParser);
        StatisticsShell<Integer> myDateShell = new StatisticsShell<>(new dateParser());

        //All of the data from the file, order is important
        ArrayList<Double> data;
        //All of the data point's date value in the form given in the document
        ArrayList<Integer> dates;
        //Data for each month separated into 12 different ArrayLists
        ArrayList<ArrayList<Pair<Double, Integer>>> monthlyData;

        System.out.println("Enter file name:");
        String fileName = scan.nextLine();
        scan.close();

        myShell.ReadFile(fileName, Statistics.DATA.TEMP);
        data = myShell.GetDataSet();

       // myMonthShell.ReadFile(fileName, Statistics.DATA.YR_MO_DA_HR_MN);
       // months = myMonthShell.GetDataSet();
        myDateShell.ReadFile(fileName, Statistics.DATA.YR_MO_DA_HR_MN);
        dates = myDateShell.GetDataSet();

        ArrayList<Pair<Double, Integer>> dataPoints = pairify(data,dates);
        monthlyData = mapDataToMonths(dataPoints, monParser);
        myShell.AddStatObject(new Minimum<>());
        myShell.AddStatObject(new Maximum<>());
        for (int i = 0; i < monthlyData.size(); i++) {
            myShell.setDataSet(getDataList(monthlyData.get(i)));
            System.out.println("For " +new DateFormatSymbols().getMonths()[i%12]+ " "+yearParser.parse(getDatesList(monthlyData.get(i)).get(0).toString())+": Min: " + myShell.GetStatObject(0).GetResult() + " Max: " + myShell.GetStatObject(1).GetResult());
        }
        System.out.println();

        myShell.removeAllStatObjects();
        myShell.AddStatObject(new Average<>());
        myShell.AddStatObject(new Median<>());
        myShell.AddStatObject(new Maximum<>());
        ArrayList<Double> maximums = new ArrayList<>();
        for (int i = 0; i < monthlyData.size(); i++) {
            ArrayList<ArrayList<Pair<Double, Integer>>> dailyData = mapDataToMonths(monthlyData.get(i),dayParser);
            for (int j = 0; j < dailyData.size(); j++) {
                ArrayList<Double> todayData = getDataList(dailyData.get(j));
                Integer todayDate = getDatesList(dailyData.get(j)).get(0);

                myShell.setDataSet(todayData);

                int year = yearParser.parse(todayDate.toString());
                String month = new DateFormatSymbols().getMonths()[monParser.parse(todayDate.toString())-1];
                int day = dayParser.parse(todayDate.toString());
                maximums.add(myShell.GetStatObject(2).GetResult().get(0).doubleValue());
                System.out.println("For " +month+" "+ day +" "+year+": Average: " + myShell.GetStatObject(0).GetResult() + " Median: " + myShell.GetStatObject(1).GetResult());
            }
        }
        myShell.setDataSet(maximums);
        myShell.removeAllStatObjects();
        Histogram<Double> maximumHist = new Histogram<>();
        maximumHist.SetNumberBins(15);
        maximumHist.SetMaxRange(110);
        maximumHist.SetMinRange(-40);
        myShell.AddStatObject(maximumHist);
        System.out.println();
        System.out.println("Maximum Temperature Histogram:");
        int binSize = (int) ((maximumHist.GetMaxRange()-maximumHist.GetMinRange())/maximumHist.GetNumberBins());
        for (int i = 0; i < maximumHist.GetNumberBins(); i++) {
            System.out.println("("+(maximumHist.GetMinRange()+i*(binSize))+","+(maximumHist.GetMinRange()+(i+1)*(binSize))+") Degrees:"+myShell.GetStatObject(0).GetResult().get(i));
        }

    }


    /**
     * Private method that pairs data with it's corresponding date
     * @param data the given data that will be paired
     * @param dates the given dates that will be paired
     * @return an ArrayList of pairs containing the data and dates of corresponding data points
     */
    private static ArrayList<Pair<Double, Integer>> pairify(ArrayList<Double> data, ArrayList<Integer> dates) {
        ArrayList<Pair<Double,Integer>> dataDates= new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            dataDates.add(new Pair<Double, Integer>(data.get(i),dates.get(i)));
        }
        return dataDates;
    }

    /**
     *Takes an arraylist of Pair<Double, Integer> and takes the double values out
     * @param dataPoints the imputed data
     * @returns an Arraylist with the double values from the imputed data
     */
    private static ArrayList<Double> getDataList(ArrayList<Pair<Double, Integer>> dataPoints) {
        ArrayList<Double> data= new ArrayList<>();
        for (Pair<Double, Integer> dataPoint : dataPoints) {
            data.add(dataPoint.getKey());
        }
        return data;
    }

    /**
     *Takes an ArrayList of Pair<Double, Integer> and takes the integer values out
     * @param dataPoints the imputed data
     * @returns an ArrayList with the double values from the imputed data
     */
    private static ArrayList<Integer> getDatesList(ArrayList<Pair<Double, Integer>> dataPoints) {
        ArrayList<Integer> dates= new ArrayList<>();
        for (Pair<Double, Integer> dataPoint : dataPoints) {
            dates.add(dataPoint.getValue());
        }
        return dates;
    }


    /**
     * Makes an Separate ArrayLists based upon the the parsed dates
     * @param dataPoints The ArrayLists to be put into the Pair ArrayList
     * @param parser The
     * @return
     */
    private static ArrayList<ArrayList<Pair<Double, Integer>>> mapDataToMonths(ArrayList<Pair<Double, Integer>> dataPoints, IParser<Integer> parser){
        int currentMonth = 1;
        ArrayList<Pair<Double, Integer>> individualMonthData= new ArrayList<>();
        ArrayList<ArrayList<Pair<Double, Integer>>> monthlyData = new ArrayList<>();
        for (int i = 0; i < dataPoints.size(); i++) {
            if (currentMonth != parser.parse(dataPoints.get(i).getValue().toString())){
                currentMonth = parser.parse(dataPoints.get(i).getValue().toString());
                monthlyData.add(individualMonthData);
                individualMonthData = new ArrayList<>();
            }
            individualMonthData.add(dataPoints.get(i));
        }
        monthlyData.add(individualMonthData);
        return monthlyData;
    }

}
