package edu.mcdaniel.java2206.final_project.components;

import com.googlecode.charts4j.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Grapher {

    private List<Double> inputDow;
    private List<Double> inflation;
    private List<Number> convertedDow;
    private List<Date> dates;
    private List<String> axisDates;
    private static final SimpleDateFormat axisDateFormat = new SimpleDateFormat("yyyy-MM");

    public Grapher(List<Double> inputDow, List<Double> inflation, List<Date> dates){
        this.inputDow = inputDow;
        this.inflation = inflation;
        this.dates = dates;
    }


    public void convert(){

        Date beginDate = dates.get(0);
        Date endDate = dates.get(dates.size());

        LocalDate localDateBegin = LocalDate.ofEpochDay(beginDate.getTime());
        LocalDate localDateEnd = LocalDate.ofEpochDay(endDate.getTime());

        Period lengthOfPeriod = Period.between(localDateBegin, localDateEnd);
        int monthsBetween = lengthOfPeriod.getYears() * 12 + lengthOfPeriod.getMonths();
//        int beginMonth = beginDate.getMonth();
//        int beginYear = beginDate.getYear();
//        int endMonth = endDate.getMonth();
//        int endYear = endDate.getYear();

        this.axisDates = new ArrayList<>();
        String first = axisDateFormat.format(this.dates.get(0));
        this.axisDates.add(first);

        //First third
        int firstThird = monthsBetween / 3;
        String firstThirdStr = axisDateFormat.format(this.dates.get(firstThird));
        this.axisDates.add(firstThirdStr);

        //Second Third
        int secondThrid = (2 * monthsBetween) / 3;
        String secondThirdStr = axisDateFormat.format(this.dates.get(secondThrid));
        this.axisDates.add(secondThirdStr);

        //Final
        String finalStr = axisDateFormat.format(this.dates.get(this.dates.size()));
        this.axisDates.add(finalStr);

    }


    public String drawLineGraph() {


        // Your really great chart.
        final Plot plot = Plots.newPlot(Data.newData(0, 10.6, 20.5, 80.20, 50.50, 95.5, 92.00));
        final Plot plot1 = Plots.newPlot(Data.newData(0,20.6, 40.6, 90, 100, 130));
        final LineChart chart = GCharts.newLineChart(plot, plot1);
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(
                Arrays.asList("2010-2011", "2011-2013", "2013-present"), Arrays.asList(10.6, 50.00, 80.00)));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericAxisLabels(0, 20.00, 40.00, 60.00, 80.00, 90.00, 100.00));
        chart.setTitle("Growth of Alibata System Inc. (Estimated Plot)");
        chart.setSize(720, 360);
        return chart.toURLString();
    }
}
