package com.kata.dataMunging;

import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeatherDataTest {
    WeatherData weatherData;

    @Before
    public void setUp() throws Exception{
        weatherData = new WeatherData();
    }

    @Test
    public void should_return_day_with_minimum_temperature_spread() throws Exception{
        //Given
        weatherData.processWeatherData(Resources.getResource("weather.dat").getPath());

        //When
        int theDay = weatherData.getDayWithMinimumTemperatureSpread();

        //Then
        Assert.assertEquals(14, theDay);
    }
}