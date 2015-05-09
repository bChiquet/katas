package com.kata.dataMunging;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Kata implementing weather calculations
 * codekata.com/kata/kata04-data-munging/
 **/
public class WeatherData {
    public static final int DAY_FIELD = 1;
    public static final int MIN_TEMP_FIELD = 3;
    public static final int MAX_TEMP_FIELD = 2;

    private class DayTemperatureInfo {
        public final int day;
        public final int minimumTemperature;
        public final int maximumTemperature;

        DayTemperatureInfo(int day, int minimumTemperature, int maximumTemperature){
            this.day = day;
            this.minimumTemperature = minimumTemperature;
            this.maximumTemperature = maximumTemperature;
        }
    }
    List<DayTemperatureInfo> temperatureInfoList = new ArrayList<>();

    public void processWeatherData(String filePath) {
        try {
            Files.lines(Paths.get(filePath))
                    .skip(2) //Skip the header lines
                    .filter(line -> !line.contains("  mo")) //Skip the footer line with monthly figures.
                    .forEach(this::weatherDataLineConsumer);
        }
        catch(IOException exception){
            Throwables.propagate(exception);
        }
    }

    void weatherDataLineConsumer(String weatherDataLine) {
        String[] weatherData = weatherDataLine.split("\\s+");
        temperatureInfoList.add(
                new DayTemperatureInfo(
                        Integer.parseInt(weatherData[DAY_FIELD]),
                        Integer.parseInt(weatherData[MIN_TEMP_FIELD].replace("*", "")),
                        Integer.parseInt(weatherData[MAX_TEMP_FIELD].replace("*", "")))
        );
    }

    int getDayWithMinimumTemperatureSpread(){
        return temperatureInfoList.stream()
                .reduce((dayTemperatureInfo, dayTemperatureInfo2) -> {
                    if ((dayTemperatureInfo.maximumTemperature - dayTemperatureInfo.minimumTemperature)
                            < (dayTemperatureInfo2.maximumTemperature - dayTemperatureInfo2.minimumTemperature))
                        return dayTemperatureInfo;
                    return dayTemperatureInfo2;
                })
                .get()
                .day;
    }
}
