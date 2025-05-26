import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.*;
import java.util.*;

public class Main {

    public static void sortsShowAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Insertion Sort", doubleX, yAxis[0]);
        chart.addSeries("Merge Sort", doubleX, yAxis[1]);
        chart.addSeries("Counting Sort", doubleX, yAxis[2]);


        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public static void searchShowAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Linear Search for Random Data", doubleX, yAxis[0]);
        chart.addSeries("Linear Search for Sorted Data", doubleX, yAxis[1]);
        chart.addSeries("Binary Search for Sorted Data", doubleX, yAxis[2]);



        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public static void insertionTimeCalculator(Integer[] array, double[][] randomSortsTimes, int[] sizes){
        for (int i = 0; i < sizes.length; i++){
            long time1, time2;

            double totalTime = 0;
            for (int c = 0; c < 10; c++){

                Integer[] targetArray = Arrays.copyOfRange(array, 0, sizes[i]);

                time1 = System.currentTimeMillis();
                Sorts.insertionSort(targetArray);
                time2 = System.currentTimeMillis();

                double timeTaken = (time2 - time1);
                totalTime += timeTaken;

            }
            randomSortsTimes[0][i] = totalTime / 10;
        }
    }

    public static void mergeTimeCalculator(Integer[] array, double[][] randomSortsTimes, int[] sizes){
        for (int i = 0; i < sizes.length; i++){
            long time1, time2;

            double totalTime = 0;
            for (int c = 0; c < 10; c++){

                Integer[] targetArray = Arrays.copyOfRange(array, 0, sizes[i]);

                time1 = System.currentTimeMillis();
                Sorts.mergeSort(targetArray);
                time2 = System.currentTimeMillis();

                double timeTaken = (time2 - time1);
                totalTime += timeTaken;

            }
            randomSortsTimes[1][i] = totalTime / 10;

        }
    }

    public static void countingTimeCalculator(Integer[] array, double[][] sortsTimes, int[] sizes){
        for (int i = 0; i < sizes.length; i++){
            long time1, time2;

            double totalTime = 0;
            for (int c = 0; c < 10; c++){

                Integer[] targetArray = Arrays.copyOfRange(array, 0, sizes[i]);
                time1 = System.currentTimeMillis();
                Integer maxK = Sorts.findMax(targetArray);
                Sorts.countingSort(targetArray, maxK);
                time2 = System.currentTimeMillis();

                double timeTaken = (time2 - time1);
                totalTime += timeTaken;

            }
            sortsTimes[2][i] = totalTime / 10;
        }
    }

    public static void linearTimeCalculator(Integer[] array, double[][] searchTimes, int[] sizes, int sortedOrNot){
        Random random = new Random();

        for (int i = 0; i < sizes.length; i++){
            long time1, time2;

            double totalTime = 0;
            for (int c = 0; c < 1000; c++){
                int randomIndex = random.nextInt(sizes[i]);

                Integer[] targetArray = Arrays.copyOfRange(array, 0, sizes[i]);

                int randomNumber = targetArray[randomIndex];

                time1 = System.nanoTime();
                Searches.linearSearch(targetArray, randomNumber);
                time2 = System.nanoTime();

                double timeTaken = (time2 - time1);
                totalTime += timeTaken;

            }
            searchTimes[sortedOrNot][i] = totalTime / 1000;
        }
    }

    public static void binaryTimeCalculator(Integer[] array, double[][] searchTimes, int[] sizes){
        Random random = new Random();

        for (int i = 0; i < sizes.length; i++){
            long time1, time2;

            double totalTime = 0;
            for (int c = 0; c < 1000; c++){
                int randomIndex = random.nextInt(sizes[i]);

                Integer[] targetArray = Arrays.copyOfRange(array, 0, sizes[i]);

                int randomNumber = targetArray[randomIndex];

                time1 = System.nanoTime();
                Searches.binarySearch(targetArray, randomNumber);
                time2 = System.nanoTime();

                double timeTaken = (time2 - time1);
                totalTime += timeTaken;

            }
            searchTimes[2][i] = totalTime / 1000;
        }
    }

    public static void printTable(double[][] times, int[] sizes , int sortOrNot , String status){
        System.out.print("\t\t");
        for (int i = 0; i < sizes.length; i++){
            System.out.print("\t"+ sizes[i]);
        }
        System.out.println();
        if (sortOrNot == 1){
            String insertionSortInfo = status + " Insertion Sort: ";
            String mergeSortInfo = status + " Merge Sort: ";
            String countingSortInfo = status + " Counting Sort: ";

            for (int i = 0; i < sizes.length; i++){
                insertionSortInfo += "\t\t" + times[0][i];
                mergeSortInfo += "\t\t" + times[1][i];
                countingSortInfo += "\t\t" + times[2][i];

                /*
                System.out.println(status +" Insertion Sort: " + times[0][i] + " for size: " + sizes[i]);
                System.out.println(status +" Merge Sort: " + times[1][i] + " for size: " + sizes[i]);
                System.out.println(status +" Counting Sort: " + times[2][i] + " for size: " + sizes[i]);
                */

            }
            System.out.println(insertionSortInfo);
            System.out.println(mergeSortInfo);
            System.out.println(countingSortInfo);
        }


        else {
            String randomLinearSearchData = "Random Linear Search: ";
            String sortedLinearSearchData = "Sorted Linear Search: ";
            String sortedBinarySearchData = "Sorted Binary Search: ";

            for (int i = 0; i < sizes.length; i++){

                randomLinearSearchData += "\t" + times[0][i];
                sortedLinearSearchData += "\t" + times[1][i];
                sortedBinarySearchData += "\t" + times[2][i];
                /*
                System.out.println("Average Time taken for Random Linear: " + times[0][i] + " for size: " + sizes[i]);
                System.out.println("Average Time taken for Sorted Linear : " + times[1][i] + " for size: " + sizes[i]);
                System.out.println("Average Time taken for Sorted Binary: " + times[2][i] + " for size: " + sizes[i]);
                */

            }

            System.out.println(randomLinearSearchData);
            System.out.println(sortedLinearSearchData);
            System.out.println(sortedBinarySearchData);


        }
    }

    public static void main(String[] args) throws IOException {

        int[] sizes = {500, 1000, 2000, 4000, 8000, 16_000, 32_000, 64_000, 128_000, 250_000};
        Integer[] array = FileFunctions.getData(args[0]);

        //double[] times = new double[sizes.length];
        double[][] sortsTimes = new double[3][10];
        double[][] searchTimes = new double[3][10];






        // Random
        insertionTimeCalculator(array ,sortsTimes ,sizes);
        mergeTimeCalculator(array ,sortsTimes ,sizes);
        countingTimeCalculator(array ,sortsTimes ,sizes);

        linearTimeCalculator(array,searchTimes,sizes, 0);

        sortsShowAndSaveChart("Sort Tests on Random Data",sizes ,sortsTimes);

        printTable(sortsTimes,sizes,1,"Random");

        // Sort
        Arrays.sort(array);

        insertionTimeCalculator(array ,sortsTimes ,sizes);
        mergeTimeCalculator(array ,sortsTimes ,sizes);
        countingTimeCalculator(array ,sortsTimes ,sizes);

        linearTimeCalculator(array,searchTimes,sizes, 1);
        binaryTimeCalculator(array,searchTimes,sizes);


        sortsShowAndSaveChart("Sort Tests on Sorted Data",sizes ,sortsTimes);
        printTable(sortsTimes,sizes,1,"Sorted");

        searchShowAndSaveChart("Search Tests on Data",sizes ,searchTimes);
        printTable(searchTimes,sizes,0,"null");



        // Reverse

        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }

        insertionTimeCalculator(array ,sortsTimes ,sizes);
        mergeTimeCalculator(array ,sortsTimes ,sizes);
        countingTimeCalculator(array ,sortsTimes ,sizes);

        sortsShowAndSaveChart("Sort Tests on Reverse Data",sizes ,sortsTimes);

        printTable(sortsTimes,sizes,1,"Reverse");


}
}
