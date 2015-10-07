package com.kata.gildedRose;

public class Conjured extends Item {
    public Conjured(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void qualityDecay(){
            quality-=2;
        if (quality < 0) quality = 0;
    }
}
