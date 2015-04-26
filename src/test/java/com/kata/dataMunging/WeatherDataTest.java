package com.kata.dataMunging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeatherDataTest {
    public static final String PATH_TO_DATA = "C:\\Users\\Py\\IdeaProjects\\katas\\src\\test\\ressources\\weather.dat";
    WeatherData weatherData;

    @Before
    public void setUp() throws Exception{
        weatherData = new WeatherData(PATH_TO_DATA);
    }

    @Test
    public void should_return_day_with_minimum_temperature_spread() throws Exception{
        Assert.assertEquals(14, weatherData.getDayWithMinimumTemperatureSpread());
    }

}