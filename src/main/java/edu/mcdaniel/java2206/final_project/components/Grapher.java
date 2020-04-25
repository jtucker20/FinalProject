package edu.mcdaniel.java2206.final_project.components;

import com.googlecode.charts4j.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Grapher {

    private List<Double> inputDow;
    private List<Double> dowData;
    private List<Double> inflation;
    private List<Double> convertedDow;
    private List<Double> convertedInfDow;
    private List<Date> dates;
    private List<Date> inflationDates;
    private List<String> axisDates;
    private static final SimpleDateFormat axisDateFormat = new SimpleDateFormat("yyyy-MM");

    private double minVal;
    private double maxVal;
    private double minInfAdjVal;
    private double maxInfAdjVal;

    private double minValGraph;
    private double maxValGraph;
    private List<Double> graphVerAxis;

    public Grapher(List<Double> inputDow,
                   List<Double> inflation,
                   List<Date> dates,
                   List<Date> inflationDates){
        this.inputDow = inputDow;
        this.inflation = inflation;
        this.dates = dates;
        this.inflationDates = inflationDates;
        this.dowData = new ArrayList<>();
        this.convertedDow = new ArrayList<>();
        this.convertedInfDow = new ArrayList<>();
    }


    public void convert(){

        int numOfDates = this.dates.size();
        this.axisDates = new ArrayList<>();
        String first = axisDateFormat.format(this.dates.get(0));
        this.axisDates.add(first);

        //First third
        int firstThird = numOfDates / 3;
        String firstThirdStr = axisDateFormat.format(this.dates.get(firstThird));
        this.axisDates.add(firstThirdStr);

        //Second Third
        int secondThrid = (2 * numOfDates) / 3;
        String secondThirdStr = axisDateFormat.format(this.dates.get(secondThrid));
        this.axisDates.add(secondThirdStr);

        //Final
        int finalPos = this.inputDow.size() - 1;
        String finalStr = axisDateFormat.format(this.dates.get(this.dates.size() - 1));
        this.axisDates.add(finalStr);

        //Find Min value:
        minVal = Collections.min(this.inputDow, null) - 10;
        maxVal = Collections.max(this.inputDow, null) + 10;


        double span = (maxVal - minVal);
        double quarter = span / 4;

        for(double d : inputDow){
            double finalVal = 100 * (d - minVal) / span;
            dowData.add(finalVal);
        }
        System.out.println("_________FINISHED SCALING DOW RAW __________");

        for(int idx = 0; idx < inputDow.size(); idx++){
            Double d  = inputDow.get(idx);
            Date dDate = dates.get(idx);
            Date iDate;
            double i = 1;
            int pos = 0;

            for(Date iPosDate : inflationDates) {
                i = i * (1 + (inflation.get(pos) / 100 /12));
                pos++;

                LocalDate localDateBegin = LocalDate.of(dDate.getYear(),
                        dDate.getMonth() + 1, dDate.getDate());

                LocalDate localDateEnd = LocalDate.of(iPosDate.getYear(),
                        iPosDate.getMonth() + 1, iPosDate.getDate());

                Period lengthOfPeriod = Period.between(localDateBegin, localDateEnd);
                int monthsBetween = lengthOfPeriod.getYears() * 12 + lengthOfPeriod.getMonths();
                if (monthsBetween < 1.5) {

                    iDate = iPosDate;
                    break;
                }
            }
            double convertedValue = d * i;
            convertedDow.add(convertedValue);
        }

        minInfAdjVal = Collections.min(this.convertedDow);
        maxInfAdjVal = Collections.max(this.convertedDow);

        double infSpan = maxInfAdjVal - minInfAdjVal;

        for(double d : convertedDow){
            double finalVal = 100 * (d - minInfAdjVal) / infSpan;
            convertedInfDow.add(finalVal);
        }
        System.out.println("_________FINISHED SCALING DOW INF __________");

        graphVerAxis = new ArrayList<>();

        graphVerAxis.add(minVal); // 0%

        minValGraph = Math.min(minVal, minInfAdjVal);
        maxValGraph = Math.max(maxVal, maxInfAdjVal);

        System.out.println("Min: " + minValGraph);
        System.out.println("Max: " + maxValGraph);

        graphVerAxis.add(minValGraph + quarter); //25%
        graphVerAxis.add(minValGraph + (2 * quarter)); // 50%
        graphVerAxis.add(minValGraph + (3 * quarter)); // 75%
        graphVerAxis.add(maxValGraph); // 100%



    }


    public String drawLineGraph() {

        Data rawDowData = Data.newData(dowData);
        Color rawDowDataColor = Color.newColor("FF471A");
        String rawDowLegend = "Non Adjusted";

        final Plot rawPlot = Plots.newPlot(rawDowData, rawDowDataColor, rawDowLegend);

        Data infDowData = Data.newData(convertedInfDow);
        Color infDowDataColor = Color.newColor("00FF1A");
        String infDowLegend = "Inflation Adjusted";

        final Plot infPlot = Plots.newPlot(infDowData, infDowDataColor, infDowLegend);

        // Chart
        final LineChart chart = GCharts.newLineChart(rawPlot, infPlot);
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(this.axisDates));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericAxisLabels(graphVerAxis));
        chart.setTitle("Inflation Adjusted to 2019-12-31 and Non Adjusted Dow Jones Industrial Average");
        chart.setSize(720, 415);
        return chart.toURLString();
    }
}
