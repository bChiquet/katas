package com.kata.gildedRose;

public class AgedBrie extends Item {
    public AgedBrie(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    /**
     * Quality actually increases instead of decreasing
     */
    @Override
    public void qualityDecay() {
        if (quality < 50) {
            quality++;
        }
    }
}
