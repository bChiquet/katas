package com.kata.gildedRose;

public class Sulfuras extends Item {
    public Sulfuras(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    /**
     * Sulfuras never changes when day passes
     */
    @Override
    public void dailyUpdate() {}
}
