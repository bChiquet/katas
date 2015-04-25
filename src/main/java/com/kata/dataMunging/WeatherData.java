package com.kata.dataMunging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Kata implementing weather calculations
 * codekata.com/kata/kata04-data-munging/
 **/
public class WeatherData {
    public static final int DAY_FIELD = 1;
    public static final int MIN_TEMP_FIELD = 3;
    public static final int MAX_TEMP_FIELD = 2;

    private class DayTemperatureInfo {
        public int day;
        public int minimumTemperature;
        public int maximumTemperature;

        DayTemperatureInfo(int day, int minimumTemperature, int maximumTemperature){
            this.day = day;
            this.minimumTemperature = minimumTemperature;
            this.maximumTemperature = maximumTemperature;
        }
    }
    List<DayTemperatureInfo> temperatureInfoList = new ArrayList<>();

    WeatherData(String filePath) {
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            lines.forEach(this::weatherDataLineConsumer);
        }
        catch(IOException exception){
            System.out.println("IOException");
            //TODO Find a way to handle exception properly
        }
    }

    void weatherDataLineConsumer(String weatherDataLine) {
        String[] weatherData = weatherDataLine.split("\\s+");
        try {
            temperatureInfoList.add(
                    new DayTemperatureInfo(
                            Integer.parseInt(weatherData[DAY_FIELD]),
                            Integer.parseInt(weatherData[MIN_TEMP_FIELD].replace("*", "")),
                            Integer.parseInt(weatherData[MAX_TEMP_FIELD].replace("*", "")))
            );
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ignored){}
    }

    int getDayWithMinimumTemperatureSpread(){
        int lowestTemperatureSpreadDay=-1;
        int lowestTemperatureSpread = Integer.MAX_VALUE;
        for (DayTemperatureInfo day : temperatureInfoList){
            if(day.maximumTemperature-day.minimumTemperature < lowestTemperatureSpread) {
                lowestTemperatureSpreadDay = day.day;
                lowestTemperatureSpread = day.maximumTemperature - day.minimumTemperature;
            }
        }
        return lowestTemperatureSpreadDay;
    }

}
